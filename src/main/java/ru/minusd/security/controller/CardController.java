package ru.minusd.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.minusd.security.domain.model.Card;
import ru.minusd.security.service.CardService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Tag(name = "Card", description = "The Card API")
@Validated
@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @Validated
    @RequestMapping(value = "/addCard", method = RequestMethod.GET)
    @GetMapping
    @Operation(summary = "Доступен только Администратору")
//    @PreAuthorize("hasRole('ADMIN')")
    public Card card2(@NotBlank String number, @Min(10) String balance, @Size(max = 15) String owner) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return cardService.addCard(number, balance, owner);
    }

    public ResponseEntity<ErrorMessage> handleException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorMessage(exception.getMessage()));
    }

    @RequestMapping(value = "/getCard", method = RequestMethod.GET)
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public Card getCard(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return cardService.getByNumber(number);
    }

    @RequestMapping(value = "/deleteCard", method = RequestMethod.GET)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Доступен только Администратору")
    public void deleteCard(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cardService.delete(number);
    }

    @Validated
    @RequestMapping(value = "/updateBalanceCard", method = RequestMethod.GET)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Доступен только Администратору")
    public void updateBalanceCard(String number, String amount) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cardService.updateBalance(number, new BigDecimal(amount));
    }

    @RequestMapping(value = "/moneyTransferCard", method = RequestMethod.GET)
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public void moneyTransferCard(String fromNumber, String toNumber, String amount) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cardService.moneyTransferCard(fromNumber, toNumber, new BigDecimal(amount));
    }

    @RequestMapping(value = "/getTransactionHistoryCardAdmin", method = RequestMethod.GET)
    @GetMapping
    @Operation(summary = "Доступен только Администратору")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<LocalDateTime, String> getTransactionHistoryCardAdmin(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return cardService.getTransactionHistoryAdmin(number);
    }

    @RequestMapping(value = "/getTransactionHistoryCard", method = RequestMethod.GET)
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public Map<LocalDateTime, String> getTransactionHistoryCard(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return cardService.getTransactionHistory(number);
    }

    @RequestMapping(value = "/blockedCard", method = RequestMethod.GET)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Доступен только Администратору")
    public void blockedCard(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cardService.blockedStatus(number);
    }

    @RequestMapping(value = "/requestBlockedCard", method = RequestMethod.GET)
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public void requestBlockedCard(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cardService.blockedStatus(number);
    }

    @RequestMapping(value = "/activeCard", method = RequestMethod.GET)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Доступен только Администратору")
    public void activeCard(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cardService.activeStatus(number);
    }

    @RequestMapping(value = "/getAllCardAdmin", method = RequestMethod.GET)
    @GetMapping
    @Operation(summary = "Доступен только Администратору")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Card> getAllCardAdmin(Integer size) {
        return cardService.findAllAdmin(size);
    }

    @RequestMapping(value = "/getAllCard", method = RequestMethod.GET)
    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public List<Card> getAllCard(Integer size) {
        return cardService.findAll(size);
    }
}
