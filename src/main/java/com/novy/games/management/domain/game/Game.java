package com.novy.games.management.domain.game;

import com.google.common.collect.Lists;
import com.novy.games.management.domain.common.IdentifiedObject;
import com.novy.games.management.domain.game.valueobjects.GameId;
import com.novy.games.management.domain.participant.valueobjects.ParticipantId;
import org.threeten.extra.Interval;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    private ThresholdPolicy thresholdPolicy;
    private Interval duration;


    public Game(GameId gameId, ThresholdPolicy thresholdPolicy, LocalDateTime startDate, int durationInMinutes) {
        super(gameId);
        this.thresholdPolicy = thresholdPolicy;
        this.duration = intervalOfLocalDateTimes(startDate, startDate.plusMinutes(durationInMinutes));
    }

    private Interval intervalOfLocalDateTimes(LocalDateTime start, LocalDateTime end) {
        return Interval.of(
                start.toInstant(ZoneOffset.UTC),
                end.toInstant(ZoneOffset.UTC)
        );
    }

    public void registerParticipant(ParticipantId participantId) {
        if (registrationNotPossibleAnymore()) {
            throw new IllegalStateException("Registration not possible anymore!");
        }

        if (participantRegistered(participantId)) {
            throw new IllegalStateException("Participant already registered!");
        }

        participants.add(participantId);

        if (thresholdPolicy.minThresholdExceeded(participantCount())) {
            state = MIN_PARTICIPANTS_GATHERED;
        }

        if (thresholdPolicy.maxThresholdExceeded(participantCount())) {
            state = MAX_PARTICIPANTS_GATHERED;
        }
    }

    private boolean registrationNotPossibleAnymore() {
        return state == MAX_PARTICIPANTS_GATHERED || gameAlreadyTookPlaceOrHasBeenCancelled();
    }

    private boolean gameAlreadyTookPlaceOrHasBeenCancelled() {
        return EnumSet.of(TOOK_PLACE, CANCELLED)
                .contains(state);
    }

    public void unregisterParticipant(ParticipantId participantId) {
        if (!participantRegistered(participantId)) {
            throw new IllegalArgumentException("Participant not registered!");
        }

        if (gameAlreadyTookPlaceOrHasBeenCancelled()) {
            throw new IllegalStateException("Cannot unregister, wrong game state!");
        }

        participants.remove(participantId);

        if (state == MAX_PARTICIPANTS_GATHERED && !thresholdPolicy.maxThresholdExceeded(participantCount())) {
            state = MIN_PARTICIPANTS_GATHERED;
        }

        if (state == MIN_PARTICIPANTS_GATHERED && !thresholdPolicy.minThresholdExceeded(participantCount())) {
            state = PENDING;
        }

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

    public boolean overlaps(Game otherGame) {
        return duration.overlaps(
                intervalOfLocalDateTimes(otherGame.startTime(), otherGame.endTime())
        );
    }

    public Collection<ParticipantId> participants() {
        return Collections.unmodifiableCollection(participants);
    }

    private int participantCount() {
        return participants.size();
    }

    public LocalDateTime startTime() {
        return LocalDateTime.ofInstant(duration.getStart(), ZoneOffset.UTC);
    }

    public LocalDateTime endTime() {
        return LocalDateTime.ofInstant(duration.getEnd(), ZoneOffset.UTC);
    }
}
