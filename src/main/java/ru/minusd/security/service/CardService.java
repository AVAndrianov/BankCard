package ru.minusd.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.minusd.security.cripto.Crypto;
import ru.minusd.security.domain.model.Status;
import ru.minusd.security.domain.model.Card;
import ru.minusd.security.repository.CardRepository;

import javax.crypto.*;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardSettingService cardSettingService;
    private final Crypto crypto;
    private final CardRepository repository;

    public Card save(Card card) {
        return repository.save(card);
    }

    public Card addCard(String number, String balance, String owner) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return save(new Card(crypto.encrypt(number), owner, balance, "null", Status.ACTIVE, new HashMap<>()));
    }

    public Card getByNumber(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Card card = repository.findByNumber(crypto.encrypt(number)).get();
//        card.setOwner(crypto.decrypt(card.getOwner()));
        card.setOwner(card.getOwner());
//        System.out.println("1111121312312312312312 "+ card.getNumber());
//        card.setNumber(crypto.decrypt(card.getNumber()));
        card.setNumber(card.getNumber());
        return card;
    }

    public void delete(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        repository.delete(getByNumber(number));
    }

    public Card updateBalance(String number, BigDecimal amount) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        System.out.println(Status.ACTIVE);
        Card card = getByNumber(number);
        card.getTransactionHistory().put(LocalDateTime.now(), String.valueOf(amount));
        card.setBalance(String.valueOf(new BigDecimal(card.getBalance()).add(amount)));
        return repository.save(card);
    }

    public void moneyTransferCard(String fromNumber, String toNumber, BigDecimal amount) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if (getByNumber(fromNumber).getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())
                && getByNumber(toNumber).getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            Map<LocalDateTime, String> map = getByNumber(fromNumber).getTransactionHistory();
            int daySum = map.entrySet().stream().map(i -> {
                if (i.getKey().getDayOfMonth() > LocalDateTime.now().minusDays(1L).getDayOfMonth()
                        && Integer.parseInt(i.getValue()) > 0) {
                    return i.getValue();
                }
                return null;
            }).filter(Objects::nonNull).mapToInt(Integer::valueOf).sum();

            int monthSum = map.entrySet().stream().map(i -> {
                if (i.getKey().isAfter(LocalDateTime.now().minusMonths(1L))
                        && Integer.parseInt(i.getValue()) > 0) {
                    return i.getValue();
                }
                return null;
            }).filter(Objects::nonNull).mapToInt(Integer::valueOf).sum();

            if (daySum < cardSettingService.getDayLimitCard(fromNumber)) {
                if (monthSum < cardSettingService.getMonthLimitCard(fromNumber)) {
                    updateBalance(fromNumber, amount.negate());
                    updateBalance(toNumber, amount);
                } else {
                    System.out.println("Месячный лимит исчерпан: " + cardSettingService.getMonthLimitCard(fromNumber));
                }
            } else {
                System.out.println("Дневной лимит исчерпан: " + cardSettingService.getDayLimitCard(fromNumber));
            }
        }
    }

    public Card activeStatus(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Card card = getByNumber(number);
        card.setStatus(Status.ACTIVE);
        return repository.save(card);
    }

    public Card blockedStatus(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Card card = getByNumber(number);
        card.setStatus(Status.BLOCKED);
        return repository.save(card);
    }

    public Page<Card> findAllAdmin(Integer pageNumber) {
        return repository.findAll(Pageable.ofSize(10).withPage(pageNumber));
    }

    public List<Card> findAll(Integer pageNumber) {
        return repository.findAllByOwner(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public Page<Card> getTransacton(String fromNumber, String toNumber, String amount) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        getByNumber(fromNumber);
        return repository.findAll(Pageable.ofSize(0));
//                .orElseThrow(() -> new UsernameNotFoundException("Карта не найдена"));

    }

    public Map<LocalDateTime, String> getTransactionHistoryAdmin(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return getByNumber(number).getTransactionHistory();
    }

    public Map<LocalDateTime, String> getTransactionHistory(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if (getByNumber(number).getOwner().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return getByNumber(number).getTransactionHistory();
        } else {
            return null;
        }
    }
}
