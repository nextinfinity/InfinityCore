package net.nextinfinity.core.commands.executors;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.ArgCombiner;
import net.nextinfinity.core.utils.Settings;

public class EditArena implements Executor {

	private final Game game;

	public EditArena(Game game) {
		this.game = game;
	}

	@Override
	public void executeCommand(GamePlayer player, String... args) {
		if (!player.getBukkitPlayer().hasPermission(Settings.getPermission() + ".admin")) {
			player.sendMessage(Settings.getError() + "You do not have permission to use that command!");
		} else if (args.length == 0) {
			player.sendMessage(Settings.getError() + "Specify an arena name!");
		} else {
			String arenaName = ArgCombiner.combineArgs(args, 0);
			player.sendMessage(Settings.getSecondary() + "Opening arena editor...");
			if (game.getArenaManager().createEditor(arenaName, player) == null) {
				player.sendMessage(Settings.getError() + "That arena does not exist");
			}
		}
	}

}
