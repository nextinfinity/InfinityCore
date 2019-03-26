package net.nextinfinity.core;

import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.arena.ArenaManager;
import net.nextinfinity.core.arena.impl.CoreArenaManager;
import net.nextinfinity.core.arena.listeners.MenuListener;
import net.nextinfinity.core.arena.listeners.SignListener;
import net.nextinfinity.core.classes.ClassManager;
import net.nextinfinity.core.classes.impl.CoreClassManager;
import net.nextinfinity.core.classes.listeners.ClassListener;
import net.nextinfinity.core.commands.CommandHandler;
import net.nextinfinity.core.commands.impl.CoreCommandHandler;
import net.nextinfinity.core.events.GameDeathEvent;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.player.PlayerHandler;
import net.nextinfinity.core.player.impl.CorePlayerHandler;
import net.nextinfinity.core.player.listeners.DeathListener;
import net.nextinfinity.core.player.listeners.JoinListener;
import net.nextinfinity.core.player.listeners.LeaveListener;
import net.nextinfinity.core.player.listeners.PlayerListener;
import net.nextinfinity.core.team.TeamHandler;
import net.nextinfinity.core.team.impl.CoreTeamHandler;
import net.nextinfinity.core.utils.ConfigManager;
import net.nextinfinity.core.utils.Serializer;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * The Game class forms the base for games using InfinityCore. The main plugin class of games should extend Game (this class) in lieu of JavaPlugin.
 * <br>All game-specific initialization should be done in {@link #setup()}, as other methods should not be overridden.
 * <br>Individual games must contain:
 * <br>- all game-specific mechanics
 * <br>- death management, respawning or removing dead players ({@link GameDeathEvent})
 * <br>- scoring - the methods are provided but it is up to the game to actually add points
 * <br>In general the game should interact with InfinityCore using {@link net.nextinfinity.core.events} where possible. Only the {@link Arena} and {@link GamePlayer} implementations are intended to be modified.
 */
public abstract class Game extends JavaPlugin {

	private ArenaManager arenaManager;
	private PlayerHandler playerHandler;
	private ClassManager classManager;
	private CommandHandler commandHandler;
	private TeamHandler teamHandler;

	/**
	 * Creates the ArenaManager, PlayerHandler, and ClassManager objects, and calls other setup functions.
	 */
	@Override
	public void onEnable() {
		saveDefaults();
		loadConfig();
		loadCommands();
		loadCoreListeners();
		arenaManager = new CoreArenaManager(this);
		arenaManager.clearArenas();
		arenaManager.loadArenas();
		playerHandler = new CorePlayerHandler(this);
		classManager = new CoreClassManager(this);
		classManager.loadClasses();
		teamHandler = new CoreTeamHandler(this);
		teamHandler.loadTeams();
		setup();
	}

	/**
	 * Removes all active arenas on shutdown.
	 */
	@Override
	public void onDisable() {
		arenaManager.getArenaMap().values().stream().flatMap(List::stream).forEach(arenaManager::removeArena);
	}

	/**
	 * Games should implement this method to register their own listeners or perform other necessary initialization actions.
	 */
	public abstract void setup();

	/**
	 * Loads values for Settings from the config.yml file.
	 */
	private void loadConfig() {
		FileConfiguration config = getConfig();
		Settings.setMin(config.getInt("size.min"));
		Settings.setMax(config.getInt("size.max"));
		Settings.setName(config.getString("text.name"));
		Settings.setPrefix(ChatColor.translateAlternateColorCodes('&', config.getString("text.prefix"))
				.replace("{NAME}", Settings.getName()));
		Settings.setPrimary(ChatColor.getByChar(config.getString("text.primary-color")));
		Settings.setSecondary(ChatColor.getByChar(config.getString("text.secondary-color")));
		Settings.setError(ChatColor.getByChar(config.getString("text.error-color")));
		Settings.setBlockBreak(config.getBoolean("arena.block-break"));
		Settings.setItemManage(config.getBoolean("arena.item-manage"));
		Settings.setHunger(config.getBoolean("arena.hunger"));
		Settings.setFireBlockDamage(config.getBoolean("arena.fire-block-damage"));
		Settings.setExplosionBlockDamage(config.getBoolean("arena.explosion-block-damage"));
		Settings.setGameMode(GameMode.valueOf(config.getString("modes.game-mode")));
		Settings.setExitMode(GameMode.valueOf(config.getString("modes.exit-mode")));
		Settings.setGameTime(config.getInt("time.game-time"));
		Settings.setCountdownTime(config.getInt("time.countdown-time"));
		Settings.setLobby(Serializer.loadLocation(config.getConfigurationSection("lobby")));
		Settings.setJoinTeleport(config.getBoolean("other.join-teleport"));
		Settings.setWinningEco(config.getInt("eco.winning-eco"));
	}

	/**
	 * Sets default values in the config.yml file, if they are not already set.
	 */
	private void saveDefaults() {
		new ConfigManager(this, "arenas.yml").saveDefaultConfig();
		new ConfigManager(this, "classes.yml").saveDefaultConfig();
		new ConfigManager(this, "config.yml").saveDefaultConfig();
		new ConfigManager(this, "scoreboard.yml").saveDefaultConfig();

		FileConfiguration config = getConfig();
		if (!config.contains("lobby")) {
			Location defaultSpawn = Bukkit.getWorld("world").getSpawnLocation();
			Serializer.saveLocation(config, "lobby", defaultSpawn);
		}
		saveConfig();
		reloadConfig();
	}

	/**
	 * Creates a new {@link CommandHandler} object and registers the commands for CTCore.
	 */
	private void loadCommands() {
		commandHandler = new CoreCommandHandler(this);
		commandHandler.registerCommands();
	}

	/**
	 * Sets the primary command for the game.
	 *
	 * @param command the name of the command
	 */
	public void setCommand(String command) {
		getCommand(command).setExecutor(commandHandler);
	}

	/**
	 * Registers the listeners for CTCore.
	 */
	private void loadCoreListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new ClassListener(this), this);
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new DeathListener(this), this);
		pm.registerEvents(new JoinListener(this), this);
		pm.registerEvents(new LeaveListener(this), this);
		pm.registerEvents(new MenuListener(this), this);
		pm.registerEvents(new SignListener(this), this);
	}

	/**
	 * Gets the ArenaManager for the game.
	 *
	 * @return the ArenaManager object
	 */
	public ArenaManager getArenaManager() {
		return arenaManager;
	}

	/**
	 * Gets the ClassManager for the game.
	 *
	 * @return the ClassManager object
	 */
	public ClassManager getClassManager() {
		return classManager;
	}

	/**
	 * Gets the PlayerHandler for the game.
	 *
	 * @return the PlayerHandler object
	 */
	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

	/**
	 * Gets the CommandHandler for the game.
	 *
	 * @return the CommandHandler object
	 */
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

	/**
	 * Gets the TeamHandler for the game.
	 *
	 * @return the TeamHandler object
	 */
	public TeamHandler getTeamHandler() {
		return teamHandler;
	}

}
