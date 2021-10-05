package com.carlson.categoryservice;

import org.springframework.data.repository.CrudRepository;

public interface CategoryProductRepository extends CrudRepository<CategoryProduct, Integer> {
}