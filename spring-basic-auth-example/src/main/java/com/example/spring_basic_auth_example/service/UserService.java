package com.example.spring_basic_auth_example.service;

import com.example.spring_basic_auth_example.entity.Role;
import com.example.spring_basic_auth_example.entity.User;
import com.example.spring_basic_auth_example.model.UserCreateDto;

public interface UserService<T> {
    void create(T item);
    void update(T item, String username);
    void delete(String username);
    User mapToEntity (T item);
}
