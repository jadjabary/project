package io.transferfile.sftp.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.transferfile.sftp.controller.UserData;
import io.transferfile.sftp.repository.UserRepository;

@Service("userService")
public class UserService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public UserData findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	
	public UserData findIPbyUser(int username) {
		return entityManager.find(UserData.class, username);
	}

	public void saveUser(UserData username) {
		username.setPassword(bCryptPasswordEncoder.encode(username.getPassword()));
		username.setActive(1);
		userRepository.save(username);
	}
}
