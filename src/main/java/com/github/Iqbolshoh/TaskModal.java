package com.github.Iqbolshoh;

import com.github.lgooddatepicker.components.DatePicker;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Consumer;

public class TaskModal extends JDialog {
    private DatePicker dateField;
    private JTextField nameField;

    private JButton submit;

    public TaskModal(final @NonNull JFrame parent, final @NonNull Consumer<com.github.Iqbolshoh.TaskItem> onSubmit) {
        super(parent, "Edit Task", true);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(this.createTaskPanel());
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(this.createDatePanel());
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(this.createButtonPanel());
        super.add(panel);

        submit.addActionListener(e -> {
            this.getTaskItem().ifPresent(taskItem -> {
                onSubmit.accept(taskItem);
                super.dispose();
            });
        });

        super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        super.setModal(true);
        super.setResizable(false);

        super.pack();
        super.setLocationRelativeTo(parent);
        super.setVisible(true);
    }

    /**
     * Creates the panel with date field and label.
     *
     * @return The created date panel.
     */
    private JPanel createDatePanel() {
        final JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.add(new JLabel("Date:"), BorderLayout.WEST);

        dateField = new DatePicker();
        dateField.setDate(LocalDate.now());
        dateField.getComponentDateTextField().setEditable(false);
        dateField.getComponentDateTextField().setEnabled(false);
        dateField.getComponentToggleCalendarButton().setText("Choose");
        panel.add(dateField, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates the panel with task name field and label.
     *
     * @return The created task panel.
     */
    private JPanel createTaskPanel() {
        final JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.add(new JLabel("Task:"), BorderLayout.WEST);
        nameField = new JTextField();
        panel.add(nameField, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates the panel with action buttons.
     *
     * @return The created button panel.
     */
    private JPanel createButtonPanel() {
        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submit = new JButton("Submit");
        panel.add(submit);
        return panel;
    }

    /**
     * <p>Constructs a {@link com.github.Iqbolshoh.TaskItem} from the input fields.</p>
     *
     * <p>If a field's value is invalid, it sets the border to red and shows a tooltip.</p>
     *
     * @return An {@link Optional} containing the {@link com.github.Iqbolshoh.TaskItem} if valid, otherwise empty.
     */
    private Optional<com.github.Iqbolshoh.TaskItem> getTaskItem() {
        if (nameField.getText().isEmpty()) {
            nameField.setBorder(BorderFactory.createLineBorder(Color.RED));
            nameField.setToolTipText("Task name cannot be empty.");
            return Optional.empty();
        }

        final LocalDate date = dateField.getDate();
        final String dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return Optional.of(new com.github.Iqbolshoh.TaskItem(nameField.getText(), dateString));
    }
}