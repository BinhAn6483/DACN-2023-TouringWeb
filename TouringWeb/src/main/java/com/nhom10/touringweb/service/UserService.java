package com.nhom10.touringweb.service;


import com.nhom10.touringweb.model.user.User;

import com.nhom10.touringweb.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
