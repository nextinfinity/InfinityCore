package net.nextinfinity.core.commands.executors;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.classes.impl.CoreClassEditor;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.ArgCombiner;
import net.nextinfinity.core.utils.Settings;

public class EditClass implements Executor {

	private final Game game;

	public EditClass(Game game) {
		this.game = game;
	}

	@Override
	public void executeCommand(GamePlayer player, String... args) {
		if (!player.getBukkitPlayer().hasPermission(Settings.getPermission() + ".admin")) {
			player.sendMessage(Settings.getError() + "You do not have permission to use that command!");
		} else if (args.length == 0) {
			player.sendMessage(Settings.getError() + "Specify the class name!");
		} else {
			player.sendMessage(Settings.getSecondary() + "Opening class editor...");
			String className = ArgCombiner.combineArgs(args, 0);
			new CoreClassEditor(game, player, className).open();
		}
	}

}

