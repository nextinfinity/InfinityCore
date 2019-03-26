package net.nextinfinity.core.tasks;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import net.nextinfinity.core.events.GameSecondEvent;

/**
 * The TimerTask runnable is used by an Arena to manage time. These should usually not be created manually.
 *
 * @see Arena
 */
public class TimerTask extends BukkitRunnable {

    private int time;
    private final int initialTime;
    private final Arena arena;
    private final Game game;
    private final String format;

    /**
     * Creates a new TimerTask object
     *
     * @param arena the arena for the imer
     * @param time time the length of time for the timer to run, in seconds
     * @param game the game
     * @param format the format for the time string
     */
    public TimerTask(Game game, Arena arena, int time, String format) {
        this.arena = arena;
        this.initialTime = time;
        this.time = time + 1;
        this.game = game;
        this.format = format;
    }

    @Override
    public void run() {
        time--;
        arena.getScoreboard().refreshTime(time, initialTime);
        if (time % 60 == 0 || time == 30 || time <= 10) {
            String timeString = time > 60 ? time/60 + " minutes" : time + " seconds";
            for (GamePlayer player : arena.getPlayers()) {
                player.sendMessage(Settings.getSecondary() + format.replace("{TIME}", timeString));
            }
        }
        Bukkit.getPluginManager().callEvent(new GameSecondEvent(game, arena, time));
        if (time <= 0) {
            switch (arena.getState()) {
                case STARTING:
                    arena.start();
                    break;
                case INGAME:
                    arena.end();
                    break;
            }
            cancel();
        }
    }

}
