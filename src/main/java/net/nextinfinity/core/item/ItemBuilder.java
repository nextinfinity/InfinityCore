package net.nextinfinity.core.item;

import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * The ItemBuilder allows for construction of ItemStacks in a manner which is slightly more visually pleasant.
 */
public class ItemBuilder {

	private ItemStack item;

	public ItemBuilder(Material material) {
		item = new ItemStack(material);
	}

	/**
	 * Sets the display name of the item.
	 *
	 * @param name the new display name of the item
	 */
	public void setName(String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}

	/**
	 * Sets the lore of the item. A line break character is used to denote line breaks for a single String argument
	 * instead of a String array.
	 *
	 * @param lore the new lore of the item
	 */
	public void setLore(String lore) {
		String[] loreArray = lore.split("\n");
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(loreArray));
		item.setItemMeta(meta);
	}

	/**
	 * Adds an NBT tag to an item. Functionally, this sets an NBT boolean with the given key to true.
	 *
	 * @param tag the NBT tag to add
	 */
	public void addNBTTag(String tag) {
		NBTItem nbt = new NBTItem(item);
		nbt.setBoolean(tag, true);
		item = nbt.getItem();
	}

	/**
	 * Sets an NBT string for the item.
	 *
	 * @param key the key of the NBT string
	 * @param value the value of the NBT string
	 */
	public void setNBTString(String key, String value) {
		NBTItem nbt = new NBTItem(item);
		nbt.setString(key, value);
		item = nbt.getItem();
	}

	/**
	 * Converts the ItemBuilder into an ItemStack.
	 *
	 * @return the ItemStack of the ItemBuilder object
	 */
	public ItemStack getItem() {
		return item;
	}

}
