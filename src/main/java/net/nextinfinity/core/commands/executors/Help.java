package net.nextinfinity.core.commands.executors;

import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.Settings;

public class Help implements Executor {

	@Override
	public void executeCommand(GamePlayer player, String... args) {
		String help = Settings.getPrimary() +
				"Help:\n" +
				Settings.getSecondary() +
				"/scb join: Open the join menu\n" +
				"/scb leave: Leave an arena\n" +
				"/scb start: Start an arena early\n";
		player.sendMessage(help);
	}

}
