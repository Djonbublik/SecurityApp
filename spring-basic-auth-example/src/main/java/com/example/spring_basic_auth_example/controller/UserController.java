package com.example.spring_basic_auth_example.controller;


import com.example.spring_basic_auth_example.model.PaymentDto;
import com.example.spring_basic_auth_example.model.UserDto;
import com.example.spring_basic_auth_example.model.UserCreateDto;
import com.example.spring_basic_auth_example.service.PaymentService;
import com.example.spring_basic_auth_example.service.UserService;
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
    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public UserDto getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getByUsername(userDetails.getUsername());
    }
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<String> update(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserCreateDto userCreateDto){
        userService.update(userCreateDto, userDetails.getUsername());
        return ResponseEntity.ok("User update");
    }

    @GetMapping("/payments")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Page<PaymentDto> getPayments(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Integer page){
        return userService.getUserPayment(page - 1,10, userDetails);
    }

    @DeleteMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<String > deleteUser(@AuthenticationPrincipal UserDetails userDetails){
        userService.delete(userDetails.getUsername());
        return ResponseEntity.ok("User delete");
    }

}
