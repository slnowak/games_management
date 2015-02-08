package com.novy.games.management.domain.game.valueobjects

import spock.lang.Specification

/**
 * Created by novy on 07.02.15.
 */
class EmailTest extends Specification {

    def "should not be able to create invalid email"() {

        when:
        Email.of(emailString)

        then:
        thrown(IllegalArgumentException.class)

        where:
        emailString << [
                "without@domain",
                "",
                "randomString",
                "@without.user.name",
                "with.too.short.domain@p.c"
        ]


    }

    def "should be able to create valid email address"() {

        given:
        def validEmailAddress = "validEmailAddresss@gmail.com"

        when:
        def email = Email.of(validEmailAddress)

        then:
        email.emailString() == validEmailAddress
    }
}
