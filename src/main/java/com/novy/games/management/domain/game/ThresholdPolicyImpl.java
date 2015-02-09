package com.novy.games.management.domain.game;

/**
 * Created by novy on 08.02.15.
 */
public class ThresholdPolicyImpl implements ThresholdPolicy {

    private final int minThreshold;
    private final int maxThreshold;

    public ThresholdPolicyImpl(int minThreshold, int maxThreshold) {
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    @Override
    public boolean minThresholdExceeded(int participants) {
        return participants >= minThreshold;
    }

    @Override
    public boolean maxThresholdExceeded(int participants) {
        return participants >= maxThreshold;
    }
}
