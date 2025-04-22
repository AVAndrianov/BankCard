package ru.minusd.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.minusd.security.service.UserService;

@Tag(name = "admin", description = "The Admin API")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final UserService service;

    @GetMapping("/get-admin")
    @Operation(summary = "Получить права Администратора")
    public void getAdmin() {
        service.getAdmin();
    }
}
