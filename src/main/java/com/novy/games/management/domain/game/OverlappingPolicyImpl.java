package com.novy.games.management.domain.game;

import com.novy.games.management.domain.participant.valueobjects.ParticipantId;

import java.util.Collection;

/**
 * Created by novy on 09.02.15.
 */
public class OverlappingPolicyImpl implements OverlappingPolicy {

    private GameRepository gameRepository;

    public OverlappingPolicyImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public boolean isOverlapping(ParticipantId participantId, Game game) {
        Collection<Game> games = gamesInWhichParticipantParticipates(participantId);
        return participantAlreadyHasOverlappingGame(games, game);
    }

    private boolean participantAlreadyHasOverlappingGame(Collection<Game> games, Game possiblyOverlappingGame) {
        return games
                .stream()
                .anyMatch(game -> game.state() != GameState.CANCELLED && game.overlaps(possiblyOverlappingGame));
    }

    private Collection<Game> gamesInWhichParticipantParticipates(ParticipantId participantId) {
        return gameRepository.findBy(participantId);
    }
}
