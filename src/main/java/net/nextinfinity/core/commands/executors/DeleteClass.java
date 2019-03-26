package net.nextinfinity.core.commands.executors;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.ArgCombiner;
import net.nextinfinity.core.utils.Settings;

public class DeleteClass implements Executor {

	private final Game game;

	public DeleteClass(Game game) {
		this.game = game;
	}

	@Override
	public void executeCommand(GamePlayer player, String... args) {
		if (!player.getBukkitPlayer().hasPermission("core.admin")) {
			player.sendMessage(Settings.getError() + "You do not have permission to use that command!");
		} else if (args.length == 0) {
			player.sendMessage(Settings.getError() + "Specify the class name!");
		} else {
			String className = ArgCombiner.combineArgs(args, 0);
			if (game.getClassManager().deleteClass(className)) {
				player.sendMessage(Settings.getSecondary() + "Deleted class!");
			} else {
				player.sendMessage(Settings.getError() + "That class does not exist!");
			}
		}
	}

}

