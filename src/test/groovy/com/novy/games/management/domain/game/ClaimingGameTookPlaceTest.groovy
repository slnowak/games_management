package com.novy.games.management.domain.game

import builders.GameBuilder
import com.novy.games.management.domain.participant.valueobjects.ParticipantId
import spock.lang.Specification


/**
 * Created by novy on 08.02.15.
 */
class ClaimingGameTookPlaceTest extends Specification {

    private Game game;

    void setup() {
        game = GameBuilder
                .newGame()
                .minThreshold(1)
                .maxThreshold(3)
                .build()

    }

    def "one should not be able to claim pending game took place"() {
        when:
        game.claimTookPlace()

        then:
        thrown(IllegalStateException.class)
    }

    def "one should not be able to claim that already cancelled game took place"() {
        given:
        game.cancel()

        when:
        game.claimTookPlace()

        then:
        thrown(IllegalStateException.class)

    }

    def "one could claim game took place when min participant threshold exceeded"() {
        given:
        final participantId = new ParticipantId("id")
        game.registerParticipant(participantId)

        when:
        game.claimTookPlace()

        then:
        game.state() == GameState.TOOK_PLACE

    }

    def "one could claim game took place when max participant threshold exceeded"() {
        given:
        final firstParticipantId = new ParticipantId("id1")
        final secondParticipantId = new ParticipantId("id2")
        final thirdParticipantId = new ParticipantId("id3")
        game.registerParticipant(firstParticipantId)
        game.registerParticipant(secondParticipantId)
        game.registerParticipant(thirdParticipantId)

        when:
        game.claimTookPlace()

        then:
        game.state() == GameState.TOOK_PLACE

    }
}