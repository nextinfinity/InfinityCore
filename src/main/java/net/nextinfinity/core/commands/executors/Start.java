package net.nextinfinity.core.commands.executors;

import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.arena.GameState;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.Settings;

public class Start implements Executor {

    @Override
    public void executeCommand(GamePlayer player, String... args) {
        if (!player.getBukkitPlayer().hasPermission("core.start")) {
            player.sendMessage(Settings.getError() + "You do not have permission to do that!");
            return;
        }
        if (!player.isPlaying()) {
            player.sendMessage(Settings.getError() + "You must be playing to do that!");
            return;
        }
        Arena arena = player.getArena();
        if (arena.getState() != GameState.LOBBY) {
            player.sendMessage(Settings.getError() + "The arena has already started!");
            return;
        }
        if (arena.getPlayers().size() < 2) {
            player.sendMessage(Settings.getError() + "You must have at least two players to start!");
            return;
        }
        arena.start();
    }

}
