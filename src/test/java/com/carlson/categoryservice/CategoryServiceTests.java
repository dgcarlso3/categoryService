package com.carlson.categoryservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CategoryServiceTests {

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    CategoryProductRepository categoryProductRepository;

    @Spy
    private CategoryService service;

    @BeforeEach
    public void beforeEach() {
        service = new CategoryService(categoryRepository, categoryProductRepository);
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
        Mockito.when(service.getCategoryByName("the parent")).thenReturn(parent);
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
        Mockito.when(categoryRepository.findAll()).thenReturn(expected);

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
        Mockito.when(categoryRepository.findByName("foo")).thenReturn(expected);

        Category result = service.getCategoryByName("foo");
        assertEquals(expected, result);
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

}
