package com.github.loickcherimont.trouve_ton_artisan.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A professional category grouping artisans by trade
 * (e.g. "Bâtiment", "Électricité").
 *
 * <p>Mapped to the {@code category} table. A category is identified by a
 * unique {@code slug} used in URLs and JSON payloads.</p>
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

	/** Unique identifier, generated automatically. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Human-readable name of the category, e.g. "Bâtiment". */
	@Column(nullable = false)
	private String name;

	/** URL-friendly unique key derived from the name, e.g. "batiment". */
	@Column(nullable = false, unique = true)
	private String slug;

}
