package com.novy.games.management.domain.game.valueobjects;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Created by novy on 07.02.15.
 */

@Value
@Accessors(fluent = true)
public class Participant {

    private String firstName;
    private String lastName;
    private Phone phone;
    private Email email;
}
