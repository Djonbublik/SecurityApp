package com.example.SecurityApp.controller;

import lombok.Getter;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping
    public ResponseEntity<String > getPublic(){
        return ResponseEntity.ok("Called public method");
    }
}
