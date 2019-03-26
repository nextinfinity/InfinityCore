package net.nextinfinity.core.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * The TeleportTask runnable is a entity teleport which can be used with a delay. This is most often used if a entity needs to be teleported when joining.
 */
public class TeleportTask extends BukkitRunnable {

    private final Player player;
    private final Location to;

    /**
     * Creates a new TeleportTask object
     *
     * @param player the entity to teleport
     * @param to the location to teleport the entity to
     */
    public TeleportTask(Player player, Location to) {
        this.player = player;
        this.to = to;
    }

    /**
     *
     */
    @Override
    public void run() {
        player.teleport(to);
    }

}
