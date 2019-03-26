package net.nextinfinity.core.commands.executors;

import net.nextinfinity.core.player.GamePlayer;

/**
 * The Executor object handles one specific command
 */
public interface Executor {

    /**
     * Runs the code associated with the command. This should not be done manually.
     *
     * @param player the entity who used the command
     * @param args the list of arguments used with the command
     */
    void executeCommand(GamePlayer player, String... args);

}
