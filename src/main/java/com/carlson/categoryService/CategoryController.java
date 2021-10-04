package com.carlson.categoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/categories")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewCategory (@RequestParam String name, @RequestParam String parentName) {
        categoryService.addNewCategory(name, parentName);
        return "Saved";
    }

    @GetMapping(path="")
    public @ResponseBody Iterable<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(path="/{name}")
    public @ResponseBody Category getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }
}