package net.nextinfinity.core.events;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;

/**
 * The GameStartEvent is fired when an arena starts, and can be used to add game-specific actions at the start of a match.
 */
public class GameStartEvent extends AbstractEvent {

    /**
     * Creates a new GameStartEvent
     *
     * @param game the game which is running
     * @param arena the arena which is starting
     */
    public GameStartEvent(Game game, Arena arena) {
        super(game, arena);
    }

}
