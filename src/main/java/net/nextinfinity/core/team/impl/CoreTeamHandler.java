package net.nextinfinity.core.team.impl;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.team.Team;
import net.nextinfinity.core.team.TeamHandler;
import net.nextinfinity.core.utils.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class CoreTeamHandler implements TeamHandler {

	private final Map<Arena, List<Team>> teams = new HashMap<>();
	private final List<Team> teamData = new ArrayList<>();
	private final Game game;

	public CoreTeamHandler(Game game) {
		this.game = game;
	}


	@Override
	public void loadTeams() {
		ConfigManager configManager = new ConfigManager(game, "teams.yml");
		FileConfiguration teamConfig = configManager.getConfig();
		for (String teamName : teamConfig.getKeys(false)) {
			int amount = teamConfig.isSet(teamName + ".amount") ? teamConfig.getInt(teamName + ".amount") : 0;
			ChatColor color = teamConfig.isSet(teamName + ".color") ?
					ChatColor.getByChar(teamConfig.getString(teamName + ".color")) : ChatColor.WHITE;
			String prefix = teamConfig.isSet(teamName + ".prefix") ? teamConfig.getString(teamName + ".prefix") : "";
			String suffix = teamConfig.isSet(teamName + ".suffix") ? teamConfig.getString(teamName + ".suffix") : "";
			Team team = new CoreTeam(teamName.toLowerCase());
			team.setAmount(amount);
			team.setColor(color);
			team.setPrefix(prefix);
			team.setSuffix(suffix);
			teamData.add(team);
		}
	}

	@Override
	public void createTeams(Arena arena) {
		List<Team> arenaTeams = new ArrayList<>();
		if (teamData.size() > 0) {
			for (Team baseTeam : teamData) {
				Team team = new CoreTeam(baseTeam.getName());
				team.setAmount(baseTeam.getAmount());
				team.setColor(baseTeam.getColor());
				team.setPrefix(baseTeam.getPrefix());
				team.setSuffix(baseTeam.getSuffix());
				arenaTeams.add(team);
			}
		}
		teams.put(arena, arenaTeams);
	}

	@Override
	public List<Team> getTeams(Arena arena) {
		return teams.get(arena);
	}

	@Override
	public void removeArena(Arena arena) {
		teams.remove(arena);
	}

	@Override
	public void assignPlayers(Arena arena) {
		if (teamData.size() > 0) {
			Collections.shuffle(arena.getPlayers());
			int playerIndex = 0;
			int teamIndex = 0;
			while (playerIndex < arena.getPlayers().size()) {
				if (teamIndex > teams.get(arena).size()) {
					teamIndex = 0;
				}
				GamePlayer player = arena.getPlayers().get(playerIndex++);
				Team team = teams.get(arena).get(teamIndex++);
				if (player.getTeam() == null &&
					team.getPlayers().size() < team.getAmount()) {
					player.setTeam(team);
				}
			}
		} else {
			List<Team> arenaTeams = teams.get(arena);
			arena.getPlayers().forEach((player) -> {
				Team playerTeam = new CoreTeam(player.getBukkitPlayer().getDisplayName());
				player.setTeam(playerTeam);
				arenaTeams.add(playerTeam);
			});
			teams.put(arena, arenaTeams);
		}
	}

	@Override
	public Team getWinner(Arena arena) {
		Team winner = null;
		int score = 0;
		for (Team team : teams.get(arena)) {
			int teamScore = team.getScore();
			if (teamScore > score) {
				winner = team;
				score = teamScore;
			}
		}
		return winner;
	}
}
