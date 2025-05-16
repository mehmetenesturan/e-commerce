package com.ecommerce.product.service;

import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Transactional
    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        kafkaTemplate.send("product-created", savedProduct);
        return savedProduct;
    }

    @Transactional
    public Optional<Product> updateProduct(Long id, Product product) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setStock(product.getStock());
                    existingProduct.setCategoryId(product.getCategoryId());
                    existingProduct.setImageUrl(product.getImageUrl());
                    Product updatedProduct = productRepository.save(existingProduct);
                    kafkaTemplate.send("product-updated", updatedProduct);
                    return updatedProduct;
                });
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresent(product -> {
            productRepository.delete(product);
            kafkaTemplate.send("product-deleted", product);
        });
    }

    @Transactional
    public Optional<Product> updateStock(Long id, Integer quantity) {
        return productRepository.findById(id)
                .map(product -> {
                    int newStock = product.getStock() - quantity;
                    if (newStock < 0) {
                        throw new IllegalStateException("Insufficient stock");
                    }
                    product.setStock(newStock);
                    Product updatedProduct = productRepository.save(product);
                    kafkaTemplate.send("product-stock-updated", updatedProduct);
                    return updatedProduct;
                });
    }
} 