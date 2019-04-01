package net.nextinfinity.core.commands.executors;

import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.Settings;

public class Leave implements Executor {

	@Override
	public void executeCommand(GamePlayer player, String... args) {
		if (player.isInGame()) {
			Arena arena = player.getArena();
			if (player.isPlaying()) {
				arena.removePlayer(player);
			} else {
				arena.removeSpectator(player);
			}
		} else {
			player.sendMessage(Settings.getError() + "You are not in-game!");
		}
}
}
