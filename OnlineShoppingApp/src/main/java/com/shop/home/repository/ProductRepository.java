package com.shop.home.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.home.entity.Products;
import com.shop.home.enums.Categories;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

	Page<Products> findByCategory(Categories category,Pageable pageable);

	Page<Products> findByNameContainingIgnoreCase(String search, Pageable pageable);
}
