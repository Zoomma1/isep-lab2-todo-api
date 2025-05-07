package org.isep.cleancode.presentation;

import org.isep.cleancode.application.TodoManager;
import io.javalin.http.Handler;

public class TodoController {
    private static TodoManager manager;

    public static void setManager(TodoManager injectedManager) {
        manager = injectedManager;
    }

    public static Handler createTodo = ctx -> {
        String name = ctx.formParam("name");
        String due = ctx.formParam("dueDate");

        try {
            boolean success = manager.createTodo(name, due);
            if (!success) {
                ctx.status(400).result("Todo name must be unique.");
            } else {
                ctx.status(201).result("Todo created.");
            }
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        }
    };

    public static Handler listTodos = ctx -> ctx.json(manager.getAllTodos());
}
