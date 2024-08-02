package com.example.spring_basic_auth_example.service;

import com.example.spring_basic_auth_example.entity.Role;
import com.example.spring_basic_auth_example.entity.User;
import com.example.spring_basic_auth_example.model.UserCreateDto;
import com.example.spring_basic_auth_example.model.UserDto;
import com.example.spring_basic_auth_example.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private final UserRepository userRepository =
            Mockito.mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder =
            Mockito.mock(PasswordEncoder.class);
    private final UserService userService =
            new UserService(userRepository, passwordEncoder);

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
        UserDto userDto = userService.getByUsername(user.getUsername());
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
        Role role = new Role();
        String username = "+73133803311";
        userCreateDto.setId(1L);
        userCreateDto.setBalance(5000L);
        userCreateDto.setUsername(username);
        userCreateDto.setName("bublik");
        userCreateDto.setEmail("bublik@mail.ru");
        userCreateDto.setGender(true);
        userCreateDto.setPayments(Set.of());

        userService.create(userCreateDto, role);
        verify(userRepository, times (1)).saveAndFlush(any(User.class));
    }

    @Test
    @DisplayName("Test user update")
    public void testUpdate(){
        UserCreateDto userCreateDto = new UserCreateDto();
        String username = "+73133803311";
        userCreateDto.setId(1L);
        userCreateDto.setBalance(5000L);
        userCreateDto.setUsername(username);
        userCreateDto.setName("bublik");
        userCreateDto.setEmail("bublik@mail.ru");
        userCreateDto.setGender(true);
        userCreateDto.setPayments(Set.of());
        User user = new User();
        user.setUsername(userCreateDto.getUsername());

        when(userRepository.findByUsername(userCreateDto.getUsername())).thenReturn(Optional.of(user));

        userService.update(userCreateDto, userCreateDto.getUsername());
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
        userService.delete(user.getUsername());
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
        UserDto userDto1 = userService.mapToDto(user);
        UserDto userDto = new UserDto();
        userDto.setBalance(user.getBalance());
        userDto.setUsername(user.getUsername());

        assertEquals(userDto.getUsername(),userDto1.getUsername());

    }

    @Test
    @DisplayName("Test map to Entity")
    public void testMapToEntity(){
        UserCreateDto userCreateDto = new UserCreateDto();
        Role role = new Role();
        String username = "+73133803311";
        userCreateDto.setId(1L);
        userCreateDto.setBalance(5000L);
        userCreateDto.setUsername(username);
        userCreateDto.setName("bublik");
        userCreateDto.setEmail("bublik@mail.ru");
        userCreateDto.setGender(true);
        userCreateDto.setPayments(Set.of());
        User user = userService.mapToEntity(userCreateDto, role);

        assertEquals(userCreateDto.getUsername(), user.getUsername());
    }

    @Test
    @DisplayName("Test get user payments")
    public void testGetUserPayments(){
        String username = "+79831803329";
        User user = new User();
        user.setId(1L);
        user.setBalance(5000L);
        user.setUsername(username);
        user.setName("bublik");
        user.setEmail("bublik@mail.ru");
        user.setGender(true);Payment payment = new Payment();
        payment.setAmount(500L);
        payment.setDate(new Date());
        payment.setUser(user);

        Set<Payment> payments  = new HashSet<>();
        payments.add(payment);

        user.setPayments(payments);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Page<PaymentDto> paymentsUser = userService.getUserPayment(1,10,username);

        assertEquals(1, paymentsUser.getTotalElements());
    }




}

