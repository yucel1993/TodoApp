package com.yucel.todo.repository;

import com.yucel.todo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName); // Changed from findByName to findByCategoryName
    boolean existsByCategoryName(String categoryName); //
// IMPORTANT THIS CategoryName MUST COME FROM ENTITY: tHATS WHY findByCategoryName
//    The error occurs because the repository methods (findByName and existsByName) do not match the field name (categoryName) in the Category entity.
//
//    To fix it, update the repository method names to findByCategoryName and existsByCategoryName.
//
//    Ensure that the service and controller layers use the updated method names.
}
