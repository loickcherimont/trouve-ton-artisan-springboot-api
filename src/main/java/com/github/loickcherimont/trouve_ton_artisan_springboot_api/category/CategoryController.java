package com.github.loickcherimont.trouve_ton_artisan_springboot_api.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for category management endpoints.
 *
 * <p>
 * <strong>Responsibility:</strong> Handles HTTP requests for category queries.
 * Exposes a read endpoint to retrieve all registered categories.
 * </p>
 *
 * @see com.github.loickcherimont.trouve_ton_artisan_springboot_api.category.CategoryService
 * @see com.github.loickcherimont.trouve_ton_artisan_springboot_api.category.Category
 */
@Tag(name = "Categories", description = "API for categories management")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get all categories", description = "Get all categories when they exist.")
    @ApiResponse(responseCode = "200", description = "Categories retrieved")
    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }
}