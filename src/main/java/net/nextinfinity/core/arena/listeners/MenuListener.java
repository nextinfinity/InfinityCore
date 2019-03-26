package net.nextinfinity.core.arena.listeners;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The MenuListener object listens for entity clicks in the arena selection GUI and handles them accordingly.
 */
public class MenuListener implements Listener {

	private final Game game;

	public MenuListener(Game game) {
		this.game = game;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getClickedInventory().getName() != null &&
			event.getClickedInventory().getName().equals("Arena Selection")) {
			ItemStack item = event.getCurrentItem();
			if (item != null) {
				if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
					Player bukkitPlayer = (Player) event.getWhoClicked();
					GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
					player.getBukkitPlayer().closeInventory();
					player.sendMessage(Settings.getSecondary() + "Finding an arena...");
					String arenaName = ChatColor.stripColor(item.getItemMeta().getDisplayName().toLowerCase());
					Arena arena = game.getArenaManager().getArena(arenaName);
					player.sendMessage(Settings.getSecondary() + "Connecting...");
					arena.addPlayer(player);
				}
			}
			event.setCancelled(true);
		}
	}

}
