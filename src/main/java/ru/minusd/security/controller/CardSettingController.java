package ru.minusd.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.minusd.security.domain.model.CardSetting;
import ru.minusd.security.service.CardSettingService;
import ru.minusd.security.service.UserService;

@Tag(name = "Settings", description = "The Settings API")
@RestController
@RequiredArgsConstructor
public class CardSettingController {
    private final UserService service;
    private final CardSettingService cardSettingService;

    @RequestMapping(value = "/setDayLimitCardSetting",method = RequestMethod.GET)
    @GetMapping
    @Operation(summary = "Доступен только Администратору")
    @PreAuthorize("hasRole('ADMIN')")
    public CardSetting setDayLimitCardSetting(String number, Integer dayLimit) {
        return cardSettingService.setDayLimitCard(number, dayLimit);
    }

    @RequestMapping(value = "/setMonthLimitCardSetting", method = RequestMethod.GET)
    @GetMapping
    @Operation(summary = "Доступен только Администратору")
    @PreAuthorize("hasRole('ADMIN')")
    public CardSetting setMonthLimitCardSetting(String number, Integer monthLimit) {
        return cardSettingService.setMonthLimitCard(number, monthLimit);
    }

    @RequestMapping(value = "/requestBlockCardSetting", method = RequestMethod.GET)
    @GetMapping
    @Operation(summary = "Доступен только Администратору")
    @PreAuthorize("hasRole('ADMIN')")
    public CardSetting requestBlockedCard(String number, Boolean requestBlock) {
        return cardSettingService.requestBlockedCard(number, requestBlock);
    }
}
