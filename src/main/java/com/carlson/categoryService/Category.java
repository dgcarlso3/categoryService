package com.carlson.categoryService;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String name;

    @ManyToOne
    private Category parent;

    @OneToMany
    @JoinColumn(name="categoryid")
    private List<CategoryProduct> categoryProducts;

    public Category() {}
    public Category(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<CategoryProduct> getCategoryProducts() {
        return categoryProducts;
    }

    public void setCategoryProducts(List<CategoryProduct> categoryProducts) {
        this.categoryProducts = categoryProducts;
    }
}