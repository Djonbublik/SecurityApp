package com.example.spring_basic_auth_example.controller;

import com.example.spring_basic_auth_example.model.PaymentDto;
import com.example.spring_basic_auth_example.model.UserCreateDto;
import com.example.spring_basic_auth_example.model.UserDto;
import com.example.spring_basic_auth_example.service.PaymentServiceImpl;
import com.example.spring_basic_auth_example.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final PaymentServiceImpl paymentServiceImpl;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public UserDto getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userServiceImpl.getByUsername(userDetails.getUsername());
    }
    @PutMapping ("/update")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<String> update(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserCreateDto userCreateDto){
        userServiceImpl.update(userCreateDto, userDetails.getUsername());
        return ResponseEntity.ok("User update");
    }

    @GetMapping(value="/payments/{pageNumber}/{pageSize}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Page<PaymentDto> getPayments(@AuthenticationPrincipal UserDetails userDetails,
                                     @PathVariable("pageNumber") final Integer pageNumber,
                                     @PathVariable("pageSize") final Integer pageSize){
        return userServiceImpl.getUserPayment(pageNumber, pageSize, userDetails.getUsername());
   }

    @DeleteMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<String > deleteUser(@AuthenticationPrincipal UserDetails userDetails){
        userServiceImpl.delete(userDetails.getUsername());
        return ResponseEntity.ok("User delete");
    }

    @PostMapping("/createPayment")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<String> createPayment(@AuthenticationPrincipal UserDetails userDetails,@RequestBody PaymentDto paymentDto){
        paymentServiceImpl.create(paymentDto, userDetails.getUsername());
        return ResponseEntity.ok("The payment was successful");
    }

}
