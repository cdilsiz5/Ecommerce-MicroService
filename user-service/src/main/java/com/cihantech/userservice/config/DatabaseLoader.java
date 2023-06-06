package com.cihantech.userservice.config;

import com.cihantech.userservice.entity.Role;
import com.cihantech.userservice.entity.UserRole;
import com.cihantech.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public DatabaseLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        Role adminRole = new Role();
        adminRole.setUserRole(UserRole.ROLE_ADMIN);
        roleRepository.save(adminRole);
    }
}
