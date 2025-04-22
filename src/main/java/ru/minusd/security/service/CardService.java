package ru.minusd.security.service;

import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.minusd.security.cripto.Cripto;
import ru.minusd.security.domain.model.Role;
import ru.minusd.security.domain.model.Status;
import ru.minusd.security.domain.model.Card;
import ru.minusd.security.repository.CardRepository;

import javax.crypto.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CardService {
    private final UserService userService;
    private final CardSettingService cardSettingService;
    private final JwtService jwtService;
    private final Cripto cripto;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;

    private final CardRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public Card save(Card card) {
        return repository.save(card);
    }

    /**
     * Регистрация пользователя
     * <p>
     * //     * @param request данные пользователя
     *
     * @return токен
     */
    public Card addCard(String number, String balance, String owner) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//
//        var user = User.builder()
//                .username(request.getUsername())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.ROLE_USER)
//                .build();
//
//        userService.create(user);
//
//        var jwt = jwtService.generateToken(user);
//        return new JwtAuthenticationResponse(jwt);
//        if (repository.existsByUsername(user.getUsername())) {
//            // Заменить на свои исключения
//            throw new RuntimeException("Пользователь с таким именем уже существует");
//        }
//
//        if (repository.existsByEmail(user.getEmail())) {
//            throw new RuntimeException("Пользователь с таким email уже существует");
//        }
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128);
//        SecretKey priKey = keyGenerator.generateKey();
//        Cipher cipher = Cipher.getInstance("AES");
//
//        cipher.init(Cipher.ENCRYPT_MODE, priKey);
//
//        byte[] bytes = cipher.doFinal(owner.getBytes(StandardCharsets.UTF_8));
//
//        String encrypted = DatatypeConverter.printHexBinary(bytes);
//
//        System.out.println(encrypted);
//
//        cipher.init(Cipher.DECRYPT_MODE, priKey);
//
//        byte[] bytes1 = cipher.doFinal(DatatypeConverter.parseHexBinary(encrypted));
//
//        System.out.println(new String(bytes1));

        return save(new Card(cripto.cripted(number), cripto.cripted(owner), cripto.cripted(balance), "null", Status.ACTIVE, new HashMap<>()));
    }

//    /**
//     * Аутентификация пользователя
//     *
//     * @param request данные пользователя
//     * @return токен
//     */
//    public JwtAuthenticationResponse signIn(SignInRequest request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                request.getUsername(),
//                request.getPassword()
//        ));
//
//        var user = userService
//                .userDetailsService()
//                .loadUserByUsername(request.getUsername());
//
//        var jwt = jwtService.generateToken(user);
//        return new JwtAuthenticationResponse(jwt);
//    }

    public Card getByNumber(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Card card = repository.findByNumber(cripto.cripted(number)).get();
        card.setOwner(cripto.uncripted(card.getOwner()));
        card.setNumber(cripto.uncripted(card.getNumber()));
        return card;
    }

    public void delete(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
//        System.out.println(getByNumber(number));
        repository.delete(getByNumber(number));
//        return true;
//                .orElseThrow(() -> new UsernameNotFoundException("Карта не найдена"));

    }

    public Card updateBalance(String number, BigDecimal amount) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        System.out.println(Status.ACTIVE);
        Card card = getByNumber(number);
        card.getTransactionHistory().put(LocalDateTime.now(), String.valueOf(amount));
        card.setBalance(String.valueOf(new BigDecimal(card.getBalance()).add(amount)));
        return repository.save(card);
//                .orElseThrow(() -> new UsernameNotFoundException("Карта не найдена"));
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
//        forEach(i -> System.out.println("key: " + i.getKey() + " value: " + i.getValue()));

        }
    }

    public Card activeStatus(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Card card = getByNumber(number);
        card.setStatus(Status.ACTIVE);
        return repository.save(card);
//                .orElseThrow(() -> new UsernameNotFoundException("Карта не найдена"));
    }

    public Card blockedStatus(String number) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Card card = getByNumber(number);
        card.setStatus(Status.BLOCKED);
        return repository.save(card);
//                .orElseThrow(() -> new UsernameNotFoundException("Карта не найдена"));
    }

    public Page<Card> findAllAdmin(Integer pageNumber) {
        return repository.findAll(Pageable.ofSize(2).withPage(pageNumber));
//                .orElseThrow(() -> new UsernameNotFoundException("Карта не найдена"));
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
