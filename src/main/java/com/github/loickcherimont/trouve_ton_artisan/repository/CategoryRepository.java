package com.github.loickcherimont.trouve_ton_artisan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.loickcherimont.trouve_ton_artisan.model.Category;

/**
 * Spring Data JPA repository for {@link Category} entities.
 *
 * <p>Provides standard CRUD operations plus {@code findAll()} for
 * listing every category.</p>
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
