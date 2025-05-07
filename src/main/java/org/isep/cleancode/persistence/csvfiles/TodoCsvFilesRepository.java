package org.isep.cleancode.persistence.csvfiles;

import org.isep.cleancode.application.ITodoRepository;
import org.isep.cleancode.Todo;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class TodoCsvFilesRepository implements ITodoRepository {
    private final Path filePath;

    public TodoCsvFilesRepository() {
        // Uses system APPDATA directory (Windows) or fallback to user home
        String appData = System.getenv("APPDATA");
        if (appData == null) {
            appData = System.getProperty("user.home");
        }
        this.filePath = Paths.get(appData, "todos.csv");
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create CSV file", e);
        }
    }

    @Override
    public boolean add(Todo todo) {
        List<Todo> existing = getAll();
        boolean exists = existing.stream()
                .anyMatch(t -> t.getName().equalsIgnoreCase(todo.getName()));
        if (exists) return false;

        String line = todo.getName() + "," + (todo.getDueDate() != null ? todo.getDueDate() : "");
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write todo to CSV", e);
        }

        return true;
    }

    @Override
    public List<Todo> getAll() {
        List<Todo> todos = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                String name = parts[0];
                LocalDate date = parts.length > 1 && !parts[1].isBlank()
                        ? LocalDate.parse(parts[1])
                        : null;
                todos.add(new Todo(name, date));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read todos from CSV", e);
        }
        return todos;
    }
}
