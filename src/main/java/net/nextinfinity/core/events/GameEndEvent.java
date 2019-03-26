package net.nextinfinity.core.events;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.team.Team;

/**
 * The GameEndEvent is fired when an arena ends.
 */
public class GameEndEvent extends AbstractEvent {

    private final Team winner;

    /**
     * Creates a new GameEndEvent for a single entity winner.
     *
     * @param game the game which is running
     * @param arena the arena which ended
     * @param winner the entity or team who won
     */
    public GameEndEvent(Game game, Arena arena, Team winner) {
        super(game, arena);
        this.winner = winner;
    }

    /**
     * Gets the entity who won.
     *
     * @return the GamePlayer object of the entity who won or the Team object of the Team who won
     */
    public Object getWinner() {
        return winner;
    }

}
