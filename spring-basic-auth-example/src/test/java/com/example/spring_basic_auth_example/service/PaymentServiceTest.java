package com.example.spring_basic_auth_example.service;

import com.example.spring_basic_auth_example.entity.Payment;
import com.example.spring_basic_auth_example.entity.User;
import com.example.spring_basic_auth_example.model.PaymentDto;
import com.example.spring_basic_auth_example.repository.PaymentRepository;
import com.example.spring_basic_auth_example.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    private final PaymentRepository paymentRepository =
            Mockito.mock(PaymentRepository.class);
    private final UserRepository userRepository =
            Mockito.mock(UserRepository.class);

    private final PaymentService paymentService =
            new PaymentService(paymentRepository, userRepository);

    @Test
    @DisplayName("Test create")
    public void testCreate(){
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(1L);
        paymentDto.setAmount(500L);
        paymentDto.setDate(new Date());
        paymentDto.setNumberPhone("+79831803329");

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
        paymentService.create(paymentDto);

        verify(paymentRepository, times(1)).saveAndFlush(any(Payment.class));
        verify(userRepository , times(1)).saveAndFlush(any(User.class));
    }
}
