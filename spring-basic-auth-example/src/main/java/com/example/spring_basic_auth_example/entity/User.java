package com.example.spring_basic_auth_example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="username", unique=true, nullable = false)
    private String username;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="email", unique=true, length = 100, nullable = false)
    private String email;

    @Column(name="gender", nullable = false)
    private Gender gender;

    @Column(name="birthday", nullable = false)
    private Date birthday;

    @Column(name="balance", nullable = false)
    private Long balance;

    @Column(name="password", nullable = false)
    private String password;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Payment> payments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Role> roles = new ArrayList<>();




}
