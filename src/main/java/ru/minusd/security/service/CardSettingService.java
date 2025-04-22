package ru.minusd.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.minusd.security.domain.model.Card;
import ru.minusd.security.domain.model.CardSetting;
import ru.minusd.security.domain.model.Status;
import ru.minusd.security.repository.CardRepository;
import ru.minusd.security.repository.CardSettingRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardSettingService {
    private final UserService userService;
    private final JwtService jwtService;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;

    private final CardSettingRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public CardSetting save(CardSetting cardSetting) {
        return repository.save(cardSetting);
    }

    /**
     * Регистрация пользователя
     * <p>
     * //     * @param request данные пользователя
     *
     * @return токен
     */
//    public Card addCard(String number, String balance, String owner) {
//
//        return save(new CardSetting(number, owner, balance, "null", Status.ACTIVE, new ArrayList<>()));
//    }
    public CardSetting setDayLimitCard(String number, Integer dayLimit) {
        if (repository.existsByNumber(number)) {
            CardSetting cardSetting = getByNumber(number);
            cardSetting.setDayLimit(dayLimit);
            return save(cardSetting);
        } else {
            return save(new CardSetting(number, null, dayLimit, null));
        }
    }

    public CardSetting setMonthLimitCard(String number, Integer monthLimit) {
        if (repository.existsByNumber(number)) {
            CardSetting cardSetting = getByNumber(number);
            cardSetting.setMonthLimit(monthLimit);
            return save(cardSetting);
        } else {
            return save(new CardSetting(number, monthLimit, null, null));
        }
    }

    public CardSetting getByNumber(String number) {
        return repository.findByNumber(number)
                .orElseThrow(() -> new UsernameNotFoundException("Карта не найдена"));
    }

    public CardSetting requestBlockedCard(String number, Boolean requestBlocked) {
        if (repository.existsByNumber(number)) {
            CardSetting cardSetting = getByNumber(number);
            cardSetting.setBlockRequest(requestBlocked);
            return save(cardSetting);
        } else {
            return save(new CardSetting(number, null, null, requestBlocked));
        }
    }

    public Integer getMonthLimitCard(String number) {
        if (repository.existsByNumber(number)) {
            CardSetting cardSetting = getByNumber(number);
            return cardSetting.getMonthLimit();
        } else {
            return null;
        }
    }

    public Integer getDayLimitCard(String number) {
        if (repository.existsByNumber(number)) {
            CardSetting cardSetting = getByNumber(number);
            return cardSetting.getDayLimit();
        } else {
            return null;
        }
    }
}
