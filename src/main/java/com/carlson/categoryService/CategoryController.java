package com.carlson.categoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/categories")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewCategory (@RequestParam String name, @RequestParam String parentName) {
        return categoryService.addNewCategory(name, parentName);
    }

    @GetMapping(path="")
    public @ResponseBody Iterable<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(path="/{name}")
    public @ResponseBody Category getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    @PutMapping(path="/{categoryId}/products/{productId}")
    public @ResponseBody String addProductToCategory(@PathVariable Integer categoryId, @PathVariable Integer productId) {
        return categoryService.addProductToCategory(categoryId, productId);
    }
}