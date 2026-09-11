package com.github.loickcherimont.trouve_ton_artisan_springboot_api.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @Test
    void shouldReturnAllCategoriesWhenTheyExistWithHttp200Ok() throws Exception {
        List<Category> expectedCategories = List.of(
                Category.builder().id(1L).name("Plomberie").slug("plomberie").build(),
                Category.builder().id(2L).name("Électricité").slug("electricite").build()
        );
        when(categoryService.findAll()).thenReturn(expectedCategories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(content().json("""
                [
                  {"id": 1, "name": "Plomberie", "slug": "plomberie"},
                  {"id": 2, "name": "Électricité", "slug": "electricite"}
                ]
                """));
    }

    @Test
    void shouldReturnEmptyListWhenNoCategoriesExistWithHttp200Ok() throws Exception {
        when(categoryService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}