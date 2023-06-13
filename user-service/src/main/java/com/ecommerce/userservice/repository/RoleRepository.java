package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.entity.Role;
import com.ecommerce.userservice.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByUserRoleEquals(UserRole name);
}
