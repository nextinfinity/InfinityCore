package net.nextinfinity.core.events;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.player.GamePlayer;

/**
 * The GameDeathEvent is fired when a entity dies in an arena.
 * <br>It is up the the game to call the respawning or removal of the entity from the arena. This event should be used
 * in place of PlayerDeathEvent.
 * In general, the game core does not seek to handle all player events - for example, item interaction. However,
 * some things about handling the death event are common across games, so it makes sense to do them automatically.
 */
public class GameDeathEvent extends AbstractEvent {

    private final GamePlayer player;

    /**
     * Creates a new GameDeathEvent.
     *
     * @param game the game which is running
     * @param arena the arena the death occured in
     * @param player the entity who died
     */
    public GameDeathEvent(Game game, Arena arena, GamePlayer player) {
        super(game, arena);
        this.player = player;
    }

    /**
     * Gets the entity who died.
     *
     * @return the GamePlayer object of the dead entity
     */
    public GamePlayer getPlayer() {
        return player;
    }

}
