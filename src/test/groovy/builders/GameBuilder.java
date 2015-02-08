package builders;

import com.novy.games.management.domain.game.Game;
import com.novy.games.management.domain.game.ThresholdStrategyImpl;
import com.novy.games.management.domain.game.valueobjects.GameId;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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

    public Game build() {
        return new Game(
                gameId,
                new ThresholdStrategyImpl(minThreshold, maxThreshold)
        );
    }
}
