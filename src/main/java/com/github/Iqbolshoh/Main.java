package com.github.Iqbolshoh;

import javax.swing.*;
import java.awt.*;

public class Main {
    private final TaskList taskList = new TaskList();

    public Main() {
        // Create main window
        final JFrame frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500); // Increase window size

        // Main panel
        final JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout(10, 10)); // Add margin

        panel.add(this.createTaskListPanel(), BorderLayout.CENTER);
        panel.add(this.createButtonPanel(), BorderLayout.SOUTH);

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    /**
     * Creates a {@link JPanel} with components for managing the {@link #taskList}.
     *
     * @return The created {@link JPanel}.
     */
    private JPanel createButtonPanel() {
        final JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 3 buttons
        final JButton addButton = new JButton("Add");
        final JButton removeButton = new JButton("Remove");
        final JButton editButton = new JButton("Edit");

        // Add buttons to panel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);

        // Add action listeners to buttons
        addButton.addActionListener(e -> {
            new TaskModal((JFrame) SwingUtilities.getWindowAncestor(taskList), taskList::addTask);
        });
        removeButton.addActionListener(e -> taskList.getSelectedTask().ifPresent(taskList::removeTask));
        editButton.addActionListener(e -> editTask());

        return buttonPanel;
    }

    /**
     * Creates a {@link JScrollPane} containing the {@link #taskList}.
     *
     * @return The created {@link JScrollPane}.
     */
    private JScrollPane createTaskListPanel() {
        final JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    // Edit a task
    private void editTask() {
        final int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            final String newTaskText = JOptionPane.showInputDialog("Enter new task:");
            final String newDueDateText = JOptionPane.showInputDialog("Enter new due date (yyyy-mm-dd):");
            if (newTaskText != null && newDueDateText != null) {
                taskList.getSelectedTask().ifPresent(task -> {
                    task.setTask(newTaskText);
                    task.setDueDate(newDueDateText);
                    taskList.repaint();
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
