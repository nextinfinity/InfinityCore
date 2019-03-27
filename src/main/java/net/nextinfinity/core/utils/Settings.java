package net.nextinfinity.core.utils;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;

/**
 * The Settings class contains static values often accessed during runtime.
 * The set methods should usually not be used except during game initialization.
 */
public class Settings {

	private static int max;
	private static int min;
	private static ChatColor primary;
	private static ChatColor secondary;
	private static ChatColor error;
	private static String name;
	private static String prefix;
	private static String permission;
	private static Location lobby;
	private static boolean blockBreak;
	private static boolean itemManage;
	private static boolean hunger;
	private static boolean fireBlockDamage;
	private static boolean explosionBlockDamage;
	private static GameMode gameMode;
	private static GameMode exitMode;
	private static int gameTime;
	private static int countdownTime;
	private static boolean joinTeleport;
	private static int winningEco;

	/**
	 * Gets the maximum amount of players for an arena.
	 *
	 * @return the maximum amount of players
	 */
	public static int getMax() {
		return max;
	}

	/**
	 * Sets the maxmimum amount of players for an arena.
	 *
	 * @param max the new maximum amount
	 */
	public static void setMax(int max) {
		Settings.max = max;
	}

	/**
	 * Gets the minimum amount of players for an arena.
	 *
	 * @return the minimum amount of players
	 */
	public static int getMin() {
		return min;
	}

	/**
	 * Sets the minimum amount of players for an arena.
	 *
	 * @param min the new minimum amount
	 */
	public static void setMin(int min) {
		Settings.min = min;
	}

	/**
	 * Gets the name of the game.
	 *
	 * @return the name of the game
	 */
	public static String getName() {
		return name;
	}

	/**
	 * Sets the name of the game.
	 *
	 * @param name the new name of the game
	 */
	public static void setName(String name) {
		Settings.name = name;
	}

	/**
	 * Gets the prefix used for chat messages.
	 *
	 * @return the game's chat prefix
	 */
	public static String getPrefix() {
		return prefix;
	}

	/**
	 * Sets the prefix used for chat messages.
	 *
	 * @param prefix the game's new chat prefix
	 */
	public static void setPrefix(String prefix) {
		Settings.prefix = prefix;
	}

	/**
	 * Gets the primary color used for chat messages and UI elements.
	 *
	 * @return the primary ChatColor
	 */
	public static ChatColor getPrimary() {
		return primary;
	}

	/**
	 * Sets the primary color used for chat messages and UI elements.
	 *
	 * @param primary the new primary ChatColor
	 */
	public static void setPrimary(ChatColor primary) {
		Settings.primary = primary;
	}

	/**
	 * Gets the secondary color used for chat messages and UI elements.
	 *
	 * @return the secondary ChatColor
	 */
	public static ChatColor getSecondary() {
		return secondary;
	}

	/**
	 * Sets the secondary color used for chat messages and UI elements.
	 *
	 * @param secondary the new secondary ChatColor
	 */
	public static void setSecondary(ChatColor secondary) {
		Settings.secondary = secondary;
	}

	/**
	 * Gets the error color used for chat messages and UI elements.
	 *
	 * @return the error ChatColor
	 */
	public static ChatColor getError() {
		return error;
	}

	/**
	 * Sets the error color used for chat messages and UI elements.
	 *
	 * @param error the new error ChatColor
	 */
	public static void setError(ChatColor error) {
		Settings.error = error;
	}

	/**
	 * Gets the lobby joining players are teleported to.
	 *
	 * @return the global lobby location
	 */
	public static Location getLobby() {
		return lobby;
	}

	/**
	 * Sets the lobby joining players are teleported to.
	 *
	 * @param lobby the new global lobby location
	 */
	public static void setLobby(Location lobby) {
		Settings.lobby = lobby;
	}

	/**
	 * Gets whether or not to allow block placing and breaking in Arenas.
	 *
	 * @return whether or not to allow block breakage
	 */
	public static boolean getBlockBreak() {
		return blockBreak;
	}

	/**
	 * Sets whether or not to allow block placing and breaking in Arenas.
	 *
	 * @param blockBreak whether or not to allow block breakage
	 */
	public static void setBlockBreak(boolean blockBreak) {
		Settings.blockBreak = blockBreak;
	}

	/**
	 * Gets whether or not to allow a player to manage their inventory in Arenas.
	 *
	 * @return whether or not to allow inventory management
	 */
	public static boolean getItemManage() {
		return itemManage;
	}

	/**
	 * Sets whether or not to allow a player to manage their inventory in Arenas.
	 *
	 * @param itemManage whether or not to allow inventory management
	 */
	public static void setItemManage(boolean itemManage) {
		Settings.itemManage = itemManage;
	}

