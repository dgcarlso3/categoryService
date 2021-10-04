package com.carlson.categoryService;

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
    CategoryRepository mockCategoryRepository;

    @Spy
    private CategoryService service;

    @BeforeEach
    public void beforeEach() {
        service = new CategoryService(mockCategoryRepository);
    }

    @Test
    public void addNewCategory_returnsSaved() {
        String result = service.addNewCategory("", "");

        assertEquals("Saved", result);
    }

    @Test
    public void addNewCategory_callsSavesOnRepository() {
        service.addNewCategory("the name", "the parent");

        Mockito.verify(mockCategoryRepository).save(Mockito.any());
    }

    @Test
    public void addNewCategory_savesObjectWithCorrectParams() {
        Category parent = new Category("the parent");
        parent.setId(42);
        Mockito.when(service.getCategoryByName("the parent")).thenReturn(parent);
        service.addNewCategory("the name", "the parent");

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        Mockito.verify(mockCategoryRepository).save(captor.capture());

        Category result = captor.getValue();
        assertEquals("the name", result.getName());
        assertEquals(result.getParent().getId(), 42);
    }

    @Test
    public void getAllCategories_callsRepository() {
        service.getAllCategories();

        Mockito.verify(mockCategoryRepository).findAll();
    }

    @Test
    public void getAllCategories_returnsRepositoryResults() {
        Iterable<Category> expected = new ArrayList<>();
        Mockito.when(mockCategoryRepository.findAll()).thenReturn(expected);

        Iterable<Category> result = service.getAllCategories();
        assertEquals(expected, result);
    }

    @Test
    public void getCategoryByName_callsRepositoryWithParameter() {
        service.getCategoryByName("foo");

        Mockito.verify(mockCategoryRepository).findByName("foo");
    }

    @Test
    public void getCategoryByName_returnsRepositoryResults() {
        Category expected = new Category("foo");
        Mockito.when(mockCategoryRepository.findByName("foo")).thenReturn(expected);

        Category result = service.getCategoryByName("foo");
        assertEquals(expected, result);
    }}
