import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private DefaultListModel<TaskItem> listModel;
    private JList<TaskItem> taskList;
    private JTextField taskField;
    private JTextField dateField;
    private List<TaskItem> data = new ArrayList<>(); // Array to store data

    public Main() {
        // Create main window
        JFrame frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500); // Increase window size

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10)); // Add margin

        // Task list
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setCellRenderer(new CustomListRenderer());
        panel.add(new JScrollPane(taskList), BorderLayout.CENTER);

        // Input panel (for entering task and due date)
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // GridLayout with margin
        inputPanel.add(new JLabel("Task:"));
        taskField = new JTextField();
        inputPanel.add(taskField);

        inputPanel.add(new JLabel("Due Date (dd-mm-yyyy):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10)); // 5 buttons
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton editButton = new JButton("Edit");
        JButton saveButton = new JButton("Save");
        JButton toggleButton = new JButton("Completed");

        // Add buttons to panel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(toggleButton);

        // Add action listeners to buttons
        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeTask());
        editButton.addActionListener(e -> editTask());
        saveButton.addActionListener(e -> saveTasks());
        toggleButton.addActionListener(e -> toggleTaskStatus());

        // Add panels to main panel
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }

    // Add a new task
    private void addTask() {
        String taskText = taskField.getText().trim();
        String dueDateText = dateField.getText().trim();
        if (!taskText.isEmpty() && !dueDateText.isEmpty()) {
            TaskItem newTask = new TaskItem(taskText, dueDateText);
            listModel.addElement(newTask); // Add to list
            data.add(newTask); // Add to array
            taskField.setText("");
            dateField.setText("");
        }
    }

    // Remove a task
    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex); // Remove from list
            data.remove(selectedIndex); // Remove from array
        }
    }

    // Edit a task
    private void editTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String newTaskText = JOptionPane.showInputDialog("Enter new task:");
            String newDueDateText = JOptionPane.showInputDialog("Enter new due date (yyyy-mm-dd):");
            if (newTaskText != null && newDueDateText != null) {
                TaskItem item = listModel.getElementAt(selectedIndex);
                item.setTask(newTaskText);
                item.setDueDate(newDueDateText);
                taskList.repaint(); // Refresh list
            }
        }
    }

    // Mark task as completed
    private void toggleTaskStatus() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            TaskItem item = listModel.getElementAt(selectedIndex);
            item.toggleCompleted();
            taskList.repaint(); // Refresh list
        }
    }

    // Save tasks (only in memory)
    private void saveTasks() {
        JOptionPane.showMessageDialog(null, "Tasks saved!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}

// Task class
class TaskItem {
    private String task;
    private String dueDate;
    private boolean isCompleted;

    public TaskItem(String task, String dueDate) {
        this.task = task;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    public void toggleCompleted() {
        isCompleted = !isCompleted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return (isCompleted ? "✓ " : "✗ ") + task + " (" + dueDate + ")";
    }
}

// Custom list renderer for better UI
class CustomListRenderer extends DefaultListCellRenderer {
    private ImageIcon checkIcon = new ImageIcon("check.png");
    private ImageIcon uncheckIcon = new ImageIcon("uncheck.png");

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof TaskItem) {
            TaskItem task = (TaskItem) value;
            label.setIcon(task.isCompleted() ? checkIcon : uncheckIcon);
        }
        return label;
    }
}
