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
import ru.minusd.security.service.UserService;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void whenFindByName_thenReturnUser() {
        User anton = new User(1L, "AVAndrianov2", "123", "AVAndrianov@example.com", Role.ROLE_USER);
        Mockito.when(userRepository.findByUsername(anton.getUsername())).thenReturn(java.util.Optional.of(anton));
    }

    @Test
    public void whenValidName_thenUserShouldBeFound() {
        String name = "AVAndrianov2";
        User found = userService.getByUsername(name);
        assertThat(found.getUsername()).isEqualTo(name);
    }

}
