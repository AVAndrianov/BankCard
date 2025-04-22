package ru.minusd.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.minusd.security.domain.model.CardSetting;

import java.util.Optional;

@Repository
public interface CardSettingRepository extends JpaRepository<CardSetting, Long> {
    Optional<CardSetting> findByNumber(String number);
    @Override
    void delete(CardSetting entity);
    boolean existsByNumber(String number);
    @Override
    Page<CardSetting> findAll(Pageable pageable);
}
