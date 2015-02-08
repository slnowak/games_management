package com.novy.games.management.domain.game.valueobjects;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.commons.validator.routines.RegexValidator;

/**
 * Created by novy on 07.02.15.
 */

@Getter
@Accessors(fluent = true)
public class Phone {

    private static final String VALIDATION_MESSAGE = "Invalid phone number string!";
    private static final RegexValidator validator;

    private final String phoneString;

    static {
        validator = configuredRegexValidator();
    }

    private static RegexValidator configuredRegexValidator() {
        final String regexPattern = "\\d{9}";
        return new RegexValidator(regexPattern);
    }

    public static Phone of(String phoneString) {
        return new Phone(phoneString);
    }

    private Phone(String phoneString) {
        Preconditions.checkArgument(isValid(phoneString), VALIDATION_MESSAGE);
        this.phoneString = phoneString;
    }

    private boolean isValid(String phoneString) {
        return validator.isValid(phoneString);
    }
}
