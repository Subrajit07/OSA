package com.shop.users.service;


//import org.springframework.security.core.userdetails.UserDetailsService;

import com.shop.users.dto.LoginRequest;
import com.shop.users.dto.SignUpRequest;
import com.shop.users.entity.Users;

public interface IUserService {

	public void saveUser(SignUpRequest request);
	
}
