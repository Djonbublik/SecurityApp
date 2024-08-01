package com.example.spring_basic_auth_example.controller;

import com.example.spring_basic_auth_example.entity.Role;
import com.example.spring_basic_auth_example.entity.RoleType;
import com.example.spring_basic_auth_example.model.PaymentDto;
import com.example.spring_basic_auth_example.model.UserCreateDto;
import com.example.spring_basic_auth_example.service.PaymentService;
import com.example.spring_basic_auth_example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;
    private final PaymentService paymentService;

    @PostMapping(value = "/account")
    public void createNewAccount(@RequestBody UserCreateDto userCreateDto, @RequestParam RoleType roleType){
        userService.create(userCreateDto, Role.from(roleType));
    }

    @PostMapping("/payment")
    public ResponseEntity<String> createPayment(@RequestBody PaymentDto paymentDto){
        paymentService.create(paymentDto);
        return ResponseEntity.ok("The payment was successful");
    }
}
