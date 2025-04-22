package ru.minusd.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.minusd.security.domain.model.Card;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByNumber(String username);

    @Override
    void delete(Card entity);

    List<Card> findAllByOwner(String owner);

    boolean existsByNumber(String username);

    @Override
    Page<Card> findAll(Pageable pageable);
}
