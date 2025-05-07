package org.isep.cleancode.application;

import org.isep.cleancode.Todo;
import java.util.List;

public interface ITodoRepository {
    boolean add(Todo todo);
    List<Todo> getAll();
}
