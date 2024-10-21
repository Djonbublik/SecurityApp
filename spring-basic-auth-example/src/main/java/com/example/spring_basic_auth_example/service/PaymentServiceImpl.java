package com.example.spring_basic_auth_example.service;

import com.example.spring_basic_auth_example.entity.Payment;
import com.example.spring_basic_auth_example.entity.User;
import com.example.spring_basic_auth_example.model.PaymentDto;
import com.example.spring_basic_auth_example.repository.PaymentRepository;
import com.example.spring_basic_auth_example.repository.UserRepository;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService<PaymentDto> {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;


    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void create(PaymentDto paymentDto, String username){
        User user = userRepository.findByUsername(username).orElseThrow();

        if(user.getBalance() > paymentDto.getAmount() && paymentDto.getAmount() > 0){
            paymentRepository.saveAndFlush(mapToEntity(paymentDto, user));
            user.setBalance(user.getBalance() - paymentDto.getAmount());
            userRepository.saveAndFlush(user);
        }else {
            System.out.println("Error! Check the balance and the amount of the charge");
        }
    }


    public Payment mapToEntity(PaymentDto paymentDto, User user){
        Payment payment = new Payment();

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
        paymentDto.setUsername(payment.getUser().getUsername());

        return paymentDto;
    }
}
