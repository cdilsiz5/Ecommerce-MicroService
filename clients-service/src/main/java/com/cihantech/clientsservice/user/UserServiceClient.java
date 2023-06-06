package com.cihantech.clientsservice.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient("user-service")
public interface UserServiceClient {

    @GetMapping("/api/user/{id}")
    @ResponseStatus(HttpStatus.OK)
     UserDto getUser(@PathVariable(value = "id") Long id) ;
}
