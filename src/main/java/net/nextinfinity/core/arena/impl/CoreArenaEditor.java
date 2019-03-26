package net.nextinfinity.core.arena.impl;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.ArenaEditor;
import net.nextinfinity.core.arena.listeners.EditListener;
import net.nextinfinity.core.item.ItemBuilder;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.ConfigManager;
import net.nextinfinity.core.utils.Serializer;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CoreArenaEditor implements ArenaEditor {

	private final Game game;
	private final World world;
	private final String name;
	private final GamePlayer player;
	private final Location origin;
	private final List<Location> spawns = new ArrayList<>();
	private Location pregame;
	private Location spectator;
	private final EditListener listener;
	private Inventory menu;

	public CoreArenaEditor(Game game, World world, String name, GamePlayer player) {
		this.game = game;
		this.world = world;
		this.name = name;
		this.player = player;
		origin = player.getBukkitPlayer().getLocation();
		this.listener = new EditListener(this, game);
		createMenu();
	}

	@Override
	public ArenaEditor open() {
		ConfigManager configManager = new ConfigManager(game, "arenas.yml");
		FileConfiguration arenaConfig = configManager.getConfig();
		ConfigurationSection section = arenaConfig.getConfigurationSection(name + ".spawns");
		List<Location> spawnList = Serializer.loadSpawnList(section, world);
		for (Location spawn : spawnList) {
			spawns.add(spawn);
			spawn.getBlock().getRelative(0, -1, 0).setType(Material.GLOWSTONE);
		}
		if (arenaConfig.isSet(name + ".pregame")) {
			pregame = Serializer.loadSpawn(arenaConfig.getConfigurationSection(name + ".pregame"), world);
		}
		if (arenaConfig.isSet(name + ".spectator")) {
			spectator = Serializer.loadSpawn(arenaConfig.getConfigurationSection(name + ".spectator"), world);
		}
		player.teleport(world.getSpawnLocation());
		player.saveInventory();
		player.clearInventory();
		player.getBukkitPlayer().getInventory().addItem(createMenuItem());
		player.setFlying(true);
		Bukkit.getPluginManager().registerEvents(listener, game);
		return this;
	}

	@Override
	public void close() {
		player.sendMessage(Settings.getSecondary() + "Exiting and saving data...");
		player.teleport(origin);
		player.restoreInventory();
		saveSpawns();
		game.getArenaManager().deleteWorld(world);
		HandlerList.unregisterAll(listener);
		game.getArenaManager().loadArenas();
	}

	@Override
	public void addSpawn() {
		Location spawn = getBlockLocation(player.getBukkitPlayer().getLocation());
		for (Location loc : spawns) {
			if (spawn.getBlock().equals(loc.getBlock())) {
				player.sendMessage(Settings.getError() + "There is already a spawn at that location!");
				return;
			}
		}
		spawns.add(spawn);
		spawn.getBlock().getRelative(0, -1, 0).setType(Material.GLOWSTONE);
		player.sendMessage(Settings.getSecondary() + "Added spawn!");
	}

	@Override
	public void removeSpawn() {
		Location spawn = getBlockLocation(player.getBukkitPlayer().getLocation());
		for (Location loc : spawns) {
			if (spawn.getBlock().equals(loc.getBlock())) {
				spawns.remove(spawn);
				spawn.getBlock().getRelative(0, -1, 0).setType(Material.DIRT);
				player.sendMessage(Settings.getSecondary() + "Removed spawn!");
				return;
			}
		}
		player.sendMessage(Settings.getError() + "There is no spawn at that location!");
	}

	@Override
	public void setPregame() {
		pregame = getBlockLocation(player.getBukkitPlayer().getLocation());
		player.sendMessage(Settings.getSecondary() + "Set pregame!");
	}

	@Override
	public void setSpectator() {
		spectator = getBlockLocation(player.getBukkitPlayer().getLocation());
		player.sendMessage(Settings.getSecondary() + "Set spectator spawn!!");
	}

	@Override
	public GamePlayer getPlayer() {
		return player;
	}

	@Override
	public Inventory getMenu() {
		return menu;
	}

	private Location getBlockLocation(Location location) {
		Location block = location.getBlock().getLocation();
		block.setPitch(location.getPitch());
		block.setYaw(location.getYaw());
		return block;
	}

	private void createMenu() {
		Inventory menu = Bukkit.createInventory(null, 9, "Arena Editor: " + name);

		ItemBuilder addSpawn = new ItemBuilder(Material.GLOWSTONE);
		addSpawn.setName(Settings.getPrimary() + "Add Spawn");
		addSpawn.setLore(Settings.getSecondary() + "Adds a new spawn at your current location,\n" +
				Settings.getSecondary() + "if one does not exist.");
		addSpawn.addNBTTag("addspawn");
		menu.setItem(0, addSpawn.getItem());

		ItemBuilder removeSpawn = new ItemBuilder(Material.DIRT);
		removeSpawn.setName(Settings.getPrimary() + "Remove Spawn");
		removeSpawn.setLore(Settings.getSecondary() + "Removes a spawn at your current location," +
				Settings.getSecondary() + "if one exists.");
		removeSpawn.addNBTTag("removespawn");
		menu.setItem(2, removeSpawn.getItem());

		ItemBuilder pregame = new ItemBuilder(Material.NETHER_STAR);
		pregame.setName(Settings.getPrimary() + "Set Pregame");
		pregame.setLore(Settings.getSecondary() + "Sets the pregame location.");
		pregame.addNBTTag("pregame");
		menu.setItem(4, pregame.getItem());

		ItemBuilder spectator = new ItemBuilder(Material.PLAYER_HEAD);
		spectator.setName(Settings.getPrimary() + "Set Spectator Spawn");
		spectator.setLore(Settings.getSecondary() + "Sets the spectator spawn location.");
		spectator.addNBTTag("spectator");
		menu.setItem(6, spectator.getItem());

		ItemBuilder close = new ItemBuilder(Material.BARRIER);
		close.setName(Settings.getPrimary() + "Close Editor");
		close.setLore(Settings.getSecondary() + "Closes the arena editor and\n" +
				Settings.getSecondary() + "saves all current spawns.");
		close.addNBTTag("close");
		menu.setItem(8, close.getItem());

		this.menu = menu;
	}

	private void saveSpawns() {
		ConfigManager configManager = new ConfigManager(game, "arenas.yml");
		FileConfiguration arenaConfig = configManager.getConfig();
		arenaConfig.createSection(name + ".spawns");
		ConfigurationSection section = arenaConfig.getConfigurationSection(name);
		Serializer.saveSpawnList(section, spawns);
		if (pregame != null) {
			Serializer.saveSpawn(arenaConfig, name + ".pregame", pregame);
		}
		if (spectator != null) {
			Serializer.saveSpawn(arenaConfig, name + ".spectator", spectator);
		}
		configManager.saveConfig();
		configManager.reloadConfig();
	}

	private ItemStack createMenuItem() {
		ItemBuilder menuBuilder = new ItemBuilder(Material.STICK);
		menuBuilder.setName(Settings.getPrimary() + "Open Menu");
		menuBuilder.addNBTTag("menu");
		return menuBuilder.getItem();
	}
}
