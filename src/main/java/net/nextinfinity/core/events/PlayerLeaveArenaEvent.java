package net.nextinfinity.core.events;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.player.GamePlayer;

/**
 * PlayerLeaveArenaEvent is fired when a player (not a spectator) leaves an Arena.
 */
public class PlayerLeaveArenaEvent extends AbstractEvent {

	private final GamePlayer player;

	/**
	 * Creates a new PlayerLeaveArenaEvent.
	 *
	 * @param game the game which is running
	 * @param arena the arena the player left
	 * @param player the player who left
	 */
	public PlayerLeaveArenaEvent(Game game, Arena arena, GamePlayer player) {
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
