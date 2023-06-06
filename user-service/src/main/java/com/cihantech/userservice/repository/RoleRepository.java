package com.cihantech.userservice.repository;

import com.cihantech.userservice.entity.Role;
import com.cihantech.userservice.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByUserRoleEquals(UserRole name);
}
