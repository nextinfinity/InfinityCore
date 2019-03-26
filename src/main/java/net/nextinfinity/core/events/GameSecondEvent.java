package net.nextinfinity.core.events;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;

/**
 * The GameSecondEvent is fired every time a second is ticked in an arena.
 */
public class GameSecondEvent extends AbstractEvent {

    private final int timeLeft;

    /**
     * Creates a new GameSecondEvent.
     *
     * @param game the game which is running
     * @param arena the arena which the timer tick is for
     * @param timeLeft the time remaining in the arena, in seconds
     */
    public GameSecondEvent(Game game, Arena arena, int timeLeft) {
        super(game, arena);
        this.timeLeft = timeLeft;
    }

    /**
     * Gets the amount of time left in the arena.
     *
     * @return time remaining in seconds
     */
    public int getTimeRemaining() {
        return timeLeft;
    }

}
