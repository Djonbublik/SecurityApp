package com.example.spring_basic_auth_example.service;

import com.example.spring_basic_auth_example.entity.User;
import com.example.spring_basic_auth_example.model.PaymentDto;
import org.springframework.data.domain.Page;

public interface UserService<T> {
    void create(T item);
    void update(T item, String username);
    void delete(String username);
    User mapToEntity (T item);
    Page<PaymentDto> getUserPayment(int pageNumber, int pageSize, String username);
}
