package com.app.main.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CountryInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String country;
    private Long code;

    public CountryInfo(String country, Long code) {
        this.country = country;
        this.code = code;
    }
}
