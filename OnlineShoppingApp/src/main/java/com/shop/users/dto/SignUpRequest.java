package com.shop.users.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(@NotBlank(message = "First name is required")
														String firstName, 
														@NotBlank(message = "Last name is required")
														String lastName,
														@NotBlank(message = "Email must be required")
														@Email(message = "Invalid email format / missing symbol")
														String email,
														@NotBlank(message="Password is required")
														@Size(min = 7,message = "Password minimun 7 character")
														String password,
														@NotBlank(message="Password is required")
														@Size(min = 7,message = "Confirm Password minimun 7 character")
														String confirmPassword,
														@NotBlank(message="Phone number is required")
														@Size(min = 10,max = 13)
														Long contact,
														@NotBlank(message="Address is required")
														String address) {
}
