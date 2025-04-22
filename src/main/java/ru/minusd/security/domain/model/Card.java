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
    @Id
    @NotBlank
    @Column(name = "number")
    private String number;
    @Column(name = "owner", unique = false, nullable = true)
    private String owner;
    @Column(name = "balance", unique = false, nullable = true)
    private String balance;
    @Column(name = "validityPeriod", unique = false, nullable = true)
    private String validityPeriod;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", unique = false, nullable = true)
    private Status status;
    @Column(name = "transactionHistory", unique = false, nullable = true)
    @ElementCollection(fetch=FetchType.EAGER)
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Map<LocalDateTime, String> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(Map<LocalDateTime, String> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
}
