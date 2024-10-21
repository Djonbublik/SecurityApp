package com.example.spring_basic_auth_example.repository;

import com.example.spring_basic_auth_example.entity.Payment;
import com.example.spring_basic_auth_example.entity.User;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;




public interface PaymentRepository extends PagingAndSortingRepository<Payment,Long>, JpaRepository<Payment, Long> {

    Page<Payment> findByUser(Pageable pageable, User user);

    Page<Payment> findAll(Pageable pageable);

}
