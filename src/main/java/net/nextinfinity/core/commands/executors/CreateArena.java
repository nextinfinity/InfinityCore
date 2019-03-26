package net.nextinfinity.core.commands.executors;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.ArgCombiner;
import net.nextinfinity.core.utils.Settings;

public class CreateArena implements Executor {

	private final Game game;

	public CreateArena(Game game) {
		this.game = game;
	}

	@Override
	public void executeCommand(GamePlayer player, String... args) {
		if (!player.getBukkitPlayer().hasPermission("core.admin")) {
			player.sendMessage(Settings.getError() + "You do not have permission to use that command!");
		} else if (args.length == 0) {
			player.sendMessage(Settings.getError() + "Specify an arena name!");
		} else {
			String name = ArgCombiner.combineArgs(args, 0);
			if (game.getArenaManager().createSchematic(player, name)) {
				player.sendMessage(Settings.getSecondary() + "Created arena!");
				game.getArenaManager().loadArenas();
			} else {
				player.sendMessage(Settings.getError() + "Unable to create arena! Check your selection.");
			}
		}
	}

}
