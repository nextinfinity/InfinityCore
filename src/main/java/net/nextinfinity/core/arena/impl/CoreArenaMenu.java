package net.nextinfinity.core.arena.impl;

import net.nextinfinity.core.arena.ArenaMenu;
import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.arena.ArenaManager;
import net.nextinfinity.core.item.ItemBuilder;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.ConfigManager;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoreArenaMenu implements ArenaMenu {

	private final Game game;
	private final ArenaManager manager;
	private final Inventory menu;

	public CoreArenaMenu(Game game, ArenaManager manager) {
		this.game = game;
		this.manager = manager;
		this.menu = Bukkit.createInventory(null, 9, "Arena Selection");
	}

	@Override
	public void open(GamePlayer player) {
		player.getBukkitPlayer().openInventory(menu);
	}

	@Override
	public void update() {
		Map<String, List<Arena>> map = manager.getArenaMap();
		for (ItemStack item : menu.getContents()) {
			if (item != null && item.hasItemMeta()) {
				ItemMeta meta = item.getItemMeta();
				String name = meta.getDisplayName();
				if (name != null) {
					int activeAmount = map.get(ChatColor.stripColor(name.toLowerCase())).size();
					List<String> lore = new ArrayList<>();
					lore.add(Settings.getSecondary() + "" + activeAmount + " arenas active");
					lore.add(Settings.getSecondary() + "Right click to join!");
					meta.setLore(lore);
					item.setItemMeta(meta);
				}
			}
		}
	}

	@Override
	public void reset() {
		menu.clear();
		createMenu();
	}

	private void createMenu() {
		Map<String, List<Arena>> map = manager.getArenaMap();
		FileConfiguration config = new ConfigManager(game, "arenas.yml").getConfig();
		for (String arenaName : map.keySet()) {
			int activeAmount = map.get(arenaName).size();
			String matName = config.getString(arenaName + ".item");
			Material material = matName != null ? Material.getMaterial(matName) : Material.GRASS_BLOCK;
			ItemBuilder itemBuilder = new ItemBuilder(material);
			itemBuilder.setName(Settings.getPrimary() + "" + ChatColor.BOLD + arenaName.toUpperCase());
			menu.addItem(itemBuilder.getItem());
		}
		update();
	}
}
