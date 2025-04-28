package ru.minusd.security.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import ru.minusd.security.domain.model.Card;
import ru.minusd.security.service.CardService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CardControllerTests {

    @Autowired
    private CardController controller;
    @Autowired
    private CardService service;
    private Card resultCard;
    private String cardNumber;
    private String balance;
    private String owner;
    private Object Page;

    @BeforeEach
    public void before()  {
        cardNumber = "123";
        balance = "1000";
        owner = "qwe";
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAddCard_thenReturnCard() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        resultCard = controller.card2(cardNumber, balance, owner);
        assertThat(controller).isNotNull();
        Assertions.assertNotNull(resultCard);
        assertThat(resultCard.getOwner()).isEqualTo(owner);
        assertThat(resultCard.getBalance()).isEqualTo(balance);
    }
}
