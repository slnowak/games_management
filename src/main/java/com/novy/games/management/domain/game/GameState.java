package com.novy.games.management.domain.game;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by novy on 08.02.15.
 */
public enum GameState {

    PENDING,
    MIN_PARTICIPANTS_GATHERED,
    MAX_PARTICIPANTS_GATHERED,
    TOOK_PLACE,
    CANCELLED;

    private static Map<GameState, Set<GameState>> allowedTransitions;

    static {
        allowedTransitions = ImmutableMap.of(
                PENDING, EnumSet.of(
                        MIN_PARTICIPANTS_GATHERED, MAX_PARTICIPANTS_GATHERED, CANCELLED
                ),

                MIN_PARTICIPANTS_GATHERED, EnumSet.of(
                        PENDING, MAX_PARTICIPANTS_GATHERED, TOOK_PLACE, CANCELLED
                ),

                MAX_PARTICIPANTS_GATHERED, EnumSet.of(
                        PENDING, MIN_PARTICIPANTS_GATHERED, TOOK_PLACE, CANCELLED
                ),

                TOOK_PLACE, EnumSet.of(
                        CANCELLED
                ),

                CANCELLED, Collections.emptySet()
        );
    }

    public static boolean canTransit(GameState from, GameState to) {
        return allowedTransitions.get(from)
                .contains(to);
    }
}
