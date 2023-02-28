package com.app.main.api;

import com.app.main.api.models.CountryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<CountryInfo, Integer> {

    List<CountryInfo> findAllByCode(Long code);
}
