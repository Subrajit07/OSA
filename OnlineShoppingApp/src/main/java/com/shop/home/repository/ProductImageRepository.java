package com.shop.home.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.home.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
