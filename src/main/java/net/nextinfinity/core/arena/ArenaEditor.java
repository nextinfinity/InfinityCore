package net.nextinfinity.core.arena;

import net.nextinfinity.core.player.GamePlayer;
import org.bukkit.inventory.Inventory;

/**
 * The ArenaEditor object is created when an admin initializes an arena editing session. It may be used to add/remove spawns or perform other arena administration tasks.
 */
public interface ArenaEditor {

    /**
     * Opens the editing session. This should be called after the creation of the ArenaEditor object.
     * <br>The entity will be teleported to a copy of the arena.
     *
     * @return the ArenaEditor object for the initialized editing session
     */
    ArenaEditor open();

    /**
     * Closes the editing session and returns the entity to their previous state.
     * <br>It is at this point that the edited list of spawns is saved to the disk.
     */
    void close();

    /**
     * Adds a new spawn at the players current location.
     */
    void addSpawn();

    /**
     * Removes the spawn at the players current location, if one exists.
     */
    void removeSpawn();

    /**
     * Sets the pregame spawn to the player's current location.
     */
    void setPregame();

    /**
     * Sets the spectator spawn to the player's current location.
     */
    void setSpectator();

    /**
     * Gets the GamePlayer associated with the ArenaEditor
     *
     * @return the GamePlayer using the ArenaEditor
     */
    GamePlayer getPlayer();

    /**
     * Gets the inventory menu for the ArenaEditor.
     *
     * @return the Inventory object of the menu
     */
    Inventory getMenu();

}
