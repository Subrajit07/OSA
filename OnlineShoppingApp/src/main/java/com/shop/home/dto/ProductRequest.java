package com.shop.home.dto;

import com.shop.home.enums.Categories;
import com.shop.home.enums.Stocks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(@NotBlank
														String name,
														@NotBlank
														String description,
														@NotBlank
														String brand,
														@NotNull
														Double price,
														@NotNull
														Double discount,
														@NotNull
														Categories category,
														@NotNull
														Stocks stock) {

}
