package org.isep.cleancode.persistence.csvfiles;

import org.isep.cleancode.application.ITodoRepository;
import org.isep.cleancode.Todo;

import java.io.*;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class TodoCsvFilesRepository implements ITodoRepository {
    private final Path filePath;
    private final Set<String> nameCache = new HashSet<>();

    public TodoCsvFilesRepository() {
        String appData = System.getenv("APPDATA");
        if (appData == null) {
            appData = System.getProperty("user.home");
        }
        this.filePath = Paths.get(appData, "todos.csv");
        ensureFileExists();
        preloadNameCache();
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

    private void preloadNameCache() {
        for (Todo todo : getAll()) {
            nameCache.add(todo.getName().toLowerCase());
        }
    }

    @Override
    public synchronized boolean add(Todo todo) {
        if (nameCache.contains(todo.getName().toLowerCase())) return false;

        String line = todo.getName() + "," + (todo.getDueDate() != null ? todo.getDueDate() : "");

        try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
             BufferedWriter writer = new BufferedWriter(Channels.newWriter(channel, StandardCharsets.UTF_8))) {
            FileLock lock = channel.lock();
            try {
                writer.write(line);
                writer.newLine();
                writer.flush();
                nameCache.add(todo.getName().toLowerCase());
            } finally {
                lock.release();
            }
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
