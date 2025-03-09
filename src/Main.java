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
    private List<TaskItem> data = new ArrayList<>(); // Ma'lumotlarni saqlash uchun massiv

    public Main() {
        // Asosiy oyna yaratish
        JFrame frame = new JFrame("ToDo List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500); // Oyna hajmini kattalashtirish

        // Asosiy panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10)); // Margin qo'shish

        // Vazifalar ro'yxati
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setCellRenderer(new CustomListRenderer());
        panel.add(new JScrollPane(taskList), BorderLayout.CENTER);

        // Input paneli (Vazifa va muddat kiritish uchun)
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // GridLayout va margin
        inputPanel.add(new JLabel("Vazifa:"));
        taskField = new JTextField();
        inputPanel.add(taskField);

        inputPanel.add(new JLabel("Muddat (dd-mm-yyyy):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        // Tugmalar paneli
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10)); // 5 ta tugma
        JButton addButton = new JButton("Qo'shish");
        JButton removeButton = new JButton("O'chirish");
        JButton editButton = new JButton("Tahrirlash");
        JButton saveButton = new JButton("Saqlash");
        JButton toggleButton = new JButton("Bajarildi");

        // Tugmalarni qo'shish
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(toggleButton);

        // Tugmalarga hodisa qo'shish
        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeTask());
        editButton.addActionListener(e -> editTask());
        saveButton.addActionListener(e -> saveTasks());
        toggleButton.addActionListener(e -> toggleTaskStatus());

        // Asosiy panelga qo'shish
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Oynaga panelni qo'shish
        frame.add(panel);
        frame.setVisible(true);
    }

    // Vazifa qo'shish
    private void addTask() {
        String taskText = taskField.getText().trim();
        String dueDateText = dateField.getText().trim();
        if (!taskText.isEmpty() && !dueDateText.isEmpty()) {
            TaskItem newTask = new TaskItem(taskText, dueDateText);
            listModel.addElement(newTask); // Ro'yxatga qo'shish
            data.add(newTask); // Massivga qo'shish
            taskField.setText("");
            dateField.setText("");
        }
    }

    // Vazifani o'chirish
    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex); // Ro'yxatdan o'chirish
            data.remove(selectedIndex); // Massivdan o'chirish
        }
    }

    // Vazifani tahrirlash
    private void editTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String newTaskText = JOptionPane.showInputDialog("Yangi vazifani kiriting:");
            String newDueDateText = JOptionPane.showInputDialog("Yangi muddatni kiriting (yyyy-mm-dd):");
            if (newTaskText != null && newDueDateText != null) {
                TaskItem item = listModel.getElementAt(selectedIndex);
                item.setTask(newTaskText);
                item.setDueDate(newDueDateText);
                taskList.repaint(); // Ro'yxatni yangilash
            }
        }
    }

    // Vazifani bajarilgan deb belgilash
    private void toggleTaskStatus() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            TaskItem item = listModel.getElementAt(selectedIndex);
            item.toggleCompleted();
            taskList.repaint(); // Ro'yxatni yangilash
        }
    }

    // Vazifalarni saqlash (faqat massivda saqlash)
    private void saveTasks() {
        JOptionPane.showMessageDialog(null, "Vazifalar saqlandi!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}

// Vazifa elementlari uchun klass
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

// Vazifalar ro'yxatini chiroyli ko'rsatish uchun renderer
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