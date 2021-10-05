package com.codecool.soundblastr.repository;

import com.codecool.soundblastr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
