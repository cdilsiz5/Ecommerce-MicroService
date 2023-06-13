package com.ecommerce.clientsservice.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String userFirstName;

    private String userLastName;

    private String userEmail;

    private String userPhoneNumber;


}