package com.novy.games.management.domain.game

import builders.GameBuilder
import com.novy.games.management.domain.participant.valueobjects.ParticipantId
import spock.lang.Specification


/**
 * Created by novy on 08.02.15.
 */
class GameCancellationTest extends Specification {

    private Game game;

    void setup() {
        game = GameBuilder
                .newGame()
                .minThreshold(1)
                .maxThreshold(1)
                .build()

    }

    def "one should not be able to cancel already cancelled game"() {
        given:
        game.cancel()

        when:
        game.cancel()

        then:
        thrown(IllegalStateException.class)

    }

    def "one should not be able to cancel game that took place"() {
        given:
        final ParticipantId participantId = new ParticipantId("id")
        game.registerParticipant(participantId)
        game.claimTookPlace()

        when:
        game.cancel()

        then:
        thrown(IllegalStateException.class)

    }

    def "should be able to cancel game otherwise"() {
        when:
        game.cancel()

        then:
        game.state() == GameState.CANCELLED
    }

}