package net.nextinfinity.core.classes.listeners;

import de.tr7zw.itemnbtapi.NBTItem;
import net.nextinfinity.core.Game;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The ClassListener object listens for entity clicks in the class selection inventory and handles them accordingly.
 * <br>If the entity clicks an unlocked class, it will be assigned and the inventory closed. Otherwise, they will be sent a message.
 */
public class ClassListener implements Listener {

    private final Game game;

    public ClassListener(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getInventory().getName() == null) return;
        if (event.getInventory().getName().equalsIgnoreCase("Class Selection")) {
            if (event.getCurrentItem() == null) return;
            Player bukkitPlayer = (Player) event.getWhoClicked();
            GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
            NBTItem nbt = new NBTItem(event.getCurrentItem());
            if (nbt.hasKey("class")) {
                if (nbt.hasKey("unlocked") && nbt.getBoolean("unlocked")) {
                    String gameClass = nbt.getString("class");
                    player.setClass(game.getClassManager().getClass(gameClass));
                    player.sendMessage(Settings.getSecondary() + "Your class has been set to: " + Settings.getPrimary() +
                            gameClass.toUpperCase());
                } else {
                    player.sendMessage(Settings.getError() + "That class is not unlocked!");
                }
                player.getBukkitPlayer().closeInventory();
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player bukkitPlayer = event.getPlayer();
        GamePlayer player = game.getPlayerHandler().getPlayer(bukkitPlayer);
        if (player.isPlaying()) {
            ItemStack item = bukkitPlayer.getInventory().getItemInMainHand();
            if (item != null) {
                NBTItem itemNBT = new NBTItem(item);
                if (itemNBT.hasKey("classselector")) {
                    game.getClassManager().openGUI(player);
                }
            }
        }
    }
}
