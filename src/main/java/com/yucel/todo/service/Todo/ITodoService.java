package com.yucel.todo.service.Todo;

import com.yucel.todo.dto.TodoDto;
import com.yucel.todo.model.Todo;
import com.yucel.todo.request.AddTodoRequest;
import com.yucel.todo.request.TodoUpdateRequest;

import java.util.List;

public interface ITodoService {
    Todo addTodo(AddTodoRequest todo);
    Todo getTodoById(Long id);
    void deleteTodoById(Long id);
    Todo updateTodo(TodoUpdateRequest todo, Long todoId);
    List<Todo> getAllTodos();
    List<Todo> getTodosByCategory(String category);
    List<Todo> getTodosByName(String name);
    List<TodoDto> getConvertedTodos(List<Todo> todos);
    TodoDto convertToDto(Todo product);
}
