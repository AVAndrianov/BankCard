package ru.minusd.security.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import ru.minusd.security.service.CardSettingService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CardSettingControllerTest {

    @Autowired
    private CardSettingController controller;
    @Autowired
    private CardSettingService service;
    private String number;
    private Integer dayLimit;

    @BeforeEach
    public void before() {
        number = "123";
        dayLimit = 1000;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAddCard_thenReturnCard() {
        controller.setDayLimitCardSetting(number, dayLimit);
        assertThat(controller).isNotNull();
        assertThat(controller.setDayLimitCardSetting(number, dayLimit).getDayLimit()).isEqualTo(dayLimit);
        assertThat(controller.setDayLimitCardSetting(number, dayLimit).getNumber()).isEqualTo(number);
    }

}
