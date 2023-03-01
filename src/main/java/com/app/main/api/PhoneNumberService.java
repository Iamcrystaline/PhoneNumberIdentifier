package com.app.main.api;

import com.app.main.api.models.CountryInfo;
import com.app.main.api.models.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneNumberService {

    private final PhoneNumberRepository repository;

    public List<String> getCountryByPhoneNumber(PhoneNumber phoneNumber) {
        Long countryCode = phoneNumber.getPhoneNumber();
        List<String> countries = repository.findAllCountriesByCode(countryCode);
        while (countries.isEmpty()) {
            countryCode /= 10;
            if (countryCode == 0) {
                throw new UnknownPhoneNumberException("Can't identify country for this phone number");
            }
            countries = repository.findAllCountriesByCode(countryCode);
        }
        return countries;
    }
}
