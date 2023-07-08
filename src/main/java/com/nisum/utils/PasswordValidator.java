package com.nisum.utils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PasswordValidator {

    private final Pattern pattern;

    public PasswordValidator(String regex) {
        pattern = Pattern.compile(regex);
    }

    public boolean isValidPassword(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
