package net.nextinfinity.core.commands.executors;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.player.GamePlayer;

public class Join implements Executor {

	private final Game game;

	public Join(Game game) {
		this.game = game;
	}

	@Override
	public void executeCommand(GamePlayer player, String... args) {
		game.getArenaManager().getMenu().open(player);
	}

}
