package com.novy.games.management.domain.game

import builders.GameBuilder
import com.novy.games.management.domain.participant.valueobjects.ParticipantId
import spock.lang.Specification


/**
 * Created by novy on 08.02.15.
 */
class UnregisteringParticipantTest extends Specification {

    private Game game;

    void setup() {
        game = GameBuilder
                .newGame()
                .minThreshold(1)
                .maxThreshold(3)
                .build()

    }


    def "should not be able to unregister not registered participant"() {
        given:
        final ParticipantId participantId = new ParticipantId("id")

        when:
        game.unregisterParticipant(participantId)

        then:
        thrown(IllegalArgumentException.class)

    }

    def "should not be able to unregistered from cancelled game"() {
        given:
        final ParticipantId participantId = new ParticipantId("id")
        game.registerParticipant(participantId)
        game.cancel()

        when:
        game.unregisterParticipant(participantId)

        then:
        thrown(IllegalStateException.class)

    }

    def "should not be able to unregister from game that took place"() {
        given:
        final ParticipantId participantId = new ParticipantId("id")
        game.registerParticipant(participantId)
        game.claimTookPlace()

        when:
        game.unregisterParticipant(participantId)

        then:
        thrown(IllegalStateException.class)

    }

    def "participant should not be registered anymore after performing unregister"() {
        given:
        final ParticipantId participantId = new ParticipantId("id")
        game.registerParticipant(participantId)

        when:
        game.unregisterParticipant(participantId)

        then:
        !game.participantRegistered(participantId)

    }

    def "should transit back to MIN_PARTICIPANTS_GATHERED if necessary"() {
        given:
        final ParticipantId firstParticipantId = new ParticipantId("id1")
        final ParticipantId secondParticipantId = new ParticipantId("id2")
        final ParticipantId thirdParticipantId = new ParticipantId("id3")

        game.registerParticipant(firstParticipantId)
        game.registerParticipant(secondParticipantId)
        game.registerParticipant(thirdParticipantId)

        when:
        game.unregisterParticipant(thirdParticipantId)

        then:
        game.state() == GameState.MIN_PARTICIPANTS_GATHERED

    }

    def "should transit back to PENDING if necessary"() {
        given:
        final ParticipantId participantId = new ParticipantId("id1")
        game.registerParticipant(participantId)

        when:
        game.unregisterParticipant(participantId)

        then:
        game.state() == GameState.PENDING

    }
}