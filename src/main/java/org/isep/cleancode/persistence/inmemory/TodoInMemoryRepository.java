package org.isep.cleancode.persistence.inmemory;

import org.isep.cleancode.application.ITodoRepository;
import org.isep.cleancode.Todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoInMemoryRepository implements ITodoRepository {
    private final List<Todo> todos = new ArrayList<>();

    @Override
    public boolean add(Todo todo) {
        boolean exists = todos.stream().anyMatch(t -> t.getName().equalsIgnoreCase(todo.getName()));
        if (exists) return false;
        todos.add(todo);
        return true;
    }

    @Override
    public List<Todo> getAll() {
        return Collections.unmodifiableList(todos);
    }
}
