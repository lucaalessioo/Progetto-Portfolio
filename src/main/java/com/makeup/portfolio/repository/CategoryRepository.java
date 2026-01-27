package com.makeup.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.makeup.portfolio.model.Categories;

public interface CategoryRepository extends JpaRepository<Categories,Long> {
    
}
