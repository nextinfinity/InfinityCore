package net.nextinfinity.core.player.listeners;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.tasks.TeleportTask;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * The JoinListener object receives joining players and teleports them to spawn using a TeleportTask.
 *
 * @see TeleportTask
 */
public class JoinListener implements Listener {

	private final Game game;

	public JoinListener(Game game) {
		this.game = game;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player bukkitPlayer = event.getPlayer();
		game.getPlayerHandler().loadPlayer(bukkitPlayer);
		if(Settings.getJoinTeleport()) {
			new TeleportTask(bukkitPlayer, Settings.getLobby()).runTaskLater(game, 5);
		}
	}
}
