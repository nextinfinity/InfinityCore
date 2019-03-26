package net.nextinfinity.core.classes.impl;

import de.tr7zw.itemnbtapi.NBTItem;
import net.nextinfinity.core.Game;
import net.nextinfinity.core.classes.ClassManager;
import net.nextinfinity.core.classes.GameClass;
import net.nextinfinity.core.item.ItemBuilder;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.ConfigManager;
import net.nextinfinity.core.utils.Serializer;
import net.nextinfinity.core.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoreClassManager implements ClassManager {

    private static final List<GameClass> classes = new ArrayList<>();
    private final Game game;
    private ItemStack classSelector;

    public CoreClassManager(Game game) {
        this.game = game;
        createClassSelector();
    }

    @Override
    public void loadClasses() {
        classes.clear();
        ConfigManager configManager = new ConfigManager(game, "classes.yml");
        FileConfiguration classConfig = configManager.getConfig();
        for (String name : classConfig.getKeys(false)) {
            ConfigurationSection config = classConfig.getConfigurationSection(name);
            ChatColor color = ChatColor.WHITE;
            if (config.isSet("color")) {
                color = ChatColor.getByChar(config.getString("color"));
            }
            GameClass gameClass = new CoreGameClass(name, color);
            for (int i = 0; i < 36; i++) {
                if (config.contains("items." + i)) {
                    gameClass.addItem(Serializer.loadItem(config.getConfigurationSection("items." + i)));
                }
            }
            if (config.contains("effects")) {
                for (String effect : config.getConfigurationSection("effects").getKeys(false)) {
                    gameClass.addEffect(Serializer.loadPotionEffect(config.getConfigurationSection("effects." + effect)));
                }
            }
            gameClass.setHelmet(Serializer.loadItem(config.getConfigurationSection("helmet")));
            gameClass.setChestplate(Serializer.loadItem(config.getConfigurationSection("chestplate")));
            gameClass.setLeggings(Serializer.loadItem(config.getConfigurationSection("leggings")));
            gameClass.setBoots(Serializer.loadItem(config.getConfigurationSection("boots")));
            gameClass.setPermission(config.getString("permission"));
            ItemStack invItem = Serializer.loadItem(config.getConfigurationSection("inv-item"));
            if (invItem != null) {
                ItemMeta invMeta = invItem.getItemMeta();
                invMeta.setDisplayName(gameClass.getColor() + name.toUpperCase());
                invMeta.setLore(Collections.singletonList(Settings.getSecondary() + "Click to select"));
                invItem.setItemMeta(invMeta);
                NBTItem nbt = new NBTItem(invItem);
                nbt.setString("class", name);
                gameClass.setGUIItem(nbt.getItem());
            }
            classes.add(gameClass);
        }
    }

    @Override
    public void openGUI(GamePlayer player) {
        int size = classes.size() < 36 ? 9 * ((classes.size() - 1) / 9 + 1) : 36;
        Inventory gui = Bukkit.createInventory(null, size, "Class Selection");
        List<ItemStack> lockedItems = new ArrayList<>();
        for (GameClass gameClass : classes) {
            if (player.hasClass(gameClass)) {
                ItemStack item = gameClass.getGUIItem();
                if (item != null) {
                    gui.addItem(item);
                }
            } else {
                lockedItems.add(gameClass.getLockedItem());
            }
        }
        for (ItemStack item : lockedItems) {
            if (item != null) {
                gui.addItem(item);
            }
        }
        player.getBukkitPlayer().openInventory(gui);
    }

    @Override
    public GameClass getClass(String name) {
        for (GameClass gameClass : classes) {
            if (gameClass.getName().equalsIgnoreCase(name)) return gameClass;
        }
        return null;
    }

    @Override
    public boolean deleteClass(String name) {
        GameClass gameClass = getClass(name);
        if (gameClass == null) {
            return false;
        }
        classes.remove(gameClass);
        new ConfigManager(game, "classes.yml").getConfig().set(name.toLowerCase(), null);
        return true;
    }

    @Override
    public GameClass getRandomClass(GamePlayer player) {
        List<GameClass> shuffledClasses = new ArrayList<>(classes);
        Collections.shuffle(shuffledClasses);
        for (GameClass gameClass : shuffledClasses) {
            if (player.hasClass(gameClass)) {
                return gameClass;
            }
        }
        return null;
    }

    @Override
    public ItemStack getClassSelector() {
        return classSelector;
    }

    private void createClassSelector() {
        ItemBuilder selector = new ItemBuilder(Material.NETHER_STAR);
        selector.setName(Settings.getPrimary() + "Class Selector");
        selector.addNBTTag("classselector");
        classSelector = selector.getItem();
    }
}
