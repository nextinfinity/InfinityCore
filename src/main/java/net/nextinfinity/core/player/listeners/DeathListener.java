package net.nextinfinity.core.player.listeners;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import net.nextinfinity.core.events.GameDeathEvent;

/**
 * The death listener handles entity deaths, sending the GameDeathEvent for in-game players.
 *
 * @see GameDeathEvent
 */
public class DeathListener implements Listener {

	private final Game game;

	public DeathListener(Game game) {
		this.game = game;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDeath(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player bukkitPlayer = (Player) event.getEntity();
			if (event.getFinalDamage() >= bukkitPlayer.getHealth()) {
				GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
				if (player.isPlaying()) {
					event.setDamage(0);
					player.clearInventory();
					player.heal();
					Arena arena = player.getArena();
					Bukkit.getPluginManager().callEvent(new GameDeathEvent(game, arena, player));
				}
			}
		}
	}
}
