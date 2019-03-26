package net.nextinfinity.core.classes.impl;

import de.tr7zw.itemnbtapi.NBTItem;
import net.nextinfinity.core.classes.GameClass;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class CoreGameClass implements GameClass {

	private ItemStack[] items = new ItemStack[36];
	private int itemCount;
	private ItemStack[] armor = new ItemStack[4];
	private List<PotionEffect> effects = new ArrayList<>();
	private final String name;
	private final ChatColor color;
	private ItemStack invItem;
	private ItemStack lockedItem;
	private String permission;

	public CoreGameClass(String name, ChatColor color) {
		this.name = name;
		this.color = color;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ChatColor getColor() {
		return color;
	}

	@Override
	public String getPermission() {
		return permission;
	}

	@Override
	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public ItemStack getGUIItem() {
		return invItem;
	}

	@Override
	public ItemStack getLockedItem() {
		return lockedItem;
	}

	@Override
	public void setGUIItem(ItemStack item) {
		NBTItem unlockedNBT = new NBTItem(item);
		unlockedNBT.setBoolean("unlocked", true);
		this.invItem = unlockedNBT.getItem();
		ItemStack clone = item.clone();
		ItemMeta lockedMeta = clone.getItemMeta();
		String name = ChatColor.stripColor(lockedMeta.getDisplayName());
		lockedMeta.setDisplayName(ChatColor.DARK_GRAY + name);
		clone.setItemMeta(lockedMeta);
		NBTItem lockedNBT = new NBTItem(clone);
		lockedNBT.setBoolean("unlocked", false);
		this.lockedItem = lockedNBT.getItem();
	}

	@Override
	public ItemStack[] getItems() {
		return items;
	}

	@Override
	public void addItem(ItemStack item) {
		items[itemCount++] = item;
	}

	@Override
	public void setHelmet(ItemStack helmet) {
		armor[3] = helmet;
	}

	@Override
	public void setChestplate(ItemStack chestplate) {
		armor[2] = chestplate;
	}

	@Override
	public void setLeggings(ItemStack leggings) {
		armor[1] = leggings;
	}

	@Override
	public void setBoots(ItemStack boots) {
		armor[0] = boots;
	}

	@Override
	public ItemStack[] getArmor() {
		return armor;
	}

	@Override
	public List<PotionEffect> getEffects() {
		return effects;
	}

	@Override
	public void addEffect(PotionEffect effect) {
		effects.add(effect);
	}
}
