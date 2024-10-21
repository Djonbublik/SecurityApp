package com.example.spring_basic_auth_example.controller;

import com.example.spring_basic_auth_example.model.PaymentDto;
import com.example.spring_basic_auth_example.service.PaymentServiceImpl;
import com.example.spring_basic_auth_example.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserServiceImpl userServiceImpl;
    private final PaymentServiceImpl paymentServiceImpl;

    @PostMapping("/createPayment")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> createPayment(@RequestBody PaymentDto paymentDto){
        paymentServiceImpl.create(paymentDto, paymentDto.getUsername());
        return ResponseEntity.ok("The payment was successful");
    }
}
