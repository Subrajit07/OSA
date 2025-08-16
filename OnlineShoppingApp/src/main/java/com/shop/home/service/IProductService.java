package com.shop.home.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.shop.home.dto.ProductRequest;
import com.shop.home.entity.Products;
import com.shop.home.enums.Categories;

public interface IProductService {

	public void addNewProduct(ProductRequest req, MultipartFile[] file);
	
	public void updateExistingProduct(Long id, ProductRequest req, MultipartFile[] file);
	
	public Page<Products> getAllProducts(String search, Integer page, Integer pageSize, String sortBy, String sortByOrder);
	
	public Products getProduct(Long id);
	
	public void removeProduct(Long id);
	
	public Page<Products> getSpecificCategoryProducts(Categories category, String search, Integer page,
			Integer pageSize, String sortBy, String sortByOrder);
}
