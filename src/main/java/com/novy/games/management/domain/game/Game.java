package com.novy.games.management.domain.game;

import com.novy.games.management.domain.common.IdentifiedObject;
import com.novy.games.management.domain.game.valueobjects.GameId;

/**
 * Created by novy on 07.02.15.
 */
public class Game extends IdentifiedObject<GameId> {

    public Game(GameId gameId) {
        super(gameId);
    }
}
