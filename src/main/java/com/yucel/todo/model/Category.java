package com.yucel.todo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Todo> todos = new HashSet<>();

//    With orphanRemoval = true: The todo1 entity will be automatically
//    deleted from the database because it is no longer associated with any Category.

//    Without orphanRemoval = true: The todo1 entity would remain in the database,
//    but it would no longer be associated with any Category.


    public Category() {
    }

    public Category(Long id, String categoryName, Set<Todo> todos) {
        this.id = id;
        this.categoryName = categoryName;
        this.todos = todos;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Todo> getTodos() {
        return todos;
    }

    public void setTodos(Set<Todo> todos) {
        this.todos = todos;
    }
}
