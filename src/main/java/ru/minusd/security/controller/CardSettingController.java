package ru.minusd.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.minusd.security.domain.model.Card;
import ru.minusd.security.domain.model.CardSetting;
import ru.minusd.security.service.CardService;
import ru.minusd.security.service.CardSettingService;
import ru.minusd.security.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Примеры", description = "Примеры запросов с разными правами доступа")
public class CardSettingController {
    private final UserService service;
    private final CardSettingService cardSettingService;

    @RequestMapping("/setDayLimitCardSetting")
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    @PreAuthorize("hasRole('ADMIN')")
    public CardSetting setDayLimitCardSetting(String number, Integer dayLimit) {
        return cardSettingService.setDayLimitCard(number, dayLimit);
    }

    @RequestMapping("/setMonthLimitCardSetting")
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    @PreAuthorize("hasRole('ADMIN')")
    public CardSetting setMonthLimitCardSetting(String number, Integer monthLimit) {
        return cardSettingService.setMonthLimitCard(number, monthLimit);
    }

    @RequestMapping("/requestBlockCardSetting")
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    @PreAuthorize("hasRole('ADMIN')")
    public CardSetting requestBlockedCard(String number, Boolean requestBlock) {
        return cardSettingService.requestBlockedCard(number, requestBlock);
    }
}
