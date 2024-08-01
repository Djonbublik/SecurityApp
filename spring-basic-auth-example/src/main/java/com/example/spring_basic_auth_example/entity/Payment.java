package com.example.spring_basic_auth_example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@RequiredArgsConstructor
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date date;

    private  Long amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
