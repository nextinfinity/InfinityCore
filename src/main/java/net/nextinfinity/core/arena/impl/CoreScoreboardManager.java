package net.nextinfinity.core.arena.impl;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.arena.GameState;
import net.nextinfinity.core.arena.ScoreboardManager;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.team.Team;
import net.nextinfinity.core.utils.Settings;
import net.nextinfinity.core.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

public class CoreScoreboardManager implements ScoreboardManager {

	private final Arena arena;
	private final Game game;
	private final Scoreboard board;
	private final BossBar bossBar;
	private static final Scoreboard blankBoard = Bukkit.getScoreboardManager().getNewScoreboard();

	public CoreScoreboardManager(Arena arena, Game game) {
		this.arena = arena;
		this.game = game;
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		board.registerNewObjective("title", "dummy", Settings.getName()).setDisplaySlot(DisplaySlot.SIDEBAR);
		board.getObjective(DisplaySlot.SIDEBAR).setDisplayName(Settings.getPrimary() + Settings.getName());
		bossBar = Bukkit.createBossBar(Settings.getSecondary() + "WAITING FOR PLAYERS", BarColor.WHITE, BarStyle.SOLID);
	}

	@Override
	public void refreshTime(int time, int initialTime) {
		String timeString = null;
		switch (arena.getState()) {
			case STARTING:
				timeString = "Countdown: " + TimeUtil.getTimeAsString(time);
				bossBar.setProgress((float) time / initialTime);
				break;
			case INGAME:
				timeString = "Time Left: " + TimeUtil.getTimeAsString(time);
				bossBar.setProgress((float) time / initialTime);
				break;
			case FINISHED:
				Team winner = game.getTeamHandler().getWinner(arena);
				if (winner != null) {
					timeString = winner.getColor() + winner.getName() + Settings.getSecondary() + " won!";
				} else {
					timeString = "Game over!";
				}
				bossBar.setProgress(1);
				break;
		}
		bossBar.setTitle(Settings.getSecondary() + timeString);
	}

	@Override
	public void refreshScores() {
		if (arena.getState() == GameState.INGAME) {
			for (Team team : game.getTeamHandler().getTeams(arena)) {
				if (board.getTeam(team.getName()) == null) {
					board.registerNewTeam(team.getName());
					board.getTeam(team.getName()).addEntry(team.getName());
				}
				board.getTeam(team.getName()).setColor(team.getColor());
				board.getTeam(team.getName()).setPrefix(team.getPrefix());
				board.getTeam(team.getName()).setSuffix(team.getSuffix());
				board.getObjective(DisplaySlot.SIDEBAR).getScore(team.getName()).setScore(team.getScore());
			}
		}
	}

	@Override
	public void setBoard(GamePlayer player) {
		player.getBukkitPlayer().setScoreboard(board);
		bossBar.addPlayer(player.getBukkitPlayer());
	}

	@Override
	public void clearBoard(GamePlayer player) {
		player.getBukkitPlayer().setScoreboard(blankBoard);
		bossBar.removePlayer(player.getBukkitPlayer());
	}

}
