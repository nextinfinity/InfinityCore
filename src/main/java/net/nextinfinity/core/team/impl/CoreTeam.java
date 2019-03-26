package net.nextinfinity.core.team.impl;

import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.team.Team;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CoreTeam implements Team {

	private String prefix = "";
	private String suffix = "";
	private int amount;
	private ChatColor color = ChatColor.WHITE;
	private final String name;
	private final List<GamePlayer> players = new ArrayList<>();
	private int score = 0;

	public CoreTeam(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public void setPrefix(String prefix) {
		this.prefix = prefix != null ? prefix : "";
	}

	@Override
	public String getSuffix() {
		return suffix;
	}

	@Override
	public void setSuffix(String suffix) {
		this.suffix = suffix != null ? suffix : "";
	}

	@Override
	public ChatColor getColor() {
		return color;
	}

	@Override
	public void setColor(ChatColor color) {
		this.color = color != null ? color : ChatColor.WHITE;
	}

	@Override
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public void addPlayer(GamePlayer player) {
		players.add(player);
	}

	@Override
	public void removePlayer(GamePlayer player) {
		players.remove(player);
	}

	@Override
	public List<GamePlayer> getPlayers() {
		return players;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void setScore(int score) {
		if (score > 0) {
			this.score = score;
		} else {
			this.score = 0;
		}
	}

	@Override
	public void incrementScore(int increment) {
		if (increment > 0) {
			score += increment;
		}
	}

	@Override
	public void decrementScore(int decrement) {
		if (decrement > 0) {
			if (score > decrement) {
				score -= decrement;
			} else {
				score = 0;
			}
		}
	}
}
