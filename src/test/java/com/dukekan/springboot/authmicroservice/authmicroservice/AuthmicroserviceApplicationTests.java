package com.dukekan.springboot.authmicroservice.authmicroservice;

import com.dukekan.springboot.authmicroservice.authmicroservice.entities.User;
import com.dukekan.springboot.authmicroservice.authmicroservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AuthmicroserviceApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void testUser() {
		assert userRepository.findByUsername("test") != null;
	}

}
