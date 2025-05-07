package org.isep.cleancode;

import java.time.LocalDate;

public class Todo {

    private final String name;
    private final LocalDate dueDate;

    public Todo(String name, LocalDate due) {
        this.name = name;
        this.dueDate = due;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
