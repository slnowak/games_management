package com.novy.games.management.domain.game.valueobjects;

import com.novy.games.management.domain.common.UUIDBasedIdentifier;

/**
 * Created by novy on 07.02.15.
 */
public class GameId extends UUIDBasedIdentifier {

    public GameId(String id) {
        super(id);
    }

    public GameId() {
        super();
    }
}
