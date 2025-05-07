package org.isep.cleancode;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        app.post("/todos", TodoController.createTodo);
        app.get("/todos", TodoController.listTodos);
    }
}
