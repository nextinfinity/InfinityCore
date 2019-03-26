package net.nextinfinity.core.events;

import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.Game;

/**
 * The GameCountdownEvent is fired when an arena begins the transition from the lobby state to the in-game state.
 */
public class GameCountdownEvent extends AbstractEvent {

    /**
     * Creates a new GameCountdownEvent
     *
     * @param game the game which is running
     * @param arena the arena which is beginning the countdown
     */
    public GameCountdownEvent(Game game, Arena arena) {
        super(game, arena);
    }

}
