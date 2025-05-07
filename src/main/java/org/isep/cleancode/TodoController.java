package org.isep.cleancode;

import io.javalin.http.Handler;

public class TodoController {

    public static Handler createTodo = ctx -> {
        String name = ctx.formParam("name");

        if (name == null || name.trim().isEmpty() || name.length() > 63) {
            ctx.status(400).result("Invalid or missing 'name'. Must be non-empty and < 64 chars.");
            return;
        }

        Todo newTodo = new Todo(name);
        boolean added = TodoStore.addTodo(newTodo);

        if (!added) {
            ctx.status(400).result("Todo name must be unique.");
        } else {
            ctx.status(201).result("Todo created.");
        }
    };

    public static Handler listTodos = ctx -> ctx.json(TodoStore.getAllTodos());
}
