package com.github.Iqbolshoh;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TaskItem {
    @Setter private String task;
    @Setter private String dueDate;
    private boolean isCompleted;

    public TaskItem(final String task, final String dueDate) {
        this.task = task;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    @Override
    public String toString() {
        return (isCompleted ? "✓ " : "✗ ") + task + " (" + dueDate + ")";
    }

    public void toggleCompleted() {
        isCompleted = !isCompleted;
    }
}
