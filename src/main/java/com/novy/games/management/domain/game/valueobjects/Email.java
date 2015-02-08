package com.novy.games.management.domain.game.valueobjects;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Created by novy on 07.02.15.
 */

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
public class Email {

    private static final String VALIDATION_MESSAGE = "Invalid email string!";

    private final String emailString;

    public Email(String emailString) {
        Preconditions.checkArgument(isValid(emailString), VALIDATION_MESSAGE);
        this.emailString = emailString;
    }

    public static Email of(String emailString) {
        return new Email(emailString);
    }

    private boolean isValid(String emailString) {
        return EmailValidator
                .getInstance()
                .isValid(emailString);
    }
}
