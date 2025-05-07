package org.isep.cleancode;

import io.javalin.Javalin;
import org.isep.cleancode.presentation.TodoController;
import org.isep.cleancode.application.TodoManager;
import org.isep.cleancode.application.ITodoRepository;
import org.isep.cleancode.persistence.inmemory.TodoInMemoryRepository;
import org.isep.cleancode.persistence.csvfiles.TodoCsvFilesRepository;
import org.isep.cleancode.config.Config;

public class Main {
    public static void main(String[] args) {
        String repoType = Config.get("repo");

        ITodoRepository repo = switch (repoType.toLowerCase()) {
            case "csv" -> new TodoCsvFilesRepository();
            case "memory" -> new TodoInMemoryRepository();
            default -> throw new IllegalArgumentException("Unsupported repo type in config: " + repoType);
        };

        TodoManager manager = new TodoManager(repo);
        TodoController.setManager(manager);

        Javalin app = Javalin.create().start(7000);
        app.post("/todos", TodoController.createTodo);
        app.get("/todos", TodoController.listTodos);

        System.out.println("App started using " + repoType + " repository.");
    }
}
