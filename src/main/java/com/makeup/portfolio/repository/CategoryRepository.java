package com.makeup.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.makeup.portfolio.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
