package com.shop.users.service;

import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
/*
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

*/
import org.springframework.stereotype.Service;
import com.shop.users.dto.SignUpRequest;
import com.shop.users.entity.Users;
import com.shop.users.enums.UserRoles;
import com.shop.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl 
	implements IUserService,UserDetailsService{

	private final UsersRepository usersRepository;
	private final PasswordEncoder encoder;

	@Override
	public void saveUser(SignUpRequest request) {
		String password = "";
		if (request.password().equals(request.confirmPassword())) {
			password = request.password();
		}
		Users user = Users.builder().name(request.firstName() + " " + request.lastName()).email(request.email())
				.password(encoder.encode(password)).address(request.address()).contact(request.contact())
				.roles(Set.of(UserRoles.CUSTOMER)).build();
		usersRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Users user = usersRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
		return User.builder().username(user.getEmail()).password(user.getPassword())
				.authorities(user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.name())).toList())
				.build();
	}
}
