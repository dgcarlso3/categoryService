package com.carlson.categoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public String addNewCategory (String name, String parentName) {
//        Category category = new Category(name);
//        categoryRepository.save(n);
        return "Saved";
    }

    public List<Category> getAllCategories() {
        return null;
    }

}
