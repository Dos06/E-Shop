package springbootproject.springboot.services;

import springbootproject.springboot.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    Category getCategory(int id);
    Category addCategory(Category category);
    Category saveCategory(Category category);

}
