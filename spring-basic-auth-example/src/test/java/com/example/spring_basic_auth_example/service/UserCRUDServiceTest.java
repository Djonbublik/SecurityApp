package com.example.spring_basic_auth_example.service;

import com.example.spring_basic_auth_example.entity.Payment;
import com.example.spring_basic_auth_example.entity.User;
import com.example.spring_basic_auth_example.model.PaymentDto;
import com.example.spring_basic_auth_example.model.UserCreateDto;
import com.example.spring_basic_auth_example.model.UserDto;
import com.example.spring_basic_auth_example.repository.PaymentRepository;
import com.example.spring_basic_auth_example.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserCRUDServiceTest {

    private final UserRepository userRepository =
            mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder =
            mock(PasswordEncoder.class);
    private final PaymentServiceImpl paymentService =
            mock(PaymentServiceImpl.class);
    private final PaymentRepository paymentRepository =
            mock(PaymentRepository.class);
    private final UserServiceImpl userServiceImpl =
            new UserServiceImpl(userRepository, passwordEncoder,paymentRepository,paymentService);

    @Test
    @DisplayName("Test getByUsername")
    public void getByUsername(){
        String username = "+79831803329";
        User user = new User();
        user.setId(1L);
        user.setBalance(5000L);
        user.setUsername(username);
        user.setName("bublik");
        user.setEmail("bublik@mail.ru");
        user.setGender(true);
        user.setPayments(Set.of());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        UserDto userDto = userServiceImpl.getByUsername(user.getUsername());
        assertEquals(username, userDto.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("Test find by Username")
    public void testFindByUsername(){
        String username = "+79831803329";
        User user = new User();
        user.setId(1L);
        user.setBalance(5000L);
        user.setUsername(username);
        user.setName("bublik");
        user.setEmail("bublik@mail.ru");
        user.setGender(true);
        user.setPayments(Set.of());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        User user1 = userRepository.findByUsername(username).orElseThrow();
        assertEquals(username, user1.getUsername());
        verify(userRepository, times(1)).findByUsername(username);

    }

    @Test
    @DisplayName("Test create")
    public void testCreate() {
        UserCreateDto userCreateDto = new UserCreateDto();
        String username = "+73133803311";
        userCreateDto.setUsername(username);
        userCreateDto.setName("bublik");
        userCreateDto.setEmail("bublik@mail.ru");
        userCreateDto.setGender(true);


        userServiceImpl.create(userCreateDto);
        verify(userRepository, times (1)).saveAndFlush(any(User.class));
    }

    @Test
    @DisplayName("Test user update")
    public void testUpdate(){
        UserCreateDto userCreateDto = new UserCreateDto();
        String username = "+73133803311";
        userCreateDto.setUsername(username);
        userCreateDto.setName("bublik");
        userCreateDto.setEmail("bublik@mail.ru");
        userCreateDto.setGender(true);
        User user = new User();
        user.setUsername(userCreateDto.getUsername());

        when(userRepository.findByUsername(userCreateDto.getUsername())).thenReturn(Optional.of(user));

        userServiceImpl.update(userCreateDto, userCreateDto.getUsername());
        verify(userRepository).saveAndFlush(any(User.class));
    }

    @Test
    @DisplayName("Test User delete")
    public void testDelete(){
        String username = "+79831803329";
        User user = new User();
        user.setId(1L);
        user.setBalance(5000L);
        user.setUsername(username);
        user.setName("bublik");
        user.setEmail("bublik@mail.ru");
        user.setGender(true);
        user.setPayments(Set.of());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        userServiceImpl.delete(user.getUsername());
        verify(userRepository).delete(any(User.class));

    }

    @Test
    @DisplayName("Test map to dto")
    public void testMapToDto(){
        String username = "+79831803329";
        User user = new User();
        user.setId(1L);
        user.setBalance(5000L);
        user.setUsername(username);
        user.setName("bublik");
        user.setEmail("bublik@mail.ru");
        user.setGender(true);
        user.setPayments(Set.of());
        UserDto userDto1 = userServiceImpl.mapToDto(user);
        UserDto userDto = new UserDto();
        userDto.setBalance(user.getBalance());
        userDto.setUsername(user.getUsername());

        assertEquals(userDto.getUsername(),userDto1.getUsername());

    }

    @Test
    @DisplayName("Test map to Entity")
    public void testMapToEntity(){
        UserCreateDto userCreateDto = new UserCreateDto();
        String username = "+73133803311";
        userCreateDto.setUsername(username);
        userCreateDto.setName("bublik");
        userCreateDto.setEmail("bublik@mail.ru");
        userCreateDto.setGender(true);
        User user = userServiceImpl.mapToEntity(userCreateDto);

        assertEquals(userCreateDto.getUsername(), user.getUsername());
    }

    @Test
    @DisplayName("Test get user payments")
    public void testGetUserPayments(){
        int pageNumber = 1;
        int pageSize = 5;

        String username = "+79831803329";
        User user = new User();
        user.setId(1L);
        user.setBalance(5000L);
        user.setUsername(username);
        user.setName("bublik");
        user.setEmail("bublik@mail.ru");
        user.setGender(true);
        Payment payment = new Payment();
        payment.setAmount(500L);
        payment.setDate(new Date());
        payment.setUser(user);

        Set<Payment> payments  = new HashSet<>();
        payments.add(payment);
        user.setPayments(payments);
        List<Payment> testPaymentList = payments.stream().toList();

        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        final Page<Payment> page = new PageImpl<>(testPaymentList, pageable, payments.size());

        when(paymentRepository.findByUser(pageable,user)).thenReturn(page);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Page<PaymentDto> paymentsUser = userServiceImpl.getUserPayment(pageNumber,pageSize,username);

        assertEquals(1, paymentsUser.getTotalElements());
    }




}

