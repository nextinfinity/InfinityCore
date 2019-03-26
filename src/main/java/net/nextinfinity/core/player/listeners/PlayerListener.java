package net.nextinfinity.core.player.listeners;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.arena.GameState;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerListener implements Listener {

	private final Game game;

	public PlayerListener(Game game) {
		this.game = game;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDrop(PlayerDropItemEvent event) {
		Player bukkitPlayer = event.getPlayer();
		GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
		if (player.isInGame()) {
			if (player.isPlaying()) {
				Arena arena = player.getArena();
				switch (arena.getState()) {
					case FINISHED:
					case INGAME:
						if (Settings.getItemManage()) {
							return;
						}
				}
			}
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		Player bukkitPlayer = (Player) event.getWhoClicked();
		GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
		if (player.isInGame()) {
			if (player.isPlaying()) {
				Arena arena = player.getArena();
				switch (arena.getState()) {
					case FINISHED:
					case INGAME:
						if (Settings.getItemManage()) {
							return;
						}
				}
			}
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		Player bukkitPlayer = event.getPlayer();
		GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
		if (player.isPlaying()) {
			Arena arena = player.getArena();
			if (arena.getState() == GameState.INGAME) {
				if (Settings.getBlockBreak()) {
					return;
				}
			}
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player bukkitPlayer = event.getPlayer();
		GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
		if (player.isPlaying()) {
			Arena arena = player.getArena();
			if (arena.getState() == GameState.INGAME) {
				if (Settings.getBlockBreak()) {
					return;
				}
			}
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onTeleport(PlayerTeleportEvent event) {
		Player bukkitPlayer = event.getPlayer();
		GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
		if (player.isInGame() && event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player bukkitPlayer = (Player) event.getEntity();
			GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
			if (player.isInGame() && player.getArena().getState() != GameState.INGAME) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHunger(FoodLevelChangeEvent event) {
		Player bukkitPlayer = (Player) event.getEntity();
		GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
		if (player.isPlaying()) {
			if (player.getArena().getState() == GameState.INGAME) {
				if (Settings.getHunger()) {
					return;
				}
			}
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMove(PlayerMoveEvent event) {
		Player bukkitPlayer = event.getPlayer();
		GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
		if (player.isPlaying()) {
			switch (player.getArena().getState()) {
				case LOBBY:
				case STARTING:
					if (bukkitPlayer.getLocation().getY() < 0) {
						player.teleport(player.getArena().getPregame());
					}
					break;
			}
		}
	}
}
