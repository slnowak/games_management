package com.novy.games.management.domain.game

import builders.GameBuilder
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.Month

/**
 * Created by novy on 09.02.15.
 */
class OverlappingTest extends Specification {

    @Shared
    private Game objectUnderTest;

    void setup() {
        objectUnderTest = GameBuilder
                .newGame()
                .startTime(LocalDateTime.of(2015, Month.JANUARY, 1, 15, 0))
                .durationInMinutes(90)
                .build()
    }

    def "should return false given two games starting on totally different time"() {
        given:
        final nonOverlappingGame = GameBuilder
                .newGame()
                .startTime(LocalDateTime.of(2015, Month.JANUARY, 2, 15, 0))
                .durationInMinutes(90)
                .build()

        expect:
        !objectUnderTest.overlaps(nonOverlappingGame)

    }

    def "should return false given two games that took place sequentially"() {
        given:
        final nextGame = GameBuilder
                .newGame()
                .startTime(objectUnderTest.endTime())
                .durationInMinutes(90)
                .build()

        expect:
        !objectUnderTest.overlaps(nextGame)

    }

    def "should return true if games overlaps somehow"() {
        expect:
        objectUnderTest.overlaps(overlappingGame)

        where:
        overlappingGame << [
                GameBuilder
                        .newGame()
                        .startTime(objectUnderTest.startTime().minusMinutes(10))
                        .durationInMinutes(90)
                        .build(),

                GameBuilder
                        .newGame()
                        .startTime(objectUnderTest.startTime().plusMinutes(10))
                        .durationInMinutes(90)
                        .build(),

                GameBuilder
                        .newGame()
                        .startTime(objectUnderTest.startTime().minusMinutes(10))
                        .durationInMinutes(110)
                        .build(),

                GameBuilder
                        .newGame()
                        .startTime(objectUnderTest.startTime().plusMinutes(10))
                        .durationInMinutes(70)
                        .build(),
        ]

    }
}
