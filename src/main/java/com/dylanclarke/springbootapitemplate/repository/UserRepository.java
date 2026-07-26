package com.dylanclarke.springbootapitemplate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dylanclarke.springbootapitemplate.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}