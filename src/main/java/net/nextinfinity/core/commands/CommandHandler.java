package net.nextinfinity.core.commands;

import net.nextinfinity.core.commands.executors.Executor;
import org.bukkit.command.CommandExecutor;

/**
 * The CommandHandler object receives all commands for the core and distributes them to different executors
 */
public interface CommandHandler extends CommandExecutor {

    /**
     * Registers a command to be handled by the CommandHandler object and sent to a specific Executor
     */
    void registerCommands();
}
