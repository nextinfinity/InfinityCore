package net.nextinfinity.core.player;

import net.nextinfinity.core.player.impl.CoreGamePlayer;
import org.bukkit.entity.Player;

/**
 * The PlayerHandler object manages the GamePlayer objects for online Bukkit players.
 */
public interface PlayerHandler {

    /**
     * Loads a GamePlayer object for the specified Bukkit entity. This should only be called on entity join.
     *
     * @param player the Bukkit entity to load a GamePlayer object for
     * @return the loaded GamePlayer
     */
    GamePlayer loadPlayer(Player player);

    /**
     * Unloads the GamePlayer object for the specified Bukkit entity. This should only be called on entity disconnect.
     *
     * @param player the Bukkit entity to unload the GamePlayer object for
     */
    void unloadPlayer(Player player);

    /**
     * Obtains the GamePlayer object for the specified Bukkit entity.
     *
     * @param player the Bukkit entity to obtain the GamePlayer object for
     * @return the GamePlayer object
     */
    GamePlayer getPlayer(Player player);

    /**
     * Alters the Java class to be used for players, in case a game wishes to use a custom player class.
     * The new class must implement GamePlayer.
     * It may be helpful for custom player classes to extend CoreGamePlayer, as this implements all necessary functions.
     *
     * @param playerClass the new Java class to use for players
     * @see CoreGamePlayer
     */
    void setPlayerClass(Class<? extends GamePlayer> playerClass);
}
