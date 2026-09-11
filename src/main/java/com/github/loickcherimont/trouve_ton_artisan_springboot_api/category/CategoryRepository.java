package com.github.loickcherimont.trouve_ton_artisan_springboot_api.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
