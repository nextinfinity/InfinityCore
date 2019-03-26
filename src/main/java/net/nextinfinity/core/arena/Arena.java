package net.nextinfinity.core.arena;

import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.team.Team;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

/**
 *
 */
public interface Arena {

	/**
	 * Attempts to add a player to the arena. Will fail if the arena is not in the lobby state, is full, or the entity is in another arena.
	 *
	 * @param player the GamePlayer to attempt to add
	 * @return true if the action is successful, false otherwise
	 */
	boolean addPlayer(GamePlayer player);

	/**
	 * Removes a player from the arena if they are playing in it.
	 *
	 * @param player the GamePlayer to remove
	 */
	void removePlayer(GamePlayer player);

	/**
	 * Attempts to add a spectator to the arena.
	 *
	 * @param player the GamePlayer to attempt to add
	 * @return true is the action is successful, false otherwise
	 */
	boolean addSpectator(GamePlayer player);

	/**
	 * Removes a player from the arena if they are spectating it.
	 *
	 * @param player the GamePlayer to remove
	 */
	void removeSpectator(GamePlayer player);

	/**
	 * Spawns a entity in the arena.
	 *
	 * @param player the entity to spawn
	 */
	void spawnPlayer(GamePlayer player);

	/**
	 * Begins the countdown in the arena, which signifies a transition from the lobby state to the in-game state.
	 */
	void countdown();

	/**
	 * Starts the arena, and begins the in-game state.
	 */
	void start();

	/**
	 * Ends the arena, removes all current players, and awards the winner(s).
	 */
	void end();

	/**
	 * Gets the list of players currently playing in the arena.
	 *
	 * @return the list of players in the arena
	 */
	List<GamePlayer> getPlayers();

	/**
	 * Gets the list of players currently spectating the arena.
	 *
	 * @return the list of spectators for the arena.
	 */
	List<GamePlayer> getSpectators();

	/**
	 * Gets the state of the arena.
	 *
	 * @return the GameState the arena is currently in - lobby, starting, or ingame
	 * @see GameState
	 */
	GameState getState();

	/**
	 * Gets the ScoreboardManager of the arena.
	 *
	 * @return the ScoreboardManager object for the arena.
	 * @see ScoreboardManager
	 */
	ScoreboardManager getScoreboard();

	/**
	 * Gets the name of the map for the arena.
	 *
	 * @return the name of the arena's map
	 */
	String getName();

	/**
	 * Adds a spawn to the arena and categorizes it as a free-for-all spawn.
	 *
	 * @param location the location of the spawn to add
	 */
	void addSpawn(Location location);

	/**
	 * Adds a spawn the the arena and associates it with a certain team
	 *
	 * @param location the location of the spawn to add
	 * @param team the team to associate the spawn with
	 */
	void addSpawn(Location location, Team team);

	/**
	 * Sets the pregame teleport location for the arena.
	 *
	 * @param pregame the location to teleport players before the game
	 */
	void setPregame(Location pregame);

	/**
	 * Gets the pregame teleport location for the arena.
	 *
	 * @return the location to teleport players before the game
	 */
	Location getPregame();

	/**
	 * Sets the spectator teleport location for the arena.
	 *
	 * @param spectatorSpawn the location to teleport spectators initially
	 */
	void setSpectatorSpawn(Location spectatorSpawn);

	/**
	 * Gets the World of the arena.
	 *
	 * @return the World the arena is in
	 */
	World getWorld();

}
