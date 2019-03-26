package net.nextinfinity.core.player.listeners;

import net.nextinfinity.core.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * The LeaveListener objects listens for leaving players and calls for them to be unloaded.
 */
public class LeaveListener implements Listener {

    private final Game game;

    public LeaveListener(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        game.getPlayerHandler().unloadPlayer(event.getPlayer());
    }
}
