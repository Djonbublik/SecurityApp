package com.example.spring_basic_auth_example.model;

import com.example.spring_basic_auth_example.entity.Payment;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class UserCreateDto {
    private Long id;

    private String username;

    private String name;

    private String email;

    private Boolean gender;

    private Date birthday;

    private Long balance;

    private Set<Payment> payments;

    private String password;





}
