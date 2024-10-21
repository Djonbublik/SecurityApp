package com.example.spring_basic_auth_example.service;


import com.example.spring_basic_auth_example.entity.Role;
import com.example.spring_basic_auth_example.entity.User;
import com.example.spring_basic_auth_example.model.PaymentDto;
import com.example.spring_basic_auth_example.model.UserCreateDto;
import com.example.spring_basic_auth_example.model.UserDto;
import com.example.spring_basic_auth_example.repository.PaymentRepository;
import com.example.spring_basic_auth_example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService <UserCreateDto> {
    public static final Long START_BALANCE = 100000L;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PaymentRepository paymentRepository;
    private final PaymentServiceImpl paymentService;

    Pattern patternEmail = Pattern.compile("^(.+)@(\\S+)$");

    public UserDto getByUsername(String username) {
        return mapToDto(userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(" Username not found!")));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(" Username not found!"));
    }

    public void create(UserCreateDto userCreateDto) {
        if (userRepository.findByUsername(userCreateDto.getUsername()).isEmpty()) {
            User user = mapToEntity(userCreateDto);
            user.setBalance(START_BALANCE);
            userRepository.saveAndFlush(user);
        } else {
            System.out.println("the user already exists");
        }
    }

    public void delete(String username) {
        userRepository.delete(userRepository.findByUsername(username).orElseThrow());
    }

    public void update(UserCreateDto userCreateDto, String username) {
        Matcher matcherEmail = patternEmail.matcher(userCreateDto.getEmail());
        User user = findByUsername(username);

        if (userCreateDto.getName() != null) {
            user.setName(userCreateDto.getName());
        }
        if (userCreateDto.getEmail() != null) {
            if (matcherEmail.matches()) {
                user.setEmail(userCreateDto.getEmail());
            } else {
                throw new IllegalArgumentException("invalid  format EMAIL");
            }
        }
        if (userCreateDto.getBirthday() != null) {
            user.setBirthday(userCreateDto.getBirthday());
        }
        if (userCreateDto.getGender() != null) {
            user.setGender(userCreateDto.getGender());
        }
        userRepository.saveAndFlush(user);
    }

    public UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setUsername(user.getUsername());
        userDto.setBalance(user.getBalance());

        return userDto;
    }

    public User mapToEntity(UserCreateDto userCreateDto) {
        User user = new User();

        Pattern patternUsername = Pattern.compile("^\\+([0-9\\-]?){9,11}[0-9]$");
        Matcher matcherUsername = patternUsername.matcher(userCreateDto.getUsername());

        Matcher matcherEmail = patternEmail.matcher(userCreateDto.getEmail());

        if (matcherUsername.matches()) {
            user.setUsername(userCreateDto.getUsername());

            if (matcherEmail.matches()) {
                Role role = Role.from(userCreateDto.getRoleType());

                user.setEmail(userCreateDto.getEmail());
                user.setGender(userCreateDto.getGender());
                user.setName(userCreateDto.getName());
                user.setBirthday(userCreateDto.getBirthday());

                user.setRoles(Collections.singletonList(role));
                user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

                role.setUser(user);
                return user;
            } else {
                throw new IllegalArgumentException("invalid  format EMAIL");
            }
        } else {
            throw new IllegalArgumentException("invalid format Username! Normal format +79831803329");
        }
    }

    public Page<PaymentDto> getUserPayment(int pageNumber, int pageSize, String username) {
        if (pageNumber > 0 && pageSize > 0) {

            final Pageable contactsPageable = PageRequest.of(pageNumber - 1, pageSize);

            return (paymentRepository.findByUser(contactsPageable, findByUsername(username))).map(i -> paymentService.mapToDto(i));
        }
        throw new NumberFormatException("Select the page from 1 to 20 and the page size should be more than 0");
    }
}
