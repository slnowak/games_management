package builders;

import com.novy.games.management.domain.game.Game;
import com.novy.games.management.domain.game.ThresholdPolicyImpl;
import com.novy.games.management.domain.game.valueobjects.GameId;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.threeten.extra.Interval;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * Created by novy on 08.02.15.
 */

@Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor(staticName = "newGame")
public class GameBuilder {

    private GameId gameId = new GameId("defaultGameId");
    private int minThreshold = 10;
    private int maxThreshold = 10;
    private LocalDateTime startTime = LocalDateTime.of(2015, Month.JANUARY, 1, 15, 0);
    private int durationInMinutes = 90;

    public Game build() {
        return new Game(
                gameId,
                new ThresholdPolicyImpl(minThreshold, maxThreshold),
                startTime,
                durationInMinutes
        );
    }
}
