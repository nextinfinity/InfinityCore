package net.nextinfinity.core.arena.impl;

import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.boydti.fawe.object.FawePlayer;
import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.*;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.ConfigManager;
import net.nextinfinity.core.utils.FileUtil;
import net.nextinfinity.core.utils.Serializer;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class CoreArenaManager implements ArenaManager {

	private final Game game;
	private final ConfigManager configManager;
	private final ArenaMenu menu;
	private final Map<String, Schematic> schematics = new HashMap<>();
	private final Map<String, List<Arena>> arenas = new HashMap<>();
	private int count = 0;
	private Class<? extends Arena> arenaClass = CoreArena.class;

	public CoreArenaManager(Game game) {
		this.game = game;
		configManager = new ConfigManager(game, "arenas.yml");
		menu = new CoreArenaMenu(game, this);
	}

	@Override
	public Arena getArena(String name) {
		name = name.toLowerCase();
		if (arenas.get(name) == null) return null;

		for (Arena arena : arenas.get(name)) {
			if ((arena.getState() == GameState.LOBBY || arena.getState() == GameState.STARTING) &&
					arena.getPlayers().size() < Settings.getMax()) {
				return arena;
			}
		}

		return createArena(name);
	}

	@Override
	public void removeArena(Arena arena) {
		List<Arena> arenaList = arenas.get(arena.getName());
		arenaList.remove(arena);
		arenas.put(arena.getName(), arenaList);
		menu.update();
		game.getTeamHandler().removeArena(arena);
		deleteWorld(arena.getWorld());
	}

	@Override
	public void loadArenas() {
		arenas.clear();
		schematics.clear();
		File folder = new File(game.getDataFolder().getAbsolutePath() + File.separator + "schematics");
		FileConfiguration arenaConfig = new ConfigManager(game, "arenas.yml").getConfig();
		if (!folder.exists()) {
			folder.mkdir();
		}
		for (File schem : folder.listFiles()) {
			String internalName = schem.getName().toLowerCase().split("\\.")[0];
			try {
				schematics.put(internalName, ClipboardFormats.findByFile(schem).load(schem));
			} catch (Exception e) {
				Bukkit.getLogger().log(Level.WARNING, "Unable to load schematic " + schem.getName() +
						"If this is a valid schematic, please report this issue on Spigot.");
				e.printStackTrace();
			}
			if (arenaConfig.contains(internalName)) {
				arenas.put(internalName, new ArrayList<>());
			}
		}
		menu.reset();
	}

	@Override
	public void clearArenas() {
		for (File dir : game.getServer().getWorldContainer().listFiles()) {
			if (dir.isDirectory() && dir.getName().startsWith("arena")) {
				FileUtil.deleteFolder(dir);
			}
		}
	}

	@Override
	public ArenaEditor createEditor(String name, GamePlayer player) {
		name = name.toLowerCase();
		World world = createWorld(name);
		if (world == null) {
			return null;
		}
		return new CoreArenaEditor(game, world, name, player).open();
	}

	@Override
	public ArenaMenu getMenu() {
		return menu;
	}

	@Override
	public boolean createSchematic(GamePlayer player, String name) {
		File file = new File(game.getDataFolder().getAbsolutePath() + File.separator + "schematics" +
				File.separator + name + ".nbt");
		if (file.exists()) {
			return false;
		}
		FawePlayer fawePlayer = FawePlayer.wrap(player.getBukkitPlayer());
		Region selection = fawePlayer.getSelection();
		if (selection == null || selection.getMinimumPoint() == null || selection.getMaximumPoint() == null) {
			return false;
		}
		Schematic schem = new Schematic(selection);
		try {
			schem.save(file, BuiltInClipboardFormat.STRUCTURE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void setLobby(Location location) {
		ConfigManager configManager = new ConfigManager(game, "config.yml");
		Serializer.saveLocation(configManager.getConfig(), "lobby", location);
		configManager.saveConfig();
		configManager.reloadConfig();
		Settings.setLobby(location);
	}

	@Override
	public Map<String, List<Arena>> getArenaMap() {
		return arenas;
	}

	@Override
	public void setArenaClass(Class<? extends Arena> newClass) {
		this.arenaClass = newClass;
	}

	@Override
	public boolean isArenaWorld(World world) {
		for (List<Arena> arenaList : arenas.values()) {
			for (Arena arena : arenaList) {
				if (arena.getWorld().equals(world)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void deleteWorld(World world) {
		File folder = world.getWorldFolder();
		Bukkit.unloadWorld(world, true);
		FileUtil.deleteFolder(folder);
	}

	private Arena createArena(String name) {
		if (!arenas.containsKey(name)) return null;
		World world = createWorld(name);
		if (world == null) {
			return null;
		}
		Arena arena = null;
		try {
			arena = arenaClass.getConstructor(Game.class, String.class, World.class).newInstance(game, name, world);
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.SEVERE, "Arena constructor should remain the same!");
			Bukkit.getLogger().log(Level.SEVERE, "Problem in: " + arenaClass.getCanonicalName());
			e.printStackTrace();
		}
		FileConfiguration config = configManager.getConfig();
		if (config.contains(name + ".spawns")) {
			ConfigurationSection spawnConfig = config.getConfigurationSection(name + ".spawns");
			List<Location> spawns = Serializer.loadSpawnList(spawnConfig, world);
			spawns.forEach(arena::addSpawn);
		}
		if (config.isSet(name + ".pregame")) {
			arena.setPregame(Serializer.loadSpawn(config.getConfigurationSection(name + ".pregame"), world));
		}
		if (config.isSet(name + ".spectator")) {
			arena.setSpectatorSpawn(Serializer.loadSpawn(config.getConfigurationSection(name + ".spectator"), world));
		}
		List<Arena> arenaList = arenas.get(name);
		arenaList.add(arena);
		arenas.put(name, arenaList);
		menu.update();
		return arena;
	}

	private World createWorld(String name) {
		//It seems 1.13 broke the generatorSettings method and I couldn't find details of the new implementation,
		//so this generator workaround is courtesy of MiniDigger on spigotmc.org
		WorldCreator creator = WorldCreator.name("arena-" + count++).generator(new ArenaChunkGenerator());
		World world = AsyncWorld.create(creator).getBukkitWorld();
		world.setSpawnFlags(false, false);
		world.setSpawnLocation(0, 200, 0);
		world.setKeepSpawnInMemory(false);
		world.setAutoSave(false);
		try {
			schematics.get(name).paste(new BukkitWorld(world), BlockVector3.at(0, 100, 0), false, false, null);
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.SEVERE, "Error pasting schematic for arena " + name.toUpperCase() + "!" +
					"If this is a valid schematic, please report this issue on Spigot.");
			e.printStackTrace();
			return null;
		}
		return world;
	}

}
