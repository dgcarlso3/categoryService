package com.carlson.categoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CategoryServiceTests {

    @MockBean
    CategoryRepository mockCategoryRepository;

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
        service.addNewCategory("the name", "the parent");

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        Mockito.verify(mockCategoryRepository).save(captor.capture());

        Category result = captor.getValue();
        assertEquals("the name", result.getName());
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
}
