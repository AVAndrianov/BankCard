package ru.minusd.security.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cardSetting")
public class CardSetting implements Serializable {
    @Id
    @Column(name = "number")
    private String number;
    @Column(name = "monthLimit", unique = false, nullable = true)
    private Integer monthLimit;
    @Column(name = "dayLimit", unique = false, nullable = true)
    private Integer dayLimit;
    @Column(name = "blockRequest", unique = false, nullable = true)
    private Boolean blockRequest;


    @Override
    public String toString() {
        return "CardSetting{" +
                "number='" + number + '\'' +
                '}';
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



}
