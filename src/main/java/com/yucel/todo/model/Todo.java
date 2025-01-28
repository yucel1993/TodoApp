package com.yucel.todo.model;


import jakarta.persistence.*;




@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "todo_id")
    private Category category;

    public Todo(String description, Category category) {
        this.description = description;
        this.category = category;
    }

    public Todo() {
    }

    public Todo(Long id, String description, Category category) {
        this.id = id;
        this.description = description;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
