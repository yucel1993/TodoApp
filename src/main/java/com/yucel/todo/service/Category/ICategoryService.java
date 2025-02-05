package com.yucel.todo.service.Category;



import com.yucel.todo.model.Category;

import java.util.List;

public interface ICategoryService {
Category getCategoryById (Long id);
Category getCategoryByName(String name);
List<com.yucel.todo.model.Category> getAllCategories();
Category addCategory(Category category);
Category updateCategory(Category category, Long id);
void deleteCategoryById(Long id);
}
