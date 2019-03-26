package net.nextinfinity.core.events;

import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.Game;
import net.nextinfinity.core.player.GamePlayer;

/**
 * The GameLeaveEvent is fired when a entity either leaves or is removed from an arena.
 */
public class GameLeaveEvent extends AbstractEvent {

    private final GamePlayer player;

    /**
     * Creates a new GameLeaveEvent.
     *
     * @param game the game which is running
     * @param arena the arena which was left
     * @param player the entity who left
     */
    public GameLeaveEvent(Game game, Arena arena, GamePlayer player) {
        super(game, arena);
        this.player = player;
    }

    /**
     * Gets the entity who left or was removed.
     *
     * @return the GamePlayer object of the entity
     */
    public GamePlayer getPlayer() {
        return player;
    }

}
