package org.isep.cleancode.application;

import org.isep.cleancode.Todo;

import java.time.LocalDate;
import java.util.List;

public class TodoManager {
    private final ITodoRepository repository;

    public TodoManager(ITodoRepository repository) {
        this.repository = repository;
    }

    public boolean createTodo(String name, String due) {
        if (name == null || name.isBlank() || name.length() > 63) {
            throw new IllegalArgumentException("Invalid name");
        }

        LocalDate dueDate = null;
        if (due != null && !due.isBlank()) {
            try {
                dueDate = LocalDate.parse(due);
            } catch (java.time.format.DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd'.", e);
            }
        }

        Todo todo = new Todo(name, dueDate);
        return repository.add(todo);
    }

    public List<Todo> getAllTodos() {
        return repository.getAll();
    }
}
