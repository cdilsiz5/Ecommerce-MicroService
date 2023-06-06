package com.cihantech.clientsservice.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private int id;

    private String userFirstName;

    private String userLastName;

    private String userEmail;

    private String userPhoneNumber;


}