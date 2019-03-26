package net.nextinfinity.core.classes.listeners;

import de.tr7zw.itemnbtapi.NBTItem;
import net.nextinfinity.core.Game;
import net.nextinfinity.core.classes.ClassEditor;
import net.nextinfinity.core.player.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class EditListener implements Listener {

	private final ClassEditor editor;
	private final Game game;

	public EditListener(ClassEditor editor, Game game) {
		this.editor = editor;
		this.game = game;
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player bukkitPlayer = (Player) event.getWhoClicked();
		GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
		if (player == editor.getPlayer()) {
			ItemStack item = event.getCurrentItem();
			if (item != null) {
				NBTItem itemNBT = new NBTItem(item);
				if (itemNBT.hasKey("glass")) {
					event.setCancelled(true);
				} else if (itemNBT.hasKey("color") && event.getInventory().getSize() == 45) {
					bukkitPlayer.openInventory(editor.getColorMenu());
					event.setCancelled(true);
				} else if (itemNBT.hasKey("potion")) {
					bukkitPlayer.openInventory(editor.getCurrentEffectMenu());
					event.setCancelled(true);
				} else if (itemNBT.hasKey("addeffect")) {
					bukkitPlayer.openInventory(editor.getAddEffectMenu());
					event.setCancelled(true);
				} else if (itemNBT.hasKey("saveeffects")) {
					bukkitPlayer.closeInventory();
					bukkitPlayer.openInventory(editor.getInventory());
					event.setCancelled(true);
				} else if (itemNBT.hasKey("close")) {
					bukkitPlayer.closeInventory();
					editor.close();
					event.setCancelled(true);
				} else if (itemNBT.hasKey("color")) {
					editor.setColor(item);
					bukkitPlayer.closeInventory();
					bukkitPlayer.openInventory(editor.getInventory());
					event.setCancelled(true);
				} else if (itemNBT.hasKey("effect-current")) {
					editor.removeEffect(itemNBT.getString("effect"));
					event.setCancelled(true);
				} else if (itemNBT.hasKey("effect-new")) {
					editor.addEffect(itemNBT.getString("effect"));
					bukkitPlayer.closeInventory();
					bukkitPlayer.openInventory(editor.getCurrentEffectMenu());
					event.setCancelled(true);

				}
			}
		}
	}
}
