package ru.minusd.security.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
public class Card implements Serializable {
    /**
     * Поле номер карты
     */
    @Id
    @NotBlank
    @Column(name = "number")
    private String number;
    /**
     * Поле владелец карты
     */
    @Column(name = "owner")
    private String owner;
    /**
     * Поле сумма на карте
     */
    @Column(name = "balance")
    private String balance;
    /**
     * Поле время работы карты
     */
    @Column(name = "validityPeriod")
    private String validityPeriod;
    /**
     * Поле статус карты
     * {@link Status#ACTIVE}
     * {@link Status#BLOCKED}
     * {@link Status#EXPIRED}
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    /**
     * Поле история операций по карте
     */
    @Column(name = "transactionHistory")
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<LocalDateTime, String> transactionHistory;

    @Override
    public String toString() {
        return "Card{" +
                "number='" + number + '\'' +
                ", owner='" + owner + '\'' +
                ", balance='" + balance + '\'' +
                ", validityPeriod='" + validityPeriod + '\'' +
                ", status='" + status + '\'' +
                ", transactionHistory='" + transactionHistory + '\'' +
                '}';
    }

    /**
     * Функция получения значения поля {@link Card#number}
     *
     * @return возвращает номер карты
     */
    public String getNumber() {
        return number;
    }

    /**
     * Процедура определения номера карты {@link Card#number}
     *
     * @param number - номер карты
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Функция получения значения поля {@link Card#owner}
     *
     * @return возвращает имя владельца карты
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Процедура определения владельца карты {@link Card#owner}
     *
     * @param owner - владелец карты
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }


    /**
     * Функция получения значения поля {@link Card#balance}
     *
     * @return возвращает сумму на карте
     */
    public String getBalance() {
        return balance;
    }

    /**
     * Процедура определения суммы на карте {@link Card#balance}
     *
     * @param balance - сумма на карте
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }

    /**
     * Функция получения значения поля {@link Card#validityPeriod}
     *
     * @return возвращает срок действия карты
     */
    public String getValidityPeriod() {
        return validityPeriod;
    }

    /**
     * Процедура определения срока действия карты {@link Card#validityPeriod}
     *
     * @param validityPeriod - срок действия карты
     */
    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    /**
     * Функция получения значения поля {@link Card#status}
     *
     * @return возвращает статус карты
     * {@link Status#ACTIVE}
     * {@link Status#BLOCKED}
     * {@link Status#EXPIRED}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Процедура определения статуса карты {@link Card#status}
     *
     * @param status - статус карты
     *               {@link Status#ACTIVE}
     *               {@link Status#BLOCKED}
     *               {@link Status#EXPIRED}
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Функция получения значения поля {@link Card#transactionHistory}
     *
     * @return возвращает список операций по карте
     */
    public Map<LocalDateTime, String> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * Процедура определения списка операций по карте {@link Card#transactionHistory}
     *
     * @param transactionHistory - список операций по карте
     */
    public void setTransactionHistory(Map<LocalDateTime, String> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
}
