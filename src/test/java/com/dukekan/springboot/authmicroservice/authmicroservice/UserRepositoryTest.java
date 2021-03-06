package com.dukekan.springboot.authmicroservice.authmicroservice;

import com.dukekan.springboot.authmicroservice.authmicroservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void testLoadUser() {
		assertThat(userRepository.findByUsername("test") != null);
	}
}
