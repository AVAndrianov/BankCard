package ru.minusd.security.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.minusd.security.domain.model.Role;
import ru.minusd.security.domain.model.User;
import ru.minusd.security.repository.UserRepository;
import ru.minusd.security.service.AuthenticationService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void whenFindAll_thenReturnUsers() {
        User anton = new User(1L, "AVAndrianov", "123", "AVAndrianov@example.com", Role.ROLE_USER);
        List<User> list = new ArrayList();
        list.add(anton);
        Mockito.when(userRepository.findAll()).thenReturn(list);
    }

    @Test
    public void whenValidName_thenUserShouldBeFound() {
        String name = "AVAndrianov";
        User found = authenticationService.findAll().get(0);
        assertThat(found.getUsername()).isEqualTo(name);
    }
}
