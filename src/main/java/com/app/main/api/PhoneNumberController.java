package com.app.main.api;

import com.app.main.api.models.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PhoneNumberController {

    private final PhoneNumberService service;

    @GetMapping("/countryPhoneCode")
    public String getCountryByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        return service.getCountryByPhoneNumber(new PhoneNumber(phoneNumber));
    }
}
