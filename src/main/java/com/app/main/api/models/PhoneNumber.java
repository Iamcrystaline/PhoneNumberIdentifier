package com.app.main.api.models;

import java.util.regex.Pattern;

public class PhoneNumber {

    private final Pattern phoneNumberPattern = Pattern.compile("[1-9]\\d{1,14}");
    private final Long phoneNumber;

    public PhoneNumber(String phoneNumber) {
        if (!phoneNumberPattern.matcher(phoneNumber).matches()) {
            throw new InvalidPhoneNumberException("Invalid phone number. " +
                    "It has to start with '+' and have digits only, " +
                    "but at least 2 and no more than 15");
        }
        this.phoneNumber = Long.parseLong(phoneNumber);
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }
}
