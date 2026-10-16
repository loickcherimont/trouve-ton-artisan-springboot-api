package com.github.loickcherimont.trouve_ton_artisan.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.github.loickcherimont.trouve_ton_artisan.TestcontainersConfiguration;
import com.github.loickcherimont.trouve_ton_artisan.model.Category;

/**
 * Persistence integration tests for {@link CategoryRepository}, running against a real PostgreSQL container.
 * Executed by Maven Failsafe during {@code ./mvnw verify} (pattern {@code *IT}).
 */
@DataJpaTest
@Import(TestcontainersConfiguration.class)
class CategoryRepositoryIT {

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	@DisplayName("Should save and retrieve categories")
	void shouldSaveAndRetrieveCategories() {
		Category batiment = Category.builder().name("Bâtiment").slug("batiment").build();
		Category electricite = Category.builder().name("Électricité").slug("electricite").build();
		categoryRepository.saveAll(List.of(batiment, electricite));

		List<Category> categories = categoryRepository.findAll();

		assertThat(categories)
				.extracting(Category::getName, Category::getSlug)
				.containsExactlyInAnyOrder(
						Tuple.tuple("Bâtiment", "batiment"),
						Tuple.tuple("Électricité", "electricite"));
	}

	@Test
	@DisplayName("Should return an empty list when no categories are stored")
	void shouldReturnEmptyListWhenNoCategoriesAreStored() {
		assertThat(categoryRepository.findAll()).isEmpty();
	}

}
