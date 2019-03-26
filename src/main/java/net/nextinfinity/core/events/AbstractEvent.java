package net.nextinfinity.core.events;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * The AbstractEvent class forms the basis for all events fired by CTCore.
 */
public abstract class AbstractEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Game game;
    private final Arena arena;

    /**
     * Creates a new AbstractEvent.
     *
     * @param game the game which is running
     * @param arena the arena which the event occured in
     */
    AbstractEvent(Game game, Arena arena) {
        this.game = game;
        this.arena = arena;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Gets the game which is running.
     *
     * @return the Game object for the running game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Gets the arena the event occured in.
     *
     * @return the Arena object of the relevant arena
     */
    public Arena getArena() {
        return arena;
    }
}
