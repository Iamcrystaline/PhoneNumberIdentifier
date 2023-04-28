package com.app.main.api;

import com.app.main.api.models.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneNumberService {

    private final PhoneNumberRepository repository;

    public String getCountryByPhoneNumber(PhoneNumber phoneNumber) {
        List<String> countries = repository.findAllCountriesByCode(phoneNumber.getPhoneNumber());
        return countries.stream().findFirst().orElseThrow(() -> new UnknownPhoneNumberException("Can't identify country for this phone number"));
    }
}
