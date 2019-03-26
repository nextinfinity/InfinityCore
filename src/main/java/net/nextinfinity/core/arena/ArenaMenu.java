package net.nextinfinity.core.arena;

import net.nextinfinity.core.player.GamePlayer;

/**
 * The ArenaMenu object handles the arena selection menu, where players may view the status of lobbies for each map of the game and join one if possible.
 */
public interface ArenaMenu {

    /**
     * Opens the arena selection menu for a entity
     *
     * @param player the entity to open the menu for
     */
    void open(GamePlayer player);

    /**
     * Updates the menu entry of an arena. This should be called every time the status of an arena changes.
     */
    void update();

    /**
     * Clears the current menu and generates a new one.
     */
    void reset();
}
