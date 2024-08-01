package com.example.spring_basic_auth_example.service;


import com.example.spring_basic_auth_example.entity.Role;
import com.example.spring_basic_auth_example.entity.User;
import com.example.spring_basic_auth_example.model.PaymentDto;
import com.example.spring_basic_auth_example.model.UserCreateDto;
import com.example.spring_basic_auth_example.model.UserDto;
import com.example.spring_basic_auth_example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {
    public static final Long START_BALANCE = 100000L;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto getByUsername(String username) {
        return mapToDto(userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException (" Username not found!")));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException (" Username not found!"));
    }

    public void create(UserCreateDto userCreateDto, Role role) {
        if (userRepository.findByUsername(userCreateDto.getUsername()).isEmpty()){
            User user = mapToEntity(userCreateDto, role);
            user.setBalance(START_BALANCE);
            userRepository.saveAndFlush(user);
        }else {
            System.out.println("the user already exists");
        }
    }

    public void delete(String username){
        userRepository.delete(userRepository.findByUsername(username).orElseThrow());
    }

    public void update(UserCreateDto userCreateDto, String username) {

            User user = findByUsername(username);

            if (userCreateDto.getName() != null) {
                user.setName(userCreateDto.getName());
            }
            if (userCreateDto.getEmail() != null) {
                user.setEmail(userCreateDto.getEmail());
            }
            if (userCreateDto.getBirthday() != null) {
                user.setBirthday(userCreateDto.getBirthday());
            }
            if (userCreateDto.getGender() != null) {
                user.setGender(userCreateDto.getGender());
            }
            userRepository.saveAndFlush(user);
    }

    public UserDto mapToDto(User user){
        UserDto userDto = new UserDto();

        userDto.setUsername(user.getUsername());
        userDto.setBalance(user.getBalance());

        return userDto;
    }

    public User mapToEntity(UserCreateDto userCreateDto, Role role){
        User user = new User();

        Pattern patternUsername = Pattern.compile("^\\+([0-9\\-]?){9,11}[0-9]$");
        Matcher matcherUsername = patternUsername.matcher(userCreateDto.getUsername());

        Pattern patternEmail = Pattern.compile("^(.+)@(\\S+)$");
        Matcher matcherEmail = patternEmail.matcher(userCreateDto.getEmail());

            if (matcherUsername.matches()) {
                user.setUsername(userCreateDto.getUsername());

                if(matcherEmail.matches()){
                    user.setEmail(userCreateDto.getEmail());

                    user.setGender(userCreateDto.getGender());
                    user.setName(userCreateDto.getName());
                    user.setPayments(userCreateDto.getPayments());
                    user.setBalance(userCreateDto.getBalance());
                    user.setBirthday(userCreateDto.getBirthday());
                    user.setRoles(Collections.singletonList(role));
                    user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

                    role.setUser(user);
                    return user;
                }else {
                    throw new IllegalArgumentException("invalid  format EMAIL");
                }
            }else{
                throw new IllegalArgumentException("invalid format Username! Normal format +79831803329");
            }
    }

    public Page<PaymentDto> getUserPayment(int page, int size, UserDetails userDetails){
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<PaymentDto> paymentDtoList = user.getPayments()
                .stream()
                .map(PaymentService::mapToDto).toList();
        Comparator<PaymentDto> comparator = Comparator.comparing(PaymentDto::getId);
        Stream<PaymentDto> paymentDtoStream = paymentDtoList.stream().sorted(comparator);
        List<PaymentDto> paymentDtoListSorted = paymentDtoStream.collect(Collectors.toList());
        List<PaymentDto> list;

        Pageable pageable = PageRequest.of(page, size);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        if (paymentDtoListSorted.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, paymentDtoListSorted.size());
            list = paymentDtoListSorted.subList(startItem, toIndex);
        }

        return new PageImpl<PaymentDto>(list, PageRequest.of(currentPage, pageSize), paymentDtoList.size());
    }
}
