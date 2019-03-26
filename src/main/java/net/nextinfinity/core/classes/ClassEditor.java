package net.nextinfinity.core.classes;

import net.nextinfinity.core.player.GamePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The ClassEditor is an in-game editor to allow admins to create classes without having to manually edit files.
 */
public interface ClassEditor {

	/**
	 * Opens the editing session. This should be called after the creation of the ClassEditor object.
	 * <br>The ClassEditor inventory menu will be opened for the player.
	 */
	void open();

	/**
	 * Closes the editing session.
	 * <br>It is at this point that the edited class data is saved to the disk.
	 */
	void close();

	/**
	 * Sets the ChatColor for the class to that associated with a wool ItemStack.
	 * The wool must have the "color" NBT string set to a valid ChatColor.
	 *
	 * @param wool the wool object corresponding to the desired ChatColor
	 */
	void setColor(ItemStack wool);

	/**
	 * Adds a potion effect to the class. If the effect does not exist, it will be added at level 1,
	 * otherwise its level will be incremented.
	 *
	 * @param potionType the internal name of the potion effect to add
	 */
	void addEffect(String potionType);

	/**
	 * Removes a potion effect from the class. If the effect is level one, it will be removed,
	 * otherwise its level will be decremented.
	 *
	 * @param potionType the internal name of the potion effect to remove
	 */
	void removeEffect(String potionType);

	/**
	 * Gets the GamePlayer associated with the ClassEditor
	 *
	 * @return the GamePlayer using the ClassEditor
	 */
	GamePlayer getPlayer();

	/**
	 * Gets the primary inventory menu where items/armor may be edited and other menus may be accessed.
	 *
	 * @return the main inventory menu
	 */
	Inventory getInventory();

	/**
	 * Gets the menu to set the color for the class.
	 *
	 * @return the color menu
	 */
	Inventory getColorMenu();

	/**
	 * Gets the menu of existing potion effects for the class.
	 *
	 * @return the current effect menu
	 */
	Inventory getCurrentEffectMenu();

	/**
	 * Gets the menu to add a new potion effect to the class.
	 *
	 * @return the add effect menu
	 */
	Inventory getAddEffectMenu();

}
