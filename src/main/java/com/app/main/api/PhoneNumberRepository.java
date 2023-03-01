package com.app.main.api;

import com.app.main.api.models.CountryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<CountryInfo, Integer> {

    @Query(
            value = "SELECT country FROM country_info WHERE code = ?1",
            nativeQuery = true
    )
    List<String> findAllCountriesByCode(Long code);
}
