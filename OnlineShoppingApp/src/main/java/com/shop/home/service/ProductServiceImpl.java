package com.shop.home.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shop.exceptions.ProductNotFound;
import com.shop.home.dto.ProductRequest;
import com.shop.home.entity.ProductImage;
import com.shop.home.entity.ProductImage.ProductImageBuilder;
import com.shop.home.entity.Products;
import com.shop.home.enums.Categories;
import com.shop.home.repository.ProductImageRepository;
import com.shop.home.repository.ProductRepository;
import com.shop.users.entity.Users;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

	private final ProductRepository productRepo;
	private final ProductImageRepository imageRepo;

	@Override
	public void addNewProduct(ProductRequest req, MultipartFile[] files) {
		Products product = generateEntity(req);
		prepareProductImage(product, files);
		productRepo.save(product);
	}

	private Products generateEntity(ProductRequest req) {
		return Products.builder().name(req.name()).description(req.description()).brand(req.brand())
				.category(req.category()).stock(req.stock()).price(req.price()).discount(req.discount()).build();
	}

	@SneakyThrows
	private void prepareProductImage(Products product, MultipartFile[] files) {
		List<ProductImage> imageList = new ArrayList<ProductImage>();
		for (MultipartFile file : files) {
			ProductImage productImage = ProductImage.builder().image(file.getBytes())
					.fileName(file.getOriginalFilename()).contentType(file.getContentType()).product(product).build();
			imageList.add(productImage);
		}
		product.setImages(imageList);
	}
	
	@SneakyThrows
	private void updateProductImage(Products product,MultipartFile[] files) {
		if(files!=null && files.length>0) {
			List<ProductImage> images = product.getImages();
			images.clear();
			for(MultipartFile file:files) {
				ProductImage productImage = ProductImage.builder().image(file.getBytes())
						.fileName(file.getOriginalFilename()).contentType(file.getContentType()).product(product).build();
				images.add(productImage);
			}
			product.setImages(images);
		}
	}

	@Override
	@SneakyThrows
	public Page<Products> getAllProducts(String search, Integer page, Integer pageSize, String sortBy,
			String sortByOrder) {
		Pageable pageable;
		if (!StringUtils.isBlank(sortBy)) {
			sortBy = prepareSortBy(sortBy);
			Sort sort = "ASC".equalsIgnoreCase(sortByOrder) ? Sort.by(sortBy).ascending()
					: Sort.by(sortBy).descending();
			pageable = PageRequest.of(page, pageSize, sort);
		} else {
			pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
		}
		
		return StringUtils.isNotBlank(search) ?
						productRepo.findByNameContainingIgnoreCase(search, pageable) : 
						productRepo.findAll(pageable);
	}

	private String prepareSortBy(String sortBy) {
		return switch (sortBy) {
		case "price" -> "price";
		case "discount" -> "discount";
		default -> "id";
		};
	}

	@Override
	public Products getProduct(Long id) {
		Products product = productRepo.findById(id).orElseThrow();
		return product;
	}

	@Override
	public void removeProduct(Long id) {
		Products product = productRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
		List<ProductImage> images = product.getImages();
		if (images != null && !images.isEmpty()) {
			imageRepo.deleteAll(images);
		}
		productRepo.delete(product);
	}

	@Override
	public Page<Products> getSpecificCategoryProducts(Categories category, String search, Integer page,
			Integer pageSize, String sortBy, String sortByOrder) {
		Pageable pageable;
		if(!StringUtils.isBlank(sortBy)) {
			sortBy=prepareSortBy(sortBy);
			Sort sort="ASC".equalsIgnoreCase(sortByOrder)?Sort.by(Direction.ASC, sortBy):Sort.by(Direction.DESC, sortBy);
			pageable=PageRequest.of(page, pageSize, sort);
		}else {
			pageable=PageRequest.of(page, pageSize, Sort.by("id").ascending());
		}
		return  productRepo.findByCategory(category,pageable);
	}

	@Override
	@SneakyThrows
	public void updateExistingProduct(Long id, ProductRequest req, MultipartFile[] file) {
		Products product = productRepo.findById(id).orElseThrow(()->new ProductNotFound("Product is not found"));
		setProductDetails(product, req);
		updateProductImage(product, file);
		productRepo.save(product);
	}
	
	private void setProductDetails(Products product, ProductRequest req) {
		product.setBrand(req.brand());
		product.setCategory(req.category());
		product.setName(req.name());
		product.setPrice(req.price());
		product.setDiscount(req.discount());
		product.setDescription(req.description());
		product.setStock(req.stock());
	}
}
