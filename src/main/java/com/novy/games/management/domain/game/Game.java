package com.novy.games.management.domain.game;

import com.google.common.collect.Lists;
import com.novy.games.management.domain.common.IdentifiedObject;
import com.novy.games.management.domain.game.valueobjects.GameId;
import com.novy.games.management.domain.participant.valueobjects.ParticipantId;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

import static com.novy.games.management.domain.game.GameState.*;

/**
 * Created by novy on 07.02.15.
 */
public class Game extends IdentifiedObject<GameId> {

    private GameState state = PENDING;
    private Collection<ParticipantId> participants = Lists.newArrayList();
    private ThresholdStrategy thresholdStrategy;


    public Game(GameId gameId, ThresholdStrategy thresholdStrategy) {
        super(gameId);
        this.thresholdStrategy = thresholdStrategy;
    }

    public void registerParticipant(ParticipantId participantId) {
        if (registrationNotPossibleAnymore()) {
            throw new IllegalStateException("Registration not possible anymore!");
        }

        if (participantRegistered(participantId)) {
            throw new IllegalStateException("Participant already registered!");
        }

        participants.add(participantId);

        if (thresholdStrategy.minThresholdExceeded(participantCount())) {
            state = MIN_PARTICIPANTS_GATHERED;
        }

        if (thresholdStrategy.maxThresholdExceeded(participantCount())) {
            state = MAX_PARTICIPANTS_GATHERED;
        }
    }

    private boolean registrationNotPossibleAnymore() {
        return EnumSet.of(MAX_PARTICIPANTS_GATHERED, TOOK_PLACE, CANCELLED)
                .contains(state);
    }

    public void unregisterParticipant(ParticipantId participantId) {

    }

    public boolean participantRegistered(ParticipantId aParticipantId) {
        return participants.contains(aParticipantId);
    }

    public GameState state() {
        return state;
    }

    public void cancel() {
        if (!canTransit(state, CANCELLED)) {
            throw new IllegalStateException("Cannot cancel a game!");
        }

        state = CANCELLED;
    }

    public void claimTookPlace() {
        if (!canTransit(state, TOOK_PLACE)) {
            throw new IllegalStateException("Game's state cannot be changed to 'took place'!");
        }

        state = TOOK_PLACE;
    }

    public Collection<ParticipantId> participants() {
        return Collections.unmodifiableCollection(participants);
    }

    private int participantCount() {
        return participants.size();
    }
}
