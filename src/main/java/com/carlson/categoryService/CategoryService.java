package com.carlson.categoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService() {}

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public String addNewCategory (String name, String parentName) {
        Category parent = categoryRepository.findByName(parentName);
        Category category = new Category(name);
        category.setParent(parent);
        categoryRepository.save(category);
        return "Saved";
    }

    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

}
