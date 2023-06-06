package com.cihantech.userservice.service;

import com.cihantech.userservice.dto.UserDto;
import com.cihantech.userservice.entity.Role;
import com.cihantech.userservice.entity.User;
import com.cihantech.userservice.entity.UserRole;
import com.cihantech.userservice.exception.NotFoundException;
import com.cihantech.userservice.repository.RoleRepository;
import com.cihantech.userservice.repository.UserRepository;
import com.cihantech.userservice.request.CreateUpdateUserRequest;
import com.cihantech.userservice.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.cihantech.userservice.mapper.UserMapper.USER_MAPPER;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public boolean verifyCredentials(LoginRequest loginRequest) {
        log.info("Verifying credentials for userEmail: {}", loginRequest.getUserEmail());
        User user = findByUsername(loginRequest.getUserEmail());
        if (user != null && passwordEncoder.matches(loginRequest.getUserPassword(), user.getUserEmail())) {
            log.info("Credentials verified for userEmail: {}", loginRequest.getUserEmail());
            return true;
        } else {
            log.error("Failed to verify credentials for userEmail: {}", loginRequest.getUserEmail());
            return false;
        }
    }

    private User findByUsername(String userEmail) {
        log.info("Fetching user by userEmail: {}", userEmail);
        User user = userRepository.findByUserEmail(userEmail);
        log.info("Fetched user: {}", user);
        return user;
    }

    public UserDto getUserById(Long id){
        log.info("Fetching user by id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User not found with id: {}", id);
            return new NotFoundException("User not found.");
        });
        log.info("Fetched user: {}", user);
        return USER_MAPPER.toUserDto(user);
    }

    public List<UserDto> getUserList(){
        log.info("Fetching all users");
        List<UserDto> users = USER_MAPPER.toUserDtoList(userRepository.findAll());
        log.info("Fetched {} users", users.size());
        return users;
    }

    public void deleteUser(Long id){
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }

    public UserDto updateUser(Long id, CreateUpdateUserRequest request) {
        log.info("Updating user with id: {} and request: {}", id, request);
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User not found with id: {}", id);
            return new NotFoundException("User is not found.");
        });
        USER_MAPPER.updateUser(user, request);
        User updatedUser = userRepository.save(user);
        log.info("Updated user: {}", updatedUser);
        return USER_MAPPER.toUserDto(updatedUser);
    }

    public UserDto createUser(CreateUpdateUserRequest request) {
        log.info("Creating user with request: {}", request);
        User user = USER_MAPPER.createUser(request);
        user.setUserPassword(passwordEncoder.encode(request.getUserPassword()));
        Role userRole = roleRepository.findByUserRoleEquals(UserRole.ROLE_ADMIN).orElseThrow(() -> {
            log.error("Failed to find ROLE_ADMIN");
            return new RuntimeException("Something went wrong");
        });
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        log.info("Created user: {}", savedUser);
        return USER_MAPPER.toUserDto(savedUser);
    }
}
