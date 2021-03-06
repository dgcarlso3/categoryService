package com.carlson.categoryservice;

import javax.persistence.*;

@Entity
public class CategoryProduct {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private Integer productid;
    private Integer categoryid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productid;
    }

    public void setProductId(Integer productid) {
        this.productid = productid;
    }

    public Integer getCategoryId() {
        return categoryid;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryid = categoryId;
    }
}