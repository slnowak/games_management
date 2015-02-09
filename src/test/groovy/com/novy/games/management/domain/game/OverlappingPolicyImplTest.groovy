package com.novy.games.management.domain.game

import builders.GameBuilder
import com.novy.games.management.domain.participant.valueobjects.ParticipantId
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.Month

/**
 * Created by novy on 09.02.15.
 */
class OverlappingPolicyImplTest extends Specification {

    private OverlappingPolicy objectUnderTest

    private Game gameBeingChecked
    private GameRepository gameRepositoryMock;
    private ParticipantId participantId = new ParticipantId("andId")

    void setup() {
        gameBeingChecked = GameBuilder
                .newGame()
                .startTime(LocalDateTime.of(2015, Month.JANUARY, 1, 15, 0))
                .durationInMinutes(90)
                .build()

        gameRepositoryMock = Mock(GameRepository.class)

        objectUnderTest = new OverlappingPolicyImpl(gameRepositoryMock)
    }

    def "should return false given non overlapping or cancelled games"() {
        given:
        final cancelledOverlappingGame = GameBuilder
                .newGame()
                .startTime(gameBeingChecked.startTime())
                .durationInMinutes(30)
                .build();

        cancelledOverlappingGame.cancel()

        final nonOverlappingGame = GameBuilder
                .newGame()
                .startTime(gameBeingChecked.endTime())
                .durationInMinutes(30)
                .build();

        gameRepositoryMock.findBy(participantId) >> [
                cancelledOverlappingGame, nonOverlappingGame
        ]

        expect:
        !objectUnderTest.isOverlapping(participantId, gameBeingChecked)

    }

    def "should return true given at least one overlapping game"() {
        given:
        final nonOverlappingGame = GameBuilder
                .newGame()
                .startTime(gameBeingChecked.endTime())
                .durationInMinutes(30)
                .build();

        final overlappingGame = GameBuilder
                .newGame()
                .startTime(gameBeingChecked.endTime().minusSeconds(1))
                .durationInMinutes(30)
                .build();

        gameRepositoryMock.findBy(participantId) >> [
                nonOverlappingGame, overlappingGame
        ]

        expect:
        objectUnderTest.isOverlapping(participantId, overlappingGame)
    }
}