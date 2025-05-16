package com.ecommerce.product.service;

import com.ecommerce.product.model.Category;
import com.ecommerce.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getSubCategories(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    public List<Category> searchCategories(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    public Category createCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        kafkaTemplate.send("category-created", savedCategory);
        return savedCategory;
    }

    @Transactional
    public Optional<Category> updateCategory(Long id, Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    existingCategory.setDescription(category.getDescription());
                    existingCategory.setParentId(category.getParentId());
                    Category updatedCategory = categoryRepository.save(existingCategory);
                    kafkaTemplate.send("category-updated", updatedCategory);
                    return updatedCategory;
                });
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresent(category -> {
            // Check if category has subcategories
            List<Category> subCategories = categoryRepository.findByParentId(id);
            if (!subCategories.isEmpty()) {
                throw new IllegalStateException("Cannot delete category with subcategories");
            }
            categoryRepository.delete(category);
            kafkaTemplate.send("category-deleted", category);
        });
    }
} 