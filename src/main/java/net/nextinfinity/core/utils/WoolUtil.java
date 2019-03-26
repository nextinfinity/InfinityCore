package net.nextinfinity.core.utils;

import net.nextinfinity.core.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WoolUtil {

	/**
	 * Translates from ChatColor to colored Wool.
	 *
	 * @param chatColor the ChatColor to get wool for
	 * @return the Wool ItemStack associated with the specified ChatColor
	 */
	public static ItemStack getWool(ChatColor chatColor) {
		Material coloredWool = null;
		switch (chatColor) {
			case BLACK:
				coloredWool = Material.BLACK_WOOL;
				break;
			case DARK_BLUE:
				coloredWool = Material.BLUE_WOOL;
				break;
			case DARK_GREEN:
				coloredWool = Material.GREEN_WOOL;
				break;
			case DARK_AQUA:
				coloredWool = Material.CYAN_WOOL;
				break;
			case DARK_RED:
				coloredWool = Material.BROWN_WOOL;
				break;
			case DARK_PURPLE:
				coloredWool = Material.PURPLE_WOOL;
				break;
			case GOLD:
				coloredWool = Material.ORANGE_WOOL;
				break;
			case GRAY:
				coloredWool = Material.LIGHT_GRAY_WOOL;
				break;
			case DARK_GRAY:
				coloredWool = Material.GRAY_WOOL;
				break;
			case BLUE:
				coloredWool = Material.BLUE_WOOL;
				break;
			case GREEN:
				coloredWool = Material.LIME_WOOL;
				break;
			case AQUA:
				coloredWool = Material.LIGHT_BLUE_WOOL;
				break;
			case RED:
				coloredWool = Material.RED_WOOL;
				break;
			case LIGHT_PURPLE:
				coloredWool = Material.MAGENTA_WOOL;
				break;
			case YELLOW:
				coloredWool = Material.YELLOW_WOOL;
				break;
			case WHITE:
				coloredWool = Material.WHITE_WOOL;
				break;
		}
		if (coloredWool == null) {
			return null;
		}
		ItemBuilder itemBuilder = new ItemBuilder(coloredWool);
		itemBuilder.setName(chatColor + chatColor.name().toUpperCase());
		itemBuilder.setNBTString("color", Character.toString(chatColor.getChar()));
		return itemBuilder.getItem();
	}

}
