package com.yucel.todo.repository;

import com.yucel.todo.dto.TodoDto;
import com.yucel.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findByCategory_CategoryName(String categoryName);//IMPORTANT If you wan to traverse between another entity use this syntax
//    Why findByCategory_CategoryName Works
//    In your case:
//
//    Todo has a relationship with Category via the category field.
//
//    Category has a field called categoryName.
//
//    To query Todo entities based on the categoryName of their associated Category, you need to:
//
//    Traverse the category relationship in Todo.
//
//    Access the categoryName property in Category.
//
//    The correct syntax for this is:
//
//    java
//            Copy
//    findByCategory_CategoryName
    boolean existsByDescription(String name);
    @Query("SELECT p FROM Todo p WHERE LOWER(p.description) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Todo> findByName(@Param("name") String name);


}
