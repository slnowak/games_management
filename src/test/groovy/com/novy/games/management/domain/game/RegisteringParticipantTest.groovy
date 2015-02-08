package com.novy.games.management.domain.game

import builders.GameBuilder
import com.novy.games.management.domain.participant.valueobjects.ParticipantId
import spock.lang.Specification

/**
 * Created by novy on 08.02.15.
 */
class RegisteringParticipantTest extends Specification {

    private Game game;

    final firstParticipantId = new ParticipantId("id1")
    final secondParticipantId = new ParticipantId("id2")
    final thirdParticipantId = new ParticipantId("id3")
    final fourthParticipantId = new ParticipantId("id4")

    void setup() {
        game = GameBuilder
                .newGame()
                .minThreshold(2)
                .maxThreshold(3)
                .build();

    }

    def "should be able to add participant when game in pending state and max threshold not reached"() {
        when:
        final participantId = new ParticipantId("aParticipantId")
        game.registerParticipant(participantId)

        then:
        game.participantRegistered(participantId)
    }

    def "game should transit to MIN_PARTICIPANT_GATHERED state when min threshold reached"() {
        given:
        game.registerParticipant(firstParticipantId)

        when:
        game.registerParticipant(secondParticipantId)

        then:
        game.state() == GameState.MIN_PARTICIPANTS_GATHERED

    }

    def "game should transit to MAX_PARTICIPANT_GATHERED state when max threshold reached"() {
        given:
        game.registerParticipant(firstParticipantId)
        game.registerParticipant(secondParticipantId)

        when:
        game.registerParticipant(thirdParticipantId)

        then:
        game.state() == GameState.MAX_PARTICIPANTS_GATHERED

    }

    def "should not be able to register participant twice"() {
        given:
        game.registerParticipant(firstParticipantId)

        when:
        game.registerParticipant(firstParticipantId)

        then:
        final thrownException = thrown(IllegalStateException.class)
        thrownException.message == "Participant already registered!"

    }

    def "should not be able to register participant when max participant threshold exceeded"() {
        given:
        game.registerParticipant(firstParticipantId)
        game.registerParticipant(secondParticipantId)
        game.registerParticipant(thirdParticipantId)

        when:
        game.registerParticipant(fourthParticipantId)

        then:
        thrown(IllegalStateException.class)

    }

    def "should not be able to register participant when game is cancelled "() {
        given:
        game.cancel()

        when:
        game.registerParticipant(firstParticipantId)

        then:
        thrown(IllegalStateException.class)

    }

    def "should not be able to register participant to a game that already took place"() {
        given:
        game.registerParticipant(firstParticipantId)
        game.registerParticipant(secondParticipantId)
        game.claimTookPlace()

        when:
        game.registerParticipant(thirdParticipantId)

        then:
        thrown(IllegalStateException.class)

    }
}
