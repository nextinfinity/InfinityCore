package net.nextinfinity.core.arena.listeners;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * The SignListener handles both the creation of join signs, and their usage.
 * <br>If a sign is placed by an admin with just the game name on the first line, a join sign will be created.
 * <br>If that sign is then clicked by players, the arena selection GUI will be opened.
 */
public class SignListener implements Listener {

    private final Game game;

    public SignListener(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("game.admin")) {
            Material type = event.getBlockPlaced().getType();
            if (type == Material.SIGN || type == Material.WALL_SIGN) {
                Sign sign = (Sign) event.getBlockPlaced().getState();
                if (ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase(game.getName())) {
                    sign.setLine(0, "");
                    sign.setLine(1, Settings.getName());
                    sign.setLine(2, "Click to join!");
                    sign.setLine(3, "");
                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block != null && block.getState() instanceof Sign) {
            Sign sign = (Sign) block.getState();
            if (ChatColor.stripColor(sign.getLine(1)).equalsIgnoreCase(Settings.getName())) {
                Player bukkitPlayer = event.getPlayer();
                GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
                game.getArenaManager().getMenu().open(player);
            }
        }
    }
}
