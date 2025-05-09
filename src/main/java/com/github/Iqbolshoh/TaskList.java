package com.github.Iqbolshoh;

import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Optional;

public class TaskList extends JList<TaskItem> implements MouseListener, MouseMotionListener {
    /** Constructs a new {@link TaskList}. */
    public TaskList() {
        super(new DefaultListModel<>());
        super.addMouseListener(this);
        super.addMouseMotionListener(this);
        super.setCellRenderer(new TaskItemCellRenderer());
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        int index = super.locationToIndex(e.getPoint());
        if (index >= 0) {
            Rectangle bounds = super.getCellBounds(index, index);
            // Check if click was in the checkbox area (first ~20 pixels)
            if (bounds != null && e.getX() < bounds.x + 20) {
                TaskItem item = super.getModel().getElementAt(index);
                item.toggleCompleted();
                super.repaint();
            }
        }
    }

    @Override
    public void mousePressed(final MouseEvent e) {}

    @Override
    public void mouseReleased(final MouseEvent e) {}

    @Override
    public void mouseEntered(final MouseEvent e) {}

    @Override
    public void mouseExited(final MouseEvent e) {}

    @Override
    public void mouseDragged(final MouseEvent e) {}

    @Override
    public void mouseMoved(final MouseEvent e) {
        int index = super.locationToIndex(e.getPoint());
        if (index >= 0) {
            Rectangle bounds = super.getCellBounds(index, index);
            // Check if mouse is in the checkbox area (first ~20 pixels)
            if (bounds != null && e.getX() < bounds.x + 20) {
                super.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                super.setCursor(Cursor.getDefaultCursor());
            }
        } else {
            super.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Adds a {@link TaskItem} to the list.
     *
     * @param item The {@link TaskItem} to add.
     */
    public void addTask(final @NonNull TaskItem item) {
        ((DefaultListModel<TaskItem>) super.getModel()).addElement(item);
    }

    /**
     * Removes a {@link TaskItem} from the list.
     *
     * @param item The {@link TaskItem} to remove.
     */
    public void removeTask(final @NonNull TaskItem item) {
        ((DefaultListModel<TaskItem>) super.getModel()).removeElement(item);
    }

    /**
     * Retrieves the currently selected {@link TaskItem}.
     *
     * @return An {@link Optional} containing the selected {@link TaskItem} if one is selected, otherwise an empty {@link Optional}.
     */
    public Optional<TaskItem> getSelectedTask() {
        int index = super.getSelectedIndex();
        if (index != -1) {
            return Optional.ofNullable(super.getModel().getElementAt(index));
        }

        return Optional.empty();
    }
}
