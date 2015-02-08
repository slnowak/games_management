package com.novy.games.management.domain.game.valueobjects

import spock.lang.Specification

/**
 * Created by novy on 08.02.15.
 */
class PhoneTest extends Specification {

    def "should not be able to create phone number containing non numeric characters"() {

        when:
        Phone.of(invalidPhoneString)

        then:
        thrown(IllegalArgumentException.class)

        where:
        invalidPhoneString << [
                "792234,63",
                "456s3179w"
        ]

    }

    def "should not be able to create phone namber other than 9 digits length"() {

        when:
        Phone.of(phoneString)

        then:
        thrown(IllegalArgumentException.class)

        where:
        phoneString << [
                "7896541231",
                "123"
        ]

    }

    def "should be able to create phone number with proper phone number string"() {

        given:
        def expectedPhoneString = "795364789"

        when:
        def phone = Phone.of(expectedPhoneString)

        then:
        phone.phoneString() == expectedPhoneString
    }
}
