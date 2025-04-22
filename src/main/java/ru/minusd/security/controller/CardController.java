package ru.minusd.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.minusd.security.domain.model.Card;
import ru.minusd.security.domain.model.Status;
import ru.minusd.security.service.CardService;
import ru.minusd.security.service.UserService;
import ru.minusd.security.swagger.UserApi;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Tag(name = "User", description = "The User API")
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Примеры", description = "Примеры запросов с разными правами доступа")
public class CardController {
    private final UserService service;
    private final CardService cardService;

    //    @RequestMapping("/card")
//    @GetMapping
//    @Operation(summary = "Доступен только авторизованным пользователям")
//    public Card card(String name) {
//        return new Card(name, name, name, name, Status.ACTIVE, name);
//    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserApi.class)))
                    })
    })
    @Validated
    @RequestMapping("/addCard")
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям", tags = "user")
    @PreAuthorize("hasRole('ADMIN')")
    public Card card2(@NotBlank String number, @Min(10) String balance, @Size(max = 10) String owner) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return cardService.addCard(number, balance, owner);
    }

    public ResponseEntity<ErrorMessage> handleException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorMessage(exception.getMessage()));
    }

    @RequestMapping("/getCard")
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public Card getCard(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return cardService.getByNumber(number);
    }

    @RequestMapping("/deleteCard")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public void deleteCard(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cardService.delete(number);
//        return cardService.getByNumber(number);
    }

    @Validated
    @RequestMapping("/updateBalanceCard")
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public void updateBalanceCard(String number, String amount) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cardService.updateBalance(number, new BigDecimal(amount));
//        return cardService.getByNumber(number);
    }

    @RequestMapping("/moneyTransferCard")
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public void moneyTransferCard(String fromNumber, String toNumber, String amount) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cardService.moneyTransferCard(fromNumber, toNumber, new BigDecimal(amount));
    }

    @RequestMapping("/getTransactionHistoryCardAdmin")
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<LocalDateTime, String> getTransactionHistoryCardAdmin(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return cardService.getTransactionHistoryAdmin(number);
    }

    @RequestMapping("/getTransactionHistoryCard")
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public Map<LocalDateTime, String> getTransactionHistoryCard(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return cardService.getTransactionHistory(number);
    }


    @RequestMapping("/blockedCard")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public void blockedCard(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cardService.blockedStatus(number);
    }

    @RequestMapping("/requestBlockedCard")
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public void requestBlockedCard(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cardService.blockedStatus(number);
    }

    @RequestMapping("/activeCard")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public void activeCard(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cardService.activeStatus(number);
    }

    @RequestMapping("/getAllCardAdmin")
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Card> getAllCardAdmin(Integer size) {
        return cardService.findAllAdmin(size);
//        return cardService.getByNumber(number);
    }

    @RequestMapping("/getAllCard")
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public List<Card> getAllCard(Integer size) {
        return cardService.findAll(size);
//        return cardService.getByNumber(number);
    }

//    @RequestMapping("/transaction")
//    @GetMapping
//    @Operation(summary = "Доступен только авторизованным пользователям")
//    public List<Card> getTransaction(String fromNumber, String toNumber, String amount) {
//        return cardService.getTransacton(fromNumber, toNumber, amount);
////        return cardService.getByNumber(number);
//    }

//    @RequestMapping("/setCard")
//    @GetMapping
//    @Operation(summary = "Доступен только авторизованным пользователям")
//    public List<Card> setCard(String number, String balance, String owner) {
//        return cardService.setCard(new Card(number, owner, balance, "null", "null", "null"));
//
////        return cardService.getByNumber(number);
//    }

    @GetMapping("/admin")
    @Operation(summary = "Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public String exampleAdmin() {
        return "Hello, admin!";
    }

    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN (для демонстрации)")
    public void getAdmin() {
        service.getAdmin();
    }
}
