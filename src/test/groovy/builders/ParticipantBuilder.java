package builders;

import com.novy.games.management.domain.participant.Participant;
import com.novy.games.management.domain.participant.valueobjects.ParticipantId;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 08.02.15.
 */

@Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor(staticName = "newParticipant")
public class ParticipantBuilder {

    private ParticipantId participantId = new ParticipantId("defaultParticipantId");

    public Participant build() {
        return new Participant(participantId);
    }
}
