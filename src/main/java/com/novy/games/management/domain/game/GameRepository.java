package com.novy.games.management.domain.game;

import com.novy.games.management.domain.game.valueobjects.GameId;
import com.novy.games.management.domain.participant.valueobjects.ParticipantId;

import java.util.Collection;

/**
 * Created by novy on 09.02.15.
 */
public interface GameRepository {

    Collection<Game> find();
    Game findBy(GameId aGameId);
    Collection<Game> findBy(ParticipantId aParticipantId);

    void save(Game game);
}
