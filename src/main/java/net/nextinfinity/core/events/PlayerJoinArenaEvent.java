package net.nextinfinity.core.events;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.player.GamePlayer;

/**
 * PlayerJoinArenaEvent is fired when a player (not a spectator) joins an Arena.
 */
public class PlayerJoinArenaEvent extends AbstractEvent {

	private final GamePlayer player;

	/**
	 * Creates a new PlayerJoinArenaEvent.
	 *
	 * @param game the game which is running
	 * @param arena the arena the player joined
	 * @param player the player who joined
	 */
	public PlayerJoinArenaEvent(Game game, Arena arena, GamePlayer player) {
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
