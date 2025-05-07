package org.isep.cleancode.persistence;

import org.isep.cleancode.Todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoRepository {
    private final List<Todo> todos = new ArrayList<>();

    public List<Todo> getAll() {
        return Collections.unmodifiableList(todos);
    }

    public boolean add(Todo todo) {
        boolean exists = todos.stream().anyMatch(t -> t.getName().equalsIgnoreCase(todo.getName()));
        if (exists) return false;
        todos.add(todo);
        return true;
    }
}
