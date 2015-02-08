package com.novy.games.management.domain.participant;

import com.google.common.collect.Lists;
import com.novy.games.management.domain.common.IdentifiedObject;
import com.novy.games.management.domain.participant.valueobjects.ParticipantId;

import java.util.Collection;

/**
 * Created by novy on 08.02.15.
 */
public class Participant extends IdentifiedObject<ParticipantId> {

    public Participant(ParticipantId participantId) {
        super(participantId);
    }


}
