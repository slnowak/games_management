package com.novy.games.management.domain.game;

/**
 * Created by novy on 08.02.15.
 */
public interface ThresholdPolicy {

    boolean minThresholdExceeded(int participants);
    boolean maxThresholdExceeded(int participants);
}
