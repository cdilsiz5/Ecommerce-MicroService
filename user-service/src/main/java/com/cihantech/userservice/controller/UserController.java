package com.cihantech.userservice.controller;

import com.cihantech.userservice.dto.UserDto;
import com.cihantech.userservice.request.CreateUpdateUserRequest;
import com.cihantech.userservice.request.LoginRequest;
import com.cihantech.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    @PostMapping("/verify")
    public Boolean verifyCredentials(@RequestBody LoginRequest loginRequest) {
       return userService.verifyCredentials(loginRequest);
    }
    private final UserService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable Long id) {
        return service.getUserById(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable Long id,@Valid @RequestBody CreateUpdateUserRequest request){
        return service.updateUser(id,request);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUserList() {
        return service.getUserList();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable  Long id){
        service.deleteUser(id);
    }
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser( @Valid @RequestBody CreateUpdateUserRequest userRequest){
        return service.createUser(userRequest);
    }

}
