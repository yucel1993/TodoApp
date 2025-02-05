package com.yucel.todo.service.Todo;

import com.yucel.todo.dto.TodoDto;
import com.yucel.todo.exceptions.AlreadyExistsException;
import com.yucel.todo.exceptions.ResourceNotFoundException;
import com.yucel.todo.model.Category;
import com.yucel.todo.model.Todo;
import com.yucel.todo.repository.CategoryRepository;
import com.yucel.todo.repository.TodoRepository;
import com.yucel.todo.request.AddTodoRequest;
import com.yucel.todo.request.TodoUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService implements ITodoService{


    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Todo addTodo(AddTodoRequest request) {
        if(todoExists(request.getDescription())){
            throw new AlreadyExistsException(
                     request.getDescription() + " already exists, you may update this todo instead!");
        }
        Category category = Optional.ofNullable(categoryRepository.findByCategoryName(request.getCategory().getCategoryName()))
                .orElseGet(()->{
                    Category newCategory =new Category(request.getCategory().getCategoryName());
                    return categoryRepository.save(newCategory);
                });
            request.setCategory(category);
        return todoRepository.save(createTodo(request, category));

    }


    private Todo createTodo(AddTodoRequest request, Category category) {
        return new Todo(
                request.getDescription(),
                category
        );
    }
    private boolean todoExists(String name) {
        return todoRepository.existsByDescription(name);
    }




    @Override
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("There is no such a todo"));
    }




    @Override
    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public Todo updateTodo(TodoUpdateRequest todo, Long todoId) {

        return todoRepository.findById(todoId)
                .map(existingTodo->updateExistingTodo(existingTodo,todo))
                .map(todoRepository::save)
                .orElseThrow(()-> new ResourceNotFoundException("No eligible todo found"));
    }

    private Todo updateExistingTodo(Todo existingTodo, TodoUpdateRequest request) {

        existingTodo.setDescription(request.getDescription());

        Category category = categoryRepository.findByCategoryName(request.getCategory().getCategoryName());
        existingTodo.setCategory(category);
        return existingTodo;

    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public List<Todo> getTodosByCategory(String category) {
        return todoRepository.findByCategory_CategoryName(category);
    }

    @Override
    public List<Todo> getTodosByName(String name) {
        return todoRepository.findByName(name);
    }


    @Override
    public List<TodoDto> getConvertedTodos(List<Todo> todos) {
        return todos.stream().map(this::convertToDto).toList();
    }

    @Override
    public TodoDto convertToDto(Todo todo) {
        TodoDto todoDto = modelMapper.map(todo, TodoDto.class);
        return todoDto;

    }
}
