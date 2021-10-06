package com.carlson.categoryservice;

import com.carlson.categoryservice.webservices.WebServiceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTests {

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    CategoryProductRepository categoryProductRepository;

    @MockBean
    WebServiceHelper webServiceHelper;

    @Spy
    private CategoryService service;

    @BeforeEach
    public void beforeEach() {
        when(categoryRepository.existsById(any())).thenReturn(true);
        when(webServiceHelper.getProductExists(any())).thenReturn(true);
        service = new CategoryService(categoryRepository, categoryProductRepository, webServiceHelper);
    }

    @Test
    public void addNewCategory_returnsSaved() {
        String result = service.addNewCategory("", "");

        assertEquals("Saved", result);
    }

    @Test
    public void addNewCategory_callsSavesOnRepository() {
        service.addNewCategory("the name", "the parent");

        Mockito.verify(categoryRepository).save(Mockito.any());
    }

    @Test
    public void addNewCategory_savesObjectWithCorrectParams() {
        Category parent = new Category("the parent");
        parent.setId(42);
        when(service.getCategoryByName("the parent")).thenReturn(parent);
        service.addNewCategory("the name", "the parent");

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        Mockito.verify(categoryRepository).save(captor.capture());

        Category result = captor.getValue();
        assertEquals("the name", result.getName());
        assertEquals(result.getParent().getId(), 42);
    }

    @Test
    public void getAllCategories_callsRepository() {
        service.getAllCategories();

        Mockito.verify(categoryRepository).findAll();
    }

    @Test
    public void getAllCategories_returnsRepositoryResults() {
        Iterable<Category> expected = new ArrayList<>();
        when(categoryRepository.findAll()).thenReturn(expected);

        Iterable<Category> result = service.getAllCategories();
        assertEquals(expected, result);
    }

    @Test
    public void getCategoryByName_callsRepositoryWithParameter() {
        service.getCategoryByName("foo");

        Mockito.verify(categoryRepository).findByName("foo");
    }

    @Test
    public void getCategoryByName_returnsRepositoryResults() {
        Category expected = new Category("foo");
        when(categoryRepository.findByName("foo")).thenReturn(expected);

        Category result = service.getCategoryByName("foo");
        assertEquals(expected, result);
    }

    @Test
    public void getCategoryByName_categryNotFound() {
        when(categoryRepository.findByName("foo")).thenReturn(null);

        Category result = service.getCategoryByName("foo");
        assertNotNull(result);
        assertNotNull(result.getCategoryProducts());
    }

    @Test
    public void addProductToCategory_returnsSaved() {
        String result = service.addProductToCategory(42, 24);

        assertEquals("Added", result);
    }

    @Test
    public void addProductToCategory_callsSavesOnRepository() {
        service.addProductToCategory(42,24);

        Mockito.verify(categoryProductRepository).save(Mockito.any());
    }

    @Test
    public void addProductToCategory_savesObjectWithCorrectParams() {
        service.addProductToCategory(42, 24);

        ArgumentCaptor<CategoryProduct> captor = ArgumentCaptor.forClass(CategoryProduct.class);
        Mockito.verify(categoryProductRepository).save(captor.capture());

        CategoryProduct result = captor.getValue();
        assertEquals(42, result.getCategoryId());
        assertEquals(24, result.getProductId());
    }

    @Test
    public void addProductToCategory_throwsProductNotFoundException() {
        when(categoryRepository.existsById(42)).thenReturn(true);
        when(webServiceHelper.getProductExists(24)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> {
            service.addProductToCategory(42, 24);
        });
    }

    @Test
    public void addProductToCategory_throwsCategoryNotFoundException() {
        when(categoryRepository.existsById(42)).thenReturn(false);

        assertThrows(CategoryNotFoundException.class, () -> {
            service.addProductToCategory(42, 24);
        });
    }
}