	/**
	 * Gets whether or not to allow player hunger to reduce in Arenas.
	 *
	 * @return whether or not to allow hunger
	 */
	public static boolean getHunger() {
		return hunger;
	}

	/**
	 * Sets whether or not to allow player hunger to reduce in Arenas.
	 *
	 * @param hunger whether or not to allow hunger
	 */
	public static void setHunger(boolean hunger) {
		Settings.hunger = hunger;
	}

	/**
	 * Gets whether or not to allow blocks to burn in Arenas.
	 *
	 * @return whether or not to allow blocks to burn
	 */
	public static boolean getFireBlockDamage() {
		return fireBlockDamage;
	}

	/**
	 * Sets whether or not to allow blocks to burn in Arenas.
	 *
	 * @param fireBlockDamage whether or not to allow blocks to burn
	 */
	public static void setFireBlockDamage(boolean fireBlockDamage) {
		Settings.fireBlockDamage = fireBlockDamage;
	}

	/**
	 * Gets whether or not to allow explosions to break blocks in Arenas.
	 *
	 * @return whether or not to allow explosion block damage
	 */
	public static boolean getExplosionBlockDamage() {
		return explosionBlockDamage;
	}

	/**
	 * Sets whether or not to allow explosions to break blocks in Arenas.
	 *
	 * @param explosionBlockDamage whether or not to allow explosion block damage
	 */
	public static void setExplosionBlockDamage(boolean explosionBlockDamage) {
		Settings.explosionBlockDamage = explosionBlockDamage;
	}

	/**
	 * Gets the GameMode to set players to when they are in an Arena.
	 *
	 * @return the GameMode for Arenas
	 */
	public static GameMode getGameMode() {
		return gameMode;
	}

	/**
	 * Sets the GameMode to set players to when they are in an Arena.
	 *
	 * @param gameMode the new GameMode for Arenas
	 */
	public static void setGameMode(GameMode gameMode) {
		Settings.gameMode = gameMode;
	}

	/**
	 * Gets the GameMode to return players to outside of an Arena.
	 *
	 * @return the GameMode outside of Arenas
	 */
	public static GameMode getExitMode() {
		return exitMode;
	}

	/**
	 * Sets the GameMode to return players to outside of an Arena.
	 *
	 * @param exitMode the new GameMode outside of Arenas
	 */
	public static void setExitMode(GameMode exitMode) {
		Settings.exitMode = exitMode;
	}

	/**
	 * Gets how long a game should last for, in seconds.
	 *
	 * @return the length of a game in seconds
	 */
	public static int getGameTime() {
		return gameTime;
	}

	/**
	 * Sets how long a game should last for, in seconds.
	 *
	 * @param gameTime the new length of a game in seconds
	 */
	public static void setGameTime(int gameTime) {
		Settings.gameTime = gameTime;
	}

	/**
	 * Gets how long the countdown time should last for, in seconds.
	 *
	 * @return the countdown time in seconds
	 */
	public static int getCountdownTime() {
		return countdownTime;
	}

	/**
	 * Sets how long the countdown time should last for, in seconds.
	 *
	 * @param countdownTime the new countdown time in seconds
	 */
	public static void setCountdownTime(int countdownTime) {
		Settings.countdownTime = countdownTime;
	}

	/**
	 * Gets whether or not to teleport players to the global lobby when they join the server.
	 *
	 * @return whether or not to teleport players on join
	 */
	public static boolean getJoinTeleport() {
		return joinTeleport;
	}

	/**
	 * Sets whether or not to teleport players to the global lobby when they join the server.
	 *
	 * @param joinTeleport whether or not to teleport players on join
	 */
	public static void setJoinTeleport(boolean joinTeleport) {
		Settings.joinTeleport = joinTeleport;
	}

	/**
	 * Gets the amount to reward players when they win a game, using Vault.
	 *
	 * @return the amount to give winners
	 */
	public static int getWinningEco() {
		return winningEco;
	}

	/**
	 * Sets the amount to reward players when they win a game, using Vault.
	 *
	 * @param winningEco the new amount to give winners
	 */
	public static void setWinningEco(int winningEco) {
		Settings.winningEco = winningEco;
	}

	/**
	 * Gets the permission prefix for the game.
	 *
	 * @return the permission prefix
	 */
	public static String getPermission() {
		return permission;
	}

	/**
	 * Sets the permission prefix for the game.
	 *
	 * @param permission the new permission prefix
	 */
	public static void setPermission(String permission) {
		Settings.permission = permission;
	}
}
