package com.shop.home.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.home.dto.ProductRequest;
import com.shop.home.entity.ProductImage;
import com.shop.home.entity.Products;
import com.shop.home.enums.Categories;
import com.shop.home.service.IProductService;
import com.shop.users.entity.Users;
import com.shop.users.repository.UsersRepository;
import com.shop.users.service.IUserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final IProductService productService;
	private final UsersRepository userRepo;
	
	@GetMapping("/home")
	public String dashboard(@RequestParam(required = false) String search,
												@RequestParam(defaultValue = "0")Integer page,
												@RequestParam(defaultValue = "10") Integer pageSize,
												@RequestParam(defaultValue = "id") String sortBy,
												@RequestParam(defaultValue = "asc") String sortByOrder,
												Model model) {
		Page<Products> allProducts = productService.getAllProducts(search, page, pageSize, sortBy, sortByOrder);
		model.addAttribute("isHome", true);
		model.addAttribute("allProducts", allProducts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPage", allProducts.getTotalPages());
		model.addAttribute("search", search);
		model.addAttribute("sortByOrder", sortByOrder);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("successMessage", "LoggedIn Successfully !");
		return "home";
	}

	@PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String saveProduct(@ModelAttribute ProductRequest req, @RequestParam MultipartFile[] files,
			RedirectAttributes redirect) {
		productService.addNewProduct(req, files);
		redirect.addFlashAttribute("successMessage", "Product is addedd successfully !");
		return "redirect:/products/"+req.category();
	}

	@GetMapping("/image/{productId}/{imageIndex}")
	public ResponseEntity<byte[]> getProductImage(	@PathVariable Long productId,
	        																				@PathVariable int imageIndex) {
	    Products product = productService.getProduct(productId);
	    if (product.getImages() == null || product.getImages().isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    if (imageIndex < 0 || imageIndex >= product.getImages().size()) {
	        return ResponseEntity.notFound().build();
	    }
	    ProductImage img = product.getImages().get(imageIndex);
	    return ResponseEntity.ok()
	            .contentType(MediaType.valueOf(img.getContentType()))
	            .body(img.getImage());
	}

	@GetMapping("/remove/{id}")
	public String removeProduct(@PathVariable Long id, RedirectAttributes redirect) {
		Products product = productService.getProduct(id);
		productService.removeProduct(id);
		redirect.addFlashAttribute("removed", "Product removed successfully !");
		return "redirect:/products/"+product.getCategory();
	}

	@GetMapping("/{category}")
	public String specificCategory(@PathVariable Categories category,
															@RequestParam(required = false) String search,
															@RequestParam(defaultValue = "0")Integer page,
															@RequestParam(defaultValue = "20") Integer pageSize,
															@RequestParam(defaultValue = "id") String sortBy,
															@RequestParam(defaultValue = "asc") String sortByOrder,
															Model model) {
		Page<Products> categoryProducts = productService.getSpecificCategoryProducts(category, search, page, pageSize, sortBy, sortByOrder);
		model.addAttribute("category", category);
		model.addAttribute("specificCategory", categoryProducts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPage", categoryProducts.getTotalPages());
		model.addAttribute("search", search);
		model.addAttribute("sortByOrder", sortByOrder);
		model.addAttribute("sortBy", sortBy);
		return "products";
	}
	
	@GetMapping("/specificProduct/{productId}")
	public String getSpecificProduct(@PathVariable Long productId, Model model) {
		Products product = productService.getProduct(productId);
		model.addAttribute("product", product);
		model.addAttribute("category", product.getCategory());
		return "view_product";
	}
	
	@PostMapping(path = "/edit/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String updateExistingProduct(@PathVariable Long id,
																	  @ModelAttribute ProductRequest req,
																	  @RequestParam MultipartFile[] files,
																			RedirectAttributes redirect) {
		productService.updateExistingProduct(id, req, files);
		redirect.addFlashAttribute("success-update", "Successfully Updated !");
		return "redirect:/products/"+req.category();
	}
	
	@GetMapping("/profile")
	public String profileInfo(Model model) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Users user = userRepo.findByEmail(username).orElseThrow();
		model.addAttribute("username", user.getName());
		model.addAttribute("isProfile", true);
		return "profile";
	}
	
}
