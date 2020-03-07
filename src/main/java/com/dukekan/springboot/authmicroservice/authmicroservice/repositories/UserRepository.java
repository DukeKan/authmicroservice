package com.dukekan.springboot.authmicroservice.authmicroservice.repositories;

import com.dukekan.springboot.authmicroservice.authmicroservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
}
