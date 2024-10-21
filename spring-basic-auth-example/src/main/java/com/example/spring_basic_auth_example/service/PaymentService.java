package com.example.spring_basic_auth_example.service;

import com.example.spring_basic_auth_example.entity.Payment;
import com.example.spring_basic_auth_example.entity.User;
import com.example.spring_basic_auth_example.model.PaymentDto;

public interface PaymentService <T>{
    void create(T item, String username);
    Payment mapToEntity(T item, User user);
}
