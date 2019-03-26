package net.nextinfinity.core.arena.impl;

import net.milkbowl.vault.economy.Economy;
import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.arena.GameState;
import net.nextinfinity.core.arena.ScoreboardManager;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.events.*;
import net.nextinfinity.core.tasks.TimerTask;
import net.nextinfinity.core.team.Team;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class CoreArena implements Arena {

	private GameState state = GameState.LOBBY;
	private List<GamePlayer> players = new ArrayList<>();
	private List<GamePlayer> spectators = new ArrayList<>();
	private HashMap<Team, List<Location>> spawns = new HashMap<>();
	private HashMap<Team, List<Location>> usableSpawns = new HashMap<>();
	private Location pregame;
	private Location spectatorSpawn;
	private final String name;
	private final Game game;
	private final ScoreboardManager scoreboard;
	private final World world;
	private BukkitTask task;

	public CoreArena(Game game, String name, World world) {
		this.game = game;
		this.name = name;
		this.world = world;
		scoreboard = new CoreScoreboardManager(this, game);
		game.getTeamHandler().createTeams(this);
	}

	@Override
	public boolean addPlayer(GamePlayer player) {
		if (state != GameState.LOBBY && state != GameState.STARTING) {
			player.sendMessage("That arena is already in-game!");
			return false;
		}
		if (players.size() >= Settings.getMax()) {
			player.sendMessage("That arena is full!");
			return false;
		}
		if (player.isInGame()) {
			if (player.isPlaying()) {
				player.sendMessage("You are already in an arena!");
			} else {
				player.sendMessage("You cannot join a game while spectating!");
			}
			return false;
		}
		player.sendMessage(Settings.getSecondary() + "Welcome! Choose a class and get ready to play.");
		player.saveInventory();
		if (pregame != null) {
			player.teleport(pregame);
		}
		player.getBukkitPlayer().setGameMode(Settings.getGameMode());
		player.heal();
		player.clearInventory();
		player.getBukkitPlayer().getInventory().setItem(0, game.getClassManager().getClassSelector());
		Bukkit.getScheduler().runTaskLater(game, () -> game.getClassManager().openGUI(player), 1L);
		player.resetScore();
		player.setArena(this);
		players.add(player);
		Bukkit.getServer().getPluginManager().callEvent(new GameJoinEvent(game, this, player));

		if (players.size() >= Settings.getMin() && state != GameState.STARTING) {
			countdown();
		}

		scoreboard.setBoard(player);

		game.getArenaManager().getMenu().update();
		return true;
	}

	@Override
	public void removePlayer(GamePlayer player) {
		if (!this.players.contains(player)) return;

		player.restoreInventory();
		player.teleport(Settings.getLobby());

		player.setArena(null);
		player.resetScore();
		player.resetTeam();
		player.heal();
		player.getBukkitPlayer().setFoodLevel(20);
		player.getBukkitPlayer().setTotalExperience(0);
		player.getBukkitPlayer().setGameMode(Settings.getExitMode());

		Bukkit.getServer().getPluginManager().callEvent(new GameLeaveEvent(game, this, player));

		scoreboard.clearBoard(player);

		players.remove(player);

		if (state == GameState.FINISHED) {
			return;
		}

		if (players.size() < Settings.getMin() && state == GameState.STARTING) {
			task.cancel();
			state = GameState.LOBBY;
		}

		if (players.isEmpty()) {
			end();
		} else if (players.size() == 1 && state == GameState.INGAME) {
			end();
		}

		game.getArenaManager().getMenu().update();
	}

	@Override
	public boolean addSpectator(GamePlayer player) {
		if (state != GameState.INGAME) {
			player.sendMessage("That arena is not running!");
			return false;
		}
		if (player.isInGame()) {
			if (player.isPlaying()) {
				player.sendMessage("You cannot spectate while playing!");
			} else {
				player.sendMessage("You must stop spectating your current arena first!");
			}
			return false;
		}
		player.saveInventory();
		if (spectatorSpawn != null) {
			player.teleport(spectatorSpawn);
		}
		player.getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
		player.clearInventory();
		player.setArena(this);
		spectators.add(player);

		scoreboard.setBoard(player);
		return true;
	}

	@Override
	public void removeSpectator(GamePlayer player) {
		if (!this.spectators.contains(player)) return;

		player.restoreInventory();
		player.teleport(Settings.getLobby());

		player.setArena(null);
		player.getBukkitPlayer().setGameMode(Settings.getExitMode());

		spectators.remove(player);
		scoreboard.clearBoard(player);
	}

	@Override
	public void spawnPlayer(GamePlayer player) {
		player.teleport(getSpawn(player.getTeam()));
	}

	@Override
	public void countdown() {
		task = new TimerTask(game, this, Settings.getCountdownTime(), "Starting in {TIME}!")
				.runTaskTimer(game, 0, 20);
		state = GameState.STARTING;
		Bukkit.getServer().getPluginManager().callEvent(new GameCountdownEvent(game, this));
	}

	@Override
	public void start() {
		task = new TimerTask(game, this, Settings.getGameTime(), "{TIME} remaining!")
				.runTaskTimer(game, 0, 20);
		state = GameState.INGAME;
		game.getTeamHandler().assignPlayers(this);
		for (GamePlayer player : players) {
			player.heal();
			spawnPlayer(player);
			player.initializeClass();
		}
		Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent(game, this));
	}

	@Override
	public void end() {
		if (state == GameState.FINISHED) {
			return;
		}
		state = GameState.FINISHED;
		if (task != null) {
			task.cancel();
		}
		scoreboard.refreshTime(0, 0);
		Team winner = game.getTeamHandler().getWinner(this);
		Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(game, this, winner));
		if (winner != null) {
			String message = winner.getColor() + winner.getName().toUpperCase() + Settings.getSecondary() + " won!";
			players.forEach((player) -> player.sendMessage(message));
			spectators.forEach((spec) -> spec.sendMessage(message));
			if(Bukkit.getPluginManager().getPlugin("Vault") != null){
				RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
				if (economyProvider != null) {
					for (GamePlayer player : winner.getPlayers()) {
						economyProvider.getProvider().depositPlayer(player.getBukkitPlayer(), Settings.getWinningEco());
					}
				}
			}
		}
		Bukkit.getScheduler().runTaskLater(game, () -> {
			new ArrayList<>(players).forEach(this::removePlayer);
			new ArrayList<>(spectators).forEach(this::removeSpectator);
		}, 80);
		Bukkit.getScheduler().runTaskLater(game, () -> {
			game.getArenaManager().removeArena(this);
			game.getArenaManager().getMenu().update();
		}, 90);
	}

	@Override
	public List<GamePlayer> getPlayers() {
		return players;
	}

	@Override
	public List<GamePlayer> getSpectators() {
		return spectators;
	}

	@Override
	public GameState getState() {
		return state;
	}

	@Override
	public ScoreboardManager getScoreboard() {
		return scoreboard;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addSpawn(Location spawn) {
		addSpawn(spawn, null);
	}

	@Override
	public void addSpawn(Location spawn, Team team) {
		if (!spawns.containsKey(team)) spawns.put(team, new ArrayList<>());
		List<Location> spawnList = spawns.get(team);
		spawnList.add(spawn);
		spawns.put(team, spawnList);
	}

	@Override
	public void setPregame(Location pregame) {
		this.pregame = pregame;
	}

	@Override
	public Location getPregame() {
		return pregame;
	}

	@Override
	public void setSpectatorSpawn(Location spectatorSpawn) {
		this.spectatorSpawn = spectatorSpawn;
	}

	@Override
	public World getWorld() {
		return world;
	}

	private Location getSpawn(Team team) {
		if (!spawns.containsKey(team)) {
			team = null;
		}
		if (!usableSpawns.containsKey(team) || usableSpawns.get(team).isEmpty()) {
			usableSpawns.put(team, new ArrayList<>(spawns.get(team)));
		}
		List<Location> teamSpawns = usableSpawns.get(team);
		Location spawn = teamSpawns.remove(new Random().nextInt(teamSpawns.size()));
		usableSpawns.put(team, teamSpawns);
		return spawn;
	}

}
