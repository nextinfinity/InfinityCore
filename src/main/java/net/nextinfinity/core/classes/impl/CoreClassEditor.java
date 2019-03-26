package net.nextinfinity.core.classes.impl;

import de.tr7zw.itemnbtapi.NBTItem;
import net.nextinfinity.core.Game;
import net.nextinfinity.core.classes.ClassEditor;
import net.nextinfinity.core.classes.GameClass;
import net.nextinfinity.core.classes.listeners.EditListener;
import net.nextinfinity.core.item.ItemBuilder;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.utils.ConfigManager;
import net.nextinfinity.core.utils.Serializer;
import net.nextinfinity.core.utils.Settings;
import net.nextinfinity.core.utils.WoolUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class CoreClassEditor implements ClassEditor {

	private final Game game;
	private final GamePlayer player;
	private final Inventory inventory;
	private final Inventory currentEffectMenu;
	private static Inventory colorMenu;
	private static Inventory addEffectMenu;
	private final EditListener listener;
	private final String className;
	private final List<PotionEffect> effects = new ArrayList<>();

	static {
		colorMenu = Bukkit.createInventory(null, 18, "Color Selector");
		for (ChatColor chatColor : ChatColor.values()) {
			ItemStack wool = WoolUtil.getWool(chatColor);
			if (wool != null) {
				colorMenu.addItem(WoolUtil.getWool(chatColor));
			}
		}
		addEffectMenu = Bukkit.createInventory(null, 9 * (PotionEffectType.values().length/9 + 1), "Add Effect");
		for (PotionEffectType type : PotionEffectType.values()) {
			ItemBuilder builder = new ItemBuilder(Material.POTION);
			builder.setName(Settings.getPrimary() + type.getName().toUpperCase());
			builder.setNBTString("effect", type.getName());
			builder.addNBTTag("effect-new");
			addEffectMenu.addItem(builder.getItem());
		}
	}

	public CoreClassEditor(Game game, GamePlayer player, String className) {
		this.game = game;
		this.player = player;
		this.inventory = Bukkit.createInventory(null, 45, "Class Editor: " + className.toUpperCase());
		this.currentEffectMenu = Bukkit.createInventory(null, 9, "Effects: " + className.toUpperCase());
		this.listener = new EditListener(this, game);
		this.className = className;
		loadItems();
	}

	@Override
	public void open() {
		player.getBukkitPlayer().openInventory(inventory);
		Bukkit.getPluginManager().registerEvents(listener, game);
	}

	@Override
	public void close() {
		HandlerList.unregisterAll(listener);
		ConfigManager configManager = new ConfigManager(game, "classes.yml");
		ConfigurationSection configFile = configManager.getConfig();
		if (!configFile.contains(className.toLowerCase())) {
			configFile.createSection(className.toLowerCase());
		}
		ConfigurationSection config = configFile.getConfigurationSection(className.toLowerCase());
		ItemStack[] items = inventory.getContents();
		for (int i = 0; i < 9; i++) {
			ItemStack item = items[i + 18];
			if (item != null) {
				Serializer.saveItem(config, "items." + i, item);
			}
		}
		ItemStack helmet = items[1];
		if (helmet != null && !(new NBTItem(helmet).hasKey("placeholder"))) {
			Serializer.saveItem(config, "helmet", helmet);
		}
		ItemStack chestplate = items[3];
		if (chestplate != null && !(new NBTItem(chestplate).hasKey("placeholder"))) {
			Serializer.saveItem(config, "chestplate", chestplate);
		}
		ItemStack leggins = items[5];
		if (leggins != null && !(new NBTItem(leggins).hasKey("placeholder"))) {
			Serializer.saveItem(config, "leggings", leggins);
		}
		ItemStack boots = items[7];
		if (boots != null && !(new NBTItem(boots).hasKey("placeholder"))) {
			Serializer.saveItem(config, "boots", boots);
		}
		ItemStack invItem = items[40];
		if (invItem != null && !(new NBTItem(invItem).hasKey("placeholder"))) {
			Serializer.saveItem(config, "inv-item", invItem);
		}
		Serializer.savePotionEffects(config, "effects", effects);
		config.set("color", new NBTItem(items[38]).getString("color"));
		configManager.saveConfig();
		configManager.reloadConfig();
		game.getClassManager().loadClasses();
	}

	@Override
	public void setColor(ItemStack wool) {
		inventory.setItem(38, wool);
	}

	@Override
	public void addEffect(String potionType) {
		PotionEffectType type = PotionEffectType.getByName(potionType);
		for (PotionEffect effect : effects) {
			if (effect.getType() == type) {
				effects.remove(effect);
				effects.add(new PotionEffect(type, Integer.MAX_VALUE, effect.getAmplifier() + 1, false, false));
				updateEffects();
				return;
			}
		}
		effects.add(new PotionEffect(type, Integer.MAX_VALUE, 1, false, false));
		updateEffects();
	}

	@Override
	public void removeEffect(String potionType) {
		PotionEffectType type = PotionEffectType.getByName(potionType);
		for (PotionEffect effect : effects) {
			if (effect.getType() == type) {
				effects.remove(effect);
				if (effect.getAmplifier() > 1) {
					effects.add(new PotionEffect(type, Integer.MAX_VALUE, effect.getAmplifier() - 1, false, false));
				}
				updateEffects();
				return;
			}
		}
	}

	@Override
	public GamePlayer getPlayer() {
		return player;
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public Inventory getColorMenu() {
		return colorMenu;
	}

	@Override
	public Inventory getCurrentEffectMenu() {
		return currentEffectMenu;
	}

	@Override
	public Inventory getAddEffectMenu() {
		return addEffectMenu;
	}

	private void loadItems() {
		ItemBuilder glassBuilder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE);
		glassBuilder.setName("");
		glassBuilder.addNBTTag("glass");
		ItemStack glass = glassBuilder.getItem();
		for (int i = 0; i < 18; i++) {
			inventory.setItem(i, glass);
		}
		for (int i = 27; i < 44; i++) {
			inventory.setItem(i, glass);
		}
		GameClass gameClass = game.getClassManager().getClass(className.toLowerCase());
		if (gameClass != null) {
			ItemStack[] armor = gameClass.getArmor();
			for (int i = 0; i < 4; i++) {
				inventory.setItem(7 - 2 * i, armor[i]);
			}
			ItemStack[] items = gameClass.getItems();
			for (int i = 0; i < 9; i++) {
				inventory.setItem(i + 18, items[i]);
			}
			this.effects.addAll(gameClass.getEffects());
			inventory.setItem(40, gameClass.getGUIItem());
			inventory.setItem(38, WoolUtil.getWool(gameClass.getColor()));
		}
		if (inventory.getItem(1) == null || new NBTItem(inventory.getItem(1)).hasKey("glass")) {
			ItemBuilder helmetPlaceholder = new ItemBuilder(Material.GLASS_PANE);
			helmetPlaceholder.setName(Settings.getPrimary() + "Helmet Placeholder");
			helmetPlaceholder.setLore(Settings.getSecondary() + "Replace this with the class's helmet");
			helmetPlaceholder.addNBTTag("placeholder");
			inventory.setItem(1, helmetPlaceholder.getItem());
		}
		if (inventory.getItem(3) == null || new NBTItem(inventory.getItem(3)).hasKey("glass")) {
			ItemBuilder chestplatePlaceholder = new ItemBuilder(Material.GLASS_PANE);
			chestplatePlaceholder.setName(Settings.getPrimary() + "Chestplate Placeholder");
			chestplatePlaceholder.setLore(Settings.getSecondary() + "Replace this with the class's chestplate");
			chestplatePlaceholder.addNBTTag("placeholder");
			inventory.setItem(3, chestplatePlaceholder.getItem());
		}
		if (inventory.getItem(5) == null || new NBTItem(inventory.getItem(5)).hasKey("glass")) {
			ItemBuilder leggingsPlaceholder = new ItemBuilder(Material.GLASS_PANE);
			leggingsPlaceholder.setName(Settings.getPrimary() + "Leggings Placeholder");
			leggingsPlaceholder.setLore(Settings.getSecondary() + "Replace this with the class's leggings");
			leggingsPlaceholder.addNBTTag("placeholder");
			inventory.setItem(5, leggingsPlaceholder.getItem());
		}
		if (inventory.getItem(7) == null || new NBTItem(inventory.getItem(7)).hasKey("glass")) {
			ItemBuilder bootsPlaceholder = new ItemBuilder(Material.GLASS_PANE);
			bootsPlaceholder.setName(Settings.getPrimary() + "Boots Placeholder");
			bootsPlaceholder.setLore(Settings.getSecondary() + "Replace this with the class's boots");
			bootsPlaceholder.addNBTTag("placeholder");
			inventory.setItem(7, bootsPlaceholder.getItem());
		}
		if (inventory.getItem(40) == null || new NBTItem(inventory.getItem(40)).hasKey("glass")) {
			ItemBuilder invPlaceholder = new ItemBuilder(Material.GLASS_PANE);
			invPlaceholder.setName(Settings.getPrimary() + "Menu Item Placeholder");
			invPlaceholder.setLore(Settings.getSecondary() + "Replace this with the class's menu item");
			invPlaceholder.addNBTTag("placeholder");
			inventory.setItem(40, invPlaceholder.getItem());
		}
		if (inventory.getItem(38) == null || new NBTItem(inventory.getItem(38)).hasKey("glass")) {
			inventory.setItem(38, WoolUtil.getWool(ChatColor.WHITE));
		}
		updateEffects();
		ItemBuilder addPotion = new ItemBuilder(Material.GLASS_BOTTLE);
		addPotion.setName(Settings.getSecondary() + "Add Effect");
		addPotion.addNBTTag("addeffect");
		currentEffectMenu.setItem(7, addPotion.getItem());
		ItemBuilder savePotion = new ItemBuilder(Material.BARRIER);
		savePotion.setName(Settings.getPrimary() + "Save Effects");
		savePotion.addNBTTag("saveeffects");
		currentEffectMenu.setItem(8, savePotion.getItem());
		ItemBuilder close = new ItemBuilder(Material.BARRIER);
		close.setName(Settings.getPrimary() + "Close Editor");
		close.addNBTTag("close");
		inventory.setItem(44, close.getItem());
	}

	private void updateEffects() {
		for (int i = 0; i < 7; i++) {
			if (effects.size() > i) {
				ItemBuilder effect = new ItemBuilder(Material.POTION);
				effect.setName(Settings.getPrimary() + effects.get(i).getType().getName().toUpperCase());
				effect.setLore(Settings.getSecondary() + "Level: " + effects.get(i).getAmplifier() + "\n" + Settings.getSecondary() + "Click to remove!");
				effect.setNBTString("effect", effects.get(i).getType().getName());
				effect.addNBTTag("effect-current");
				currentEffectMenu.setItem(i, effect.getItem());
			} else {
				currentEffectMenu.setItem(i, null);
			}
		}
		ItemBuilder potion = new ItemBuilder(Material.POTION);
		potion.setName(Settings.getPrimary() + "Effects");
		potion.addNBTTag("potion");
		StringBuilder lore = new StringBuilder();
		if (effects.size() > 0) {
			for (PotionEffect effect : effects) {
				lore.append(Settings.getSecondary() + effect.getType().getName() + " " + effect.getAmplifier() + "\n");
			}
			lore.setLength(lore.length() - 1);
		} else {
			lore.append(Settings.getSecondary() + "No effects");
		}
		potion.setLore(lore.toString());
		inventory.setItem(42, potion.getItem());
	}
}
