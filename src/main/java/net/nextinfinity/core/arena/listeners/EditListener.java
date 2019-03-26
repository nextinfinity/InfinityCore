package net.nextinfinity.core.arena.listeners;

import de.tr7zw.itemnbtapi.NBTItem;
import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.ArenaEditor;
import net.nextinfinity.core.player.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The EditListener object is used to manage an arena editing session.
 */
public class EditListener implements Listener {

	private final ArenaEditor editor;
	private final Game game;

	public EditListener(ArenaEditor editor, Game game) {
		this.editor = editor;
		this.game = game;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player bukkitPlayer = event.getPlayer();
		GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
		if (player == editor.getPlayer()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player bukkitPlayer = (Player) event.getWhoClicked();
		GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
		if (player == editor.getPlayer()) {
			ItemStack item = event.getCurrentItem();
			if (item != null) {
				NBTItem itemNBT = new NBTItem(item);
				if (itemNBT.hasKey("addspawn")) {
					editor.addSpawn();
				} else if (itemNBT.hasKey("removespawn")) {
					editor.removeSpawn();
				} else if (itemNBT.hasKey("pregame")) {
					editor.setPregame();
				} else if (itemNBT.hasKey("spectator")) {
					editor.setSpectator();
				} else if (itemNBT.hasKey("close")) {
					editor.close();
				}
				bukkitPlayer.closeInventory();
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player bukkitPlayer = event.getPlayer();
		GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
		if (player == editor.getPlayer()) {
			ItemStack item = bukkitPlayer.getInventory().getItemInMainHand();
			if (item != null) {
				NBTItem itemNBT = new NBTItem(item);
				if (itemNBT.hasKey("menu")) {
					bukkitPlayer.openInventory(editor.getMenu());
				}
			}
		}
	}

}
