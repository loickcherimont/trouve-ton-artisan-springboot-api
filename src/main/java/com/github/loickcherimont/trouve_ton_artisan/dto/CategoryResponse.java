package com.github.loickcherimont.trouve_ton_artisan.dto;

import com.github.loickcherimont.trouve_ton_artisan.model.Category;

/**
 * Public representation of a category returned by the API.
 *
 * <p>Controllers never expose {@link Category} JPA entities directly:
 * the entity is mapped to this record at the service boundary so that
 * the persistence model can evolve without breaking the API contract.</p>
 *
 * @param id   unique identifier of the category
 * @param name human-readable name, e.g. "Bâtiment"
 * @param slug URL-friendly unique key, e.g. "batiment"
 */
public record CategoryResponse(Long id, String name, String slug) {

	/**
	 * Maps a {@link Category} entity to its public DTO representation.
	 *
	 * @param category the entity to map, must not be {@code null}
	 * @return an immutable DTO copy of the entity
	 */
	public static CategoryResponse from(Category category) {
		return new CategoryResponse(category.getId(), category.getName(), category.getSlug());
	}

}
