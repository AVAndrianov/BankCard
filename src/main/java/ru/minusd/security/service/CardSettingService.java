package ru.minusd.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.minusd.security.domain.model.CardSetting;
import ru.minusd.security.repository.CardSettingRepository;

@Service
@RequiredArgsConstructor
public class CardSettingService {

    private final CardSettingRepository repository;

    public CardSetting save(CardSetting cardSetting) {
        return repository.save(cardSetting);
    }

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
            return Integer.MAX_VALUE;
        }
    }

    public Integer getDayLimitCard(String number) {
        if (repository.existsByNumber(number)) {
            CardSetting cardSetting = getByNumber(number);
            return cardSetting.getDayLimit();
        } else {
            return Integer.MAX_VALUE;
        }
    }
}
