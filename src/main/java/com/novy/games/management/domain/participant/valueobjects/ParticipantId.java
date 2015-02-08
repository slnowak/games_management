package com.novy.games.management.domain.participant.valueobjects;

import com.novy.games.management.domain.common.UUIDBasedIdentifier;

/**
 * Created by novy on 08.02.15.
 */
public class ParticipantId extends UUIDBasedIdentifier {

    public ParticipantId() {
        super();
    }

    public ParticipantId(String id) {
        super(id);
    }
}
