package net.nextinfinity.core.events;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.player.GamePlayer;

/**
 * The GameJoinEvent is fired every time a entity joins an arena.
 */
public class GameJoinEvent extends AbstractEvent {

    private final GamePlayer player;

    /**
     * Creates a new GameJoinEvent.
     *
     * @param game the game which is running
     * @param arena the arena which was joined
     * @param player the entity who joined
     */
    public GameJoinEvent(Game game, Arena arena, GamePlayer player) {
        super(game, arena);
        this.player = player;
    }

    /**
     * Gets the entity who joined the arena.
     *
     * @return the GamePlayer object of the entity who joined
     */
    public GamePlayer getPlayer() {
        return player;
    }

}
