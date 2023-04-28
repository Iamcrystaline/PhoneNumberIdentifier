package com.app.main.api;

import com.app.main.api.models.CountryCodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<CountryCodeInfo, Integer> {

    @Query(
            value = "SELECT countries " +
                    "FROM country_code_info " +
                    "WHERE :phone LIKE concat(country_code, '%') " +
                    "ORDER BY country_code DESC " +
                    "LIMIT 1",
            nativeQuery = true
    )
    List<String> findAllCountriesByCode(Long phone);
}
