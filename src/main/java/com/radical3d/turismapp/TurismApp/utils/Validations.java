package com.radical3d.turismapp.TurismApp.utils;

public class Validations {

    static final String EMAIL_REGEXP = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    static final String PHONE_REGEXP = "^[1-9][0-9]{9}$";
    static final String TEXT_REGEXP = "^[\\w-., ']{5,}";
    static final String CP_REGEXP = "^[0-9]{5}$";

    public static boolean validateEmail(String email){
        if (email == null || email.isBlank() || !email.matches(EMAIL_REGEXP)) {
            LoggerHelper.error("Email \"" + email + "\" not valid");
            return false;
        }
        return true;
    }

    public static boolean validatePhoneNumber(String phoneNumber){
        if (phoneNumber == null || !phoneNumber.matches(PHONE_REGEXP)) {
            LoggerHelper.error("PhoneNumber \"" + phoneNumber + "\" not valid");
            return false;
        }
        return true;
    }

    public static boolean validateText(String text){
        if (text == null || text.isBlank() || !text.matches(TEXT_REGEXP)) {
            LoggerHelper.error("Text \"" + text + "\" not valid");
            return false;
        }
        return true;
    }

    public static boolean validatePostalCode(String postalCode){
        if (postalCode == null) return false;
        if (postalCode.equals("00000")) return false;
        if (!postalCode.matches(postalCode)) return false;
        return true;
    }
}
