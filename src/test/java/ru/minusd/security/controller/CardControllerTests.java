package ru.minusd.security.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.minusd.security.domain.dto.SignUpRequest;
import ru.minusd.security.domain.model.Card;
import ru.minusd.security.domain.model.Status;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CardControllerTests {

    @Autowired
    private CardController controller;

    @Autowired
    AuthController authController;

    @Test
    @WithMockUser(roles="ADMIN")
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
        authController.signUp(new SignUpRequest("qwe","qwe@qwe.ru","qwerty"));
        controller.card2("123","1000","qwe");
//        Mockito.when(this.controller.card2("123","1000","qwe"));
//                .thenReturn(new Card("123","1000","qwe","null", Status.ACTIVE, new HashMap<>()));

    }
//    @Autowired
//    MockMvc mvc;


//    @Test
//    void findAllShouldReturnAllBooks() throws Exception {
//        Mockito.when(this.controller.card2("123","1000","qwe")).thenReturn(new Card("123","1000","qwe","null", Status.ACTIVE, new HashMap<>()));
////
////        mvc.perform(get("/books"))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.length()").value(2));
//    }
}
