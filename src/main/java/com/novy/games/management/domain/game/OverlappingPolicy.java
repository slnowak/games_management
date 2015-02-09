package com.novy.games.management.domain.game;

import com.novy.games.management.domain.participant.valueobjects.ParticipantId;

/**
 * Created by novy on 09.02.15.
 */
public interface OverlappingPolicy {

    boolean isOverlapping(ParticipantId participantId, Game game);
}
