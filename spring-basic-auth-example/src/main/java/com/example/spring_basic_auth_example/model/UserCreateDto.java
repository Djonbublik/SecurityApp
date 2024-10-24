package com.example.spring_basic_auth_example.model;

import com.example.spring_basic_auth_example.entity.Gender;
import com.example.spring_basic_auth_example.entity.Role;
import com.example.spring_basic_auth_example.entity.RoleType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class UserCreateDto {

    private String username;

    private String name;

    private String email;

    private Gender gender;

    private Date birthday;

    private String password;

    private RoleType roleType;
}
