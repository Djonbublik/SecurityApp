package com.example.spring_basic_auth_example.service;

import com.example.spring_basic_auth_example.entity.Payment;
import com.example.spring_basic_auth_example.entity.User;
import com.example.spring_basic_auth_example.model.PaymentDto;
import com.example.spring_basic_auth_example.repository.PaymentRepository;
import com.example.spring_basic_auth_example.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public void create(PaymentDto paymentDto){
        User user = userRepository.findByUsername(paymentDto.getNumberPhone()).orElseThrow();

        if(user.getBalance()> paymentDto.getAmount()){
            paymentRepository.saveAndFlush(mapToEntity(paymentDto));
            user.setBalance(user.getBalance() - paymentDto.getAmount());
            userRepository.saveAndFlush(user);
        }else {
            System.out.println("Недостаточно средств");
        }
        }

    public Payment mapToEntity(PaymentDto paymentDto){
        Payment payment = new Payment();
        User user = userRepository.findByUsername(paymentDto.getNumberPhone()).orElseThrow(()-> new RuntimeException (" Username not found!"));

        payment.setUser(user);
        payment.setAmount(paymentDto.getAmount());
        payment.setDate(new Date());

        return payment;
    }

    public static PaymentDto mapToDto(Payment payment){
        PaymentDto paymentDto = new PaymentDto();

        paymentDto.setId(payment.getId());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setDate(payment.getDate());
        paymentDto.setNumberPhone(payment.getUser().getUsername());

        return paymentDto;
    }
}
