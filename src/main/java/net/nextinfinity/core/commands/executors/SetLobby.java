package net.nextinfinity.core.commands.executors;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.Settings;

public class SetLobby implements Executor {

	private final Game game;

	public SetLobby(Game game) {
		this.game = game;
	}

	@Override
	public void executeCommand(GamePlayer player, String... args) {
		if (player.getBukkitPlayer().hasPermission("core.admin")) {
			game.getArenaManager().setLobby(player.getBukkitPlayer().getLocation());
			player.sendMessage(Settings.getSecondary() + "Set lobby!");
		} else {
			player.sendMessage(Settings.getError() + "You do not have permission to use that command!");
		}
	}

}
