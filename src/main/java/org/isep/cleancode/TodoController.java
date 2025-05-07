package org.isep.cleancode;

import io.javalin.http.Handler;

import java.time.DateTimeException;
import java.time.LocalDate;

public class TodoController {

    public static Handler createTodo = ctx -> {
        String name = ctx.formParam("name");
        String due = ctx.formParam("dueDate");

        if (name == null || name.trim().isEmpty() || name.length() > 63) {
            ctx.status(400).result("Invalid or missing 'name'. Must be non-empty and < 64 chars.");
            return;
        }

        LocalDate dueDate = null;
        if (due != null && !due.trim().isEmpty()) {
            try {
                dueDate = LocalDate.parse(due);
            } catch (DateTimeException e) {
                ctx.status(400).result("Invalid 'dueDate'. Must be in YYYY-MM-DD format.");
                return;
            }
        }

        Todo newTodo = new Todo(name, dueDate);
        boolean added = TodoStore.addTodo(newTodo);

        if (!added) {
            ctx.status(400).result("Todo name must be unique.");
        } else {
            ctx.status(201).result("Todo created.");
        }
    };

    public static Handler listTodos = ctx -> ctx.json(TodoStore.getAllTodos());
}
