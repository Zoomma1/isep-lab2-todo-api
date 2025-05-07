package org.isep.cleancode;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TodoStore {
    private static final List<Todo> todos = new CopyOnWriteArrayList<>();

    public static List<Todo> getAllTodos() {
        return Collections.unmodifiableList(todos);
    }

    public static boolean addTodo(Todo todo) {
        boolean exists = todos.stream().anyMatch(t -> t.getName().equalsIgnoreCase(todo.getName()));
        if (exists) return false;
        todos.add(todo);
        return true;
    }
}
