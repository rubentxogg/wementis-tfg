package com.rgg.wementis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rgg.wementis.entities.AuthorityEntity;
import com.rgg.wementis.entities.UserEntity;
import com.rgg.wementis.repositories.AuthorityRepository;
import com.rgg.wementis.repositories.UserRepository;
import com.rgg.wementis.services.interfaces.IUserService;

@Service
public class UserServiceImp implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Integer actualizarPasswordUsuario(UserEntity user, String password) {
		String oldPassword = getUserByUsername(user.getUsername()).getPassword();
		
		if(passwordEncoder.matches(password, oldPassword)) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return 1;
		}
		return 0;
	}

	@Override
	public void borrarUsuario(String username) {
		UserEntity user = userRepository.findById(username).get();
		
		user.setEnabled(false);
		userRepository.save(user);
	}

	@Override
	public void insertarUsuario(UserEntity user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
		AuthorityEntity authority = new AuthorityEntity(user.getUsername(), "ROLE_USUARIO");
		authorityRepository.save(authority);
	}

	@Override
	public UserEntity getUserByUsername(String username) {
		return userRepository.findById(username).get();
	}
}
