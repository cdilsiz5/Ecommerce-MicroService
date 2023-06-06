package com.cihantech.userservice.repository;

import com.cihantech.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
        User findByUserEmail(String email);

        Optional<User> findById(Long id);

        void deleteById(Long id);
}
