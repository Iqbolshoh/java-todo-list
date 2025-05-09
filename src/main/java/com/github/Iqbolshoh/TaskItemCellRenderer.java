package com.github.Iqbolshoh;

import javax.swing.*;
import java.awt.*;

public class TaskItemCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(
        final JList<?> list,
        final Object value,
        final int index,
        final boolean isSelected,
        final boolean cellHasFocus
    ) {
        final JCheckBox checkbox = new JCheckBox();

        if (value instanceof TaskItem) {
            final TaskItem item = (TaskItem) value;
            checkbox.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            checkbox.setEnabled(list.isEnabled());
            checkbox.setFont(list.getFont());
            checkbox.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            checkbox.setText("(" + item.getDueDate() + ") " + item.getTask());
            checkbox.setSelected(item.isCompleted());
        }

        return checkbox;
    }
}