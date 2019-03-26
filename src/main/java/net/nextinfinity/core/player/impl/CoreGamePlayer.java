package net.nextinfinity.core.player.impl;

import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.Game;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.tasks.TeleportTask;
import net.nextinfinity.core.team.Team;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import net.nextinfinity.core.classes.GameClass;
import org.bukkit.util.Vector;

public class CoreGamePlayer implements GamePlayer {

	private final Game game;
	private GameClass gameClass;
	private final Player player;
	private Arena arena;
	private ItemStack[] inventory;
	private int score = 0;
	private Team team;

	public CoreGamePlayer(Player player, Game game) {
		this.player = player;
		this.game = game;
	}

	@Override
	public void setArena(Arena arena) {
		this.arena = arena;
	}

	@Override
	public Arena getArena() {
		return arena;
	}

	@Override
	public boolean isInGame() {
		return arena != null;
	}

	@Override
	public boolean isPlaying() {
		return arena != null && arena.getPlayers().contains(this);
	}

	@Override
	public boolean isSpectating() {
		return arena != null && arena.getSpectators().contains(this);
	}

	@Override
	public Player getBukkitPlayer() {
		return player;
	}

	@Override
	public void setClass(GameClass gameClass) {
		this.gameClass = gameClass;
	}

	@Override
	public GameClass getGameClass() {
		return gameClass;
	}

	@Override
	public void initializeClass() {
		saveInventory();
		player.getInventory().clear();
		if (gameClass == null) {
			gameClass = game.getClassManager().getRandomClass(this);
		}
		if (gameClass != null) {
			player.getInventory().setContents(gameClass.getItems());
			player.getInventory().setArmorContents(gameClass.getArmor());
			for (PotionEffect effect : gameClass.getEffects()) {
				player.addPotionEffect(effect, true);
			}
		}
		player.updateInventory();
	}

	@Override
	public void saveInventory() {
		this.inventory = player.getInventory().getContents();
	}

	@Override
	public void restoreInventory() {
		clearInventory();
		player.getInventory().setContents(inventory);
		player.updateInventory();
		inventory = null;
	}

	@Override
	public boolean hasClass(GameClass gameClass) {
		String perm = gameClass.getPermission();
		return perm == null || player.hasPermission(perm);
	}

	@Override
	public void sendMessage(String message) {
		player.sendMessage(Settings.getPrefix() + message);
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void setScore(int newScore) {
		score = newScore;
		if (arena != null) {
			arena.getScoreboard().refreshScores();
		}
	}

	@Override
	public void incrementScore(int toAdd) {
		score += toAdd;
	}

	@Override
	public void resetScore(){
		setScore(0);
	}

	@Override
	public Team getTeam() {
		return team;
	}

	@Override
	public void setTeam(Team team) {
		if (team != null) {
			team.addPlayer(this);
		}
		this.team = team;
	}

	@Override
	public void resetTeam(){
		if (team != null) {
			team.removePlayer(this);
			this.team = null;
		}
	}

	@Override
	public void teleport(Location location) {
		player.setVelocity(new Vector(0, 0, 0));
		player.setFallDistance(0);
		new TeleportTask(player, location).runTaskLater(game, 1);
	}

	@Override
	public void heal() {
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setFoodLevel(20);
		player.setTotalExperience(0);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	@Override
	public void clearInventory() {
		player.getInventory().setArmorContents(new ItemStack[4]);
		player.getInventory().clear();
	}

	@Override
	public void setFlying(boolean flying) {
		if (flying && !player.getAllowFlight()) {
			player.setAllowFlight(true);
		}
		player.setFlying(flying);
	}
}
