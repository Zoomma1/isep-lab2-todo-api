package org.isep.cleancode.application;

import org.isep.cleancode.Todo;
import org.isep.cleancode.persistence.TodoRepository;

import java.time.LocalDate;
import java.util.List;

public class TodoManager {
    private final TodoRepository repository = new TodoRepository();

    public boolean createTodo(String name, String due) {
        if (name == null || name.isBlank() || name.length() > 63) {
            throw new IllegalArgumentException("Invalid name");
        }

        LocalDate dueDate = null;
        if (due != null && !due.isBlank()) {
            dueDate = LocalDate.parse(due);
        }

        Todo todo = new Todo(name, dueDate);
        return repository.add(todo);
    }

    public List<Todo> getAllTodos() {
        return repository.getAll();
    }
}
