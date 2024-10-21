package com.example.spring_basic_auth_example.controller;

import com.example.spring_basic_auth_example.model.UserCreateDto;
import com.example.spring_basic_auth_example.service.PaymentServiceImpl;
import com.example.spring_basic_auth_example.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserServiceImpl userServiceImpl;
    private final PaymentServiceImpl paymentServiceImpl;

    @PostMapping(value = "/account")
    public void createNewAccount(@RequestBody UserCreateDto userCreateDto){
        userServiceImpl.create(userCreateDto);
    }
}
