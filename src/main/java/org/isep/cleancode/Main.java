package org.isep.cleancode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import org.isep.cleancode.presentation.TodoController;

public class Main {
    public static void main(String[] args) {
        // Initialize the ObjectMapper with JavaTimeModule for LocalDate serialization
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Create the Javalin app and set the custom ObjectMapper
        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson(objectMapper));
        }).start(7000);

        app.post("/todos", TodoController.createTodo);
        app.get("/todos", TodoController.listTodos);
    }
}