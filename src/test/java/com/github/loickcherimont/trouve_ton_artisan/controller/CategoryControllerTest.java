package com.github.loickcherimont.trouve_ton_artisan.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.github.loickcherimont.trouve_ton_artisan.dto.CategoryResponse;
import com.github.loickcherimont.trouve_ton_artisan.exception.GlobalExceptionHandler;
import com.github.loickcherimont.trouve_ton_artisan.service.CategoryService;

/**
 * Web layer tests for {@link CategoryController}.
 */
@WebMvcTest(CategoryController.class)
@Import(GlobalExceptionHandler.class)
class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CategoryService categoryService;

	@Test
	@DisplayName("GET /api/categories should return 200 with the list of categories")
	void getCategoriesShouldReturn200WithCategories() throws Exception {
		when(categoryService.findAll()).thenReturn(List.of(
				new CategoryResponse(1L, "Bâtiment", "batiment"),
				new CategoryResponse(2L, "Électricité", "electricite")));

		mockMvc.perform(get("/api/categories"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Bâtiment"))
				.andExpect(jsonPath("$[0].slug").value("batiment"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("Électricité"))
				.andExpect(jsonPath("$[1].slug").value("electricite"));
	}

	@Test
	@DisplayName("GET /api/categories should return 200 with an empty list when no categories exist")
	void getCategoriesShouldReturn200WithEmptyList() throws Exception {
		when(categoryService.findAll()).thenReturn(List.of());

		mockMvc.perform(get("/api/categories"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());
	}

	@Test
	@DisplayName("GET /api/categories should return 500 with an error body when the service fails")
	void getCategoriesShouldReturn500WithErrorBodyWhenServiceFails() throws Exception {
		when(categoryService.findAll()).thenThrow(new RuntimeException("boom"));

		mockMvc.perform(get("/api/categories"))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.error").value(500))
				.andExpect(jsonPath("$.message").isNotEmpty());
	}

}
