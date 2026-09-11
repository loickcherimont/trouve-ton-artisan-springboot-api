package com.github.loickcherimont.trouve_ton_artisan_springboot_api.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void shouldReturnListOfAllCategoriesWhenCategoriesExist() {
        List<Category> expectedCategories = twoCategories();
        when(categoryRepository.findAll()).thenReturn(expectedCategories);

        List<Category> actualCategories = categoryService.findAll();

        assertThat(actualCategories).containsExactlyElementsOf(expectedCategories);
    }

    @Test
    void shouldReturnEmptyListWhenNoCategoriesExist() {
        when(categoryRepository.findAll()).thenReturn(List.of());

        List<Category> actualCategories = categoryService.findAll();

        assertThat(actualCategories).isEmpty();
    }

    private static List<Category> twoCategories() {
        return List.of(
                Category.builder().id(1L).name("Plomberie").slug("plomberie").build(),
                Category.builder().id(2L).name("Électricité").slug("electricite").build()
        );
    }
}