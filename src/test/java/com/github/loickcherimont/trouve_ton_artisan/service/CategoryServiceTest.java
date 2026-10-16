package com.github.loickcherimont.trouve_ton_artisan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.loickcherimont.trouve_ton_artisan.dto.CategoryResponse;
import com.github.loickcherimont.trouve_ton_artisan.model.Category;
import com.github.loickcherimont.trouve_ton_artisan.repository.CategoryRepository;

/**
 * Unit tests for {@link CategoryService}.
 */
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryService categoryService;

	@Test
	@DisplayName("Should return all categories mapped to DTOs")
	void shouldReturnAllCategoriesMappedToDtos() {
		Category batiment = Category.builder().id(1L).name("Bâtiment").slug("batiment").build();
		Category electricite = Category.builder().id(2L).name("Électricité").slug("electricite").build();
		when(categoryRepository.findAll()).thenReturn(List.of(batiment, electricite));

		List<CategoryResponse> result = categoryService.findAll();

		assertThat(result).containsExactly(
				new CategoryResponse(1L, "Bâtiment", "batiment"),
				new CategoryResponse(2L, "Électricité", "electricite"));
		verify(categoryRepository).findAll();
	}

	@Test
	@DisplayName("Should return an empty list when no categories exist")
	void shouldReturnEmptyListWhenNoCategoriesExist() {
		when(categoryRepository.findAll()).thenReturn(List.of());

		List<CategoryResponse> result = categoryService.findAll();

		assertThat(result).isEmpty();
	}

}
