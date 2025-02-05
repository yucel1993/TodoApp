package com.yucel.todo.controller;

import com.yucel.todo.dto.TodoDto;
import com.yucel.todo.exceptions.AlreadyExistsException;
import com.yucel.todo.exceptions.ResourceNotFoundException;
import com.yucel.todo.model.Todo;
import com.yucel.todo.request.AddTodoRequest;
import com.yucel.todo.request.TodoUpdateRequest;
import com.yucel.todo.response.ApiResponse;
import com.yucel.todo.service.Todo.ITodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    @Autowired
    private ITodoService todoService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse>getAllTodos(){
              List<Todo> todos = todoService.getAllTodos();
              List<TodoDto> convertedTodos =todoService.getConvertedTodos(todos);
        return  ResponseEntity.ok(new ApiResponse("success", convertedTodos));

    };

    @GetMapping("/todo/{todoId}/todo")
    public ResponseEntity<ApiResponse> getTodoById(@PathVariable Long todoId) {
        try {
            Todo todo = todoService.getTodoById(todoId);
            TodoDto TodoDto = todoService.convertToDto(todo);
            return  ResponseEntity.ok(new ApiResponse("success", TodoDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTodo(@RequestBody AddTodoRequest todo) {
        try {
            Todo theTodo = todoService.addTodo(todo);
            TodoDto todoDto = todoService.convertToDto(theTodo);
            return ResponseEntity.ok(new ApiResponse("Add todo success!", todoDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/todo/{todoId}/update")
    public  ResponseEntity<ApiResponse> updateTodo(@RequestBody TodoUpdateRequest request, @PathVariable Long todoId) {
        try {
            Todo theTodo = todoService.updateTodo(request, todoId);
            TodoDto todoDto = todoService.convertToDto(theTodo);
            return ResponseEntity.ok(new ApiResponse("Update todo success!", todoDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/todo/{todoId}/delete")
    public ResponseEntity<ApiResponse> deleteTodo(@PathVariable Long todoId) {
        try {
            todoService.deleteTodoById(todoId);
            return ResponseEntity.ok(new ApiResponse("Delete todo success!", todoId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/todo/{category}/all/todos")
    public ResponseEntity<ApiResponse> findTodosByCategory(@PathVariable String category) {
        try {
            List<Todo> todos = todoService.getTodosByCategory(category);
            if (todos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No todos found ", null));
            }
            List<TodoDto> convertedTodos = todoService.getConvertedTodos(todos);
            return  ResponseEntity.ok(new ApiResponse("success", convertedTodos));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/todos/{name}/todos")
    public ResponseEntity<ApiResponse> getTodoByName(@PathVariable String name){
        try {
            List<Todo> todos = todoService.getTodosByName(name);
            if (todos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No todos found ", null));
            }
            List<TodoDto> convertedTodos = todoService.getConvertedTodos(todos);
            return  ResponseEntity.ok(new ApiResponse("success", convertedTodos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }
}
