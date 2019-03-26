package net.nextinfinity.core.player;

import net.nextinfinity.core.team.Team;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.classes.GameClass;

/**
 * The GamePlayer object serves as an interface/wrapper to add CTCore function on top of the Bukkit Player object.
 * <br>In some cases a method of GamePlayer is merely a wrapper for the Player method. This serves to improve readability of core code.
 */
public interface GamePlayer {

    /**
     * Sets the players arena to a specific Arena object. This signifies that the player is playing in or spectating that arena.
     *
     * @param arena the arena the player is in
     */
    void setArena(Arena arena);

    /**
     * Gets the Arena object for the arena the player is in. Returns null if the player is not in an arena.
     *
     * @return The Arena object for the arena the player is in, if there is one
     */
    Arena getArena();

    /**
     * Gets the in-game status of the player. Is equivalent to checking if the player's arena is not null.
     *
     * @return true if the player is in an arena, false otherwise
     */
    boolean isInGame();

    /**
     * Gets the playing status of the player.
     *
     * @return true if the player is playing in an arena, false otherwise
     */
    boolean isPlaying();

    /**
     * Gets the spectating status of the player.
     *
     * @return true if the player is spectating an arena, false otherwise
     */
    boolean isSpectating();

    /**
     * Gets the Bukkit player object for the player.
     *
     * @return the Bukkit player object for the player
     */
    Player getBukkitPlayer();

    /**
     * Sets the active game class for the player. This is the class that will be used when the player is spawned in an arena.
     *
     * @param gameClass the game class to set as active
     */
    void setClass(GameClass gameClass);

    /**
     * Gets the active game class for the player.
     *
     * @return the active game class for the player
     */
    GameClass getGameClass();

    /**
     * Gives the player the items provided by their active class.
     */
    void initializeClass();

    /**
     * Stores the player's inventory in its current state.
     */
    void saveInventory();

    /**
     * Restores the player's inventory to its last stored state.
     */
    void restoreInventory();

    /**
     * Checks if the player has permission to use a game class.
     *
     * @param gameClass the class to check permission for
     * @return true if the player has access, false otherwise
     */
    boolean hasClass(GameClass gameClass);

    /**
     * Sends the player a message with the game's prefix.
     *
     * @param message the content of the message
     */
    void sendMessage(String message);

    /**
     * Gets the player's team. Returns null if the player is not in-game or if the game is a free-for-all
     *
     * @return the team the player is on, if there is one
     */
    Team getTeam();

    /**
     * Sets the player's team.
     *
     * @param team the team to set the player on
     */
    void setTeam(Team team);

    /**
     * Resets the team the player is on to a null state.
     */
    void resetTeam();

    /**
     * Heals the player and removes any status effects.
     */
    void heal();

    /**
     * Teleports the player to a location.
     *
     * @param location the location to teleport the player to
     */
    void teleport(Location location);

    /**
     * Clears the player's inventory, including their armor.
     */
    void clearInventory();

    /**
     * Sets if the player is flying. If the player is flying, makes sure the player is allowed to fly first.
     *
     * @param flying whether or not the player is flying
     */
    void setFlying(boolean flying);

}