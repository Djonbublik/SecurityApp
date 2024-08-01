package com.example.spring_basic_auth_example.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentDto {

    private Long id;

    private Date date;

    private  Long amount;

    private String numberPhone;
}
