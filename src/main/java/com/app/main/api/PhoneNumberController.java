package com.app.main.api;

import com.app.main.api.models.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PhoneNumberController {

    private final PhoneNumberService service;

    @GetMapping("/countryPhoneCode")
    public List<String> getCountryByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        return service.getCountryByPhoneNumber(new PhoneNumber(phoneNumber));
    }
}
