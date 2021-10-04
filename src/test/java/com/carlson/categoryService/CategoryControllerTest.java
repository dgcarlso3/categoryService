package com.carlson.categoryService;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CategoryService mockCategoryService;

    @Test
    public void addCategory_returnsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/categories/add")
                        .param("name", "")
                        .param("parentName", ""))
                .andExpect(status().isOk());
    }

    @Test
    public void addCategory_returnsCorrectResponse() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/categories/add")
                        .param("name", "")
                        .param("parentName", ""))
                .andExpect(content().string(equalTo("Saved")));
    }

    @Test
    public void addCategory_callsServiceWithNameAndParent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/categories/add")
                        .param("name", "This name")
                        .param("parentName", "This parent"));

        Mockito.verify(mockCategoryService).addNewCategory("This name", "This parent");
    }

    @Test
    public void getAllCategories_returnsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/categories"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCategories_callsService() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/categories"));

        Mockito.verify(mockCategoryService).getAllCategories();
    }

    @Test
    public void getAllCategories_returnsCorrectData() throws Exception {
        List<Category> theList = new ArrayList<>();
        theList.add(new Category("name1"));
        theList.add(new Category("name2"));
        Mockito.when(mockCategoryService.getAllCategories()).thenReturn(theList);

        mvc.perform(MockMvcRequestBuilders.get("/categories"))
                .andExpect(jsonPath("$[*].name").value(containsInAnyOrder("name1", "name2")));
    }

    @Test
    public void getCategoryByName_returnsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/categories/foo"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCategoryByName_callsService() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/categories/foo"));

        Mockito.verify(mockCategoryService).getCategoryByName("foo");
    }

    @Test
    public void getCategoryByName_returnsCorrectData() throws Exception {
        Category expected = new Category("foof");
        Mockito.when(mockCategoryService.getCategoryByName("foo")).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.get("/categories/foo"))
                .andExpect(jsonPath("$.name").value(containsString("foof")));
    }
}