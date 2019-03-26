package net.nextinfinity.core.commands.impl;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.commands.executors.*;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.nextinfinity.core.commands.CommandHandler;

import java.util.Arrays;
import java.util.HashMap;

public class CoreCommandHandler implements CommandHandler {

	private final HashMap<String, Executor> commands = new HashMap<>();
	private final Game game;

	public CoreCommandHandler(Game game) {
		this.game = game;
	}

	@Override
	public void registerCommands() {
		commands.put("join", new Join(game));
		commands.put("quit", new Quit());
		commands.put("spectate", new Spectate());
		commands.put("start", new Start());

		commands.put("createarena", new CreateArena(game));
		commands.put("delclass", new DeleteClass(game));
		commands.put("editarena", new EditArena(game));
		commands.put("editclass", new EditClass(game));
		commands.put("setlobby", new SetLobby(game));
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender instanceof Player) {
			Player bukkitPlayer = (Player) sender;
			GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
			if (args.length > 0 && commands.containsKey(args[0].toLowerCase())) {
				commands.get(args[0].toLowerCase()).executeCommand(player, Arrays.copyOfRange(args, 1, args.length));
			} else {
				commands.get("help").executeCommand(player, "");
			}
			return true;
		} else {
			sender.sendMessage("Only players can use " + Settings.getName());
		}
		return true;
	}
}
