package net.nextinfinity.core.classes;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

/**
 * The GameClass object represents a game class, or kit. These should be accessed with the ClassManager object and not created manually.
 *
 * @see ClassManager
 */
public interface GameClass {

    /**
     * Gets the name of the class represented by the GameClass object.
     *
     * @return the name of the class
     */
    String getName();

    /**
     * Gets the color of the class represented by the GameClass object.
     *
     * @return the color of the class, as a ChatColor
     */
    ChatColor getColor();

    /**
     * Gets the permission required for access to the class in-game.
     *
     * @return the permission for the class
     */
    String getPermission();

    /**
     * Sets the permission for the class. This is set in class loading and usually should not be altered.
     *
     * @param permission the permission for the class
     */
    void setPermission(String permission);

    /**
     * Returns the item which is used to represent the class in the class selection GUI if the entity has access to the class.
     *
     * @return the item used in the GUI
     */
    ItemStack getGUIItem();

    /**
     * Returns the item which is used to represent the class in the class selection GUI if the entity does not have access to the class.
     *
     * @return the locked variation of the item used in the GUI
     */
    ItemStack getLockedItem();

    /**
     * Sets the GUI item for the class. This is set in class loading and usually should not be altered.
     *
     * @param item the item to be used in the class selection GUI
     */
    void setGUIItem(ItemStack item);

    /**
     * Gets the items given to players for the class, if applicable.
     *
     * @return the set of items for the class
     */
    ItemStack[] getItems();

    /**
     * Adds an item to the set of items for the class. This is done in class loading and usually should not be altered.
     *
     * @param item the item to be added
     */
    void addItem(ItemStack item);

    /**
     * Sets the helmet for the class. This is done in class loading and usually should not be altered.
     *
     * @param helmet the helmet item
     */
    void setHelmet(ItemStack helmet);

    /**
     * Sets the chestplate for the class. This is done in class loading and usually should not be altered.
     *
     * @param chestplate the chestplate item
     */
    void setChestplate(ItemStack chestplate);

    /**
     * Sets the leggings for the class. This is done in class loading and usually should not be altered.
     *
     * @param leggings the leggings item
     */
    void setLeggings(ItemStack leggings);

    /**
     * Sets the boots for the class. This is done in class loading and usually should not be altered.
     *
     * @param boots the boots item
     */
    void setBoots(ItemStack boots);

    /**
     * Gets the set of armor items given to players for the class, if applicable.
     *
     * @return the set of armor for the class
     */
    ItemStack[] getArmor();

    /**
     * Gets all potion effects belonging to the class.
     *
     * @return the list of all potion effects
     */
    List<PotionEffect> getEffects();

    /**
     * Adds a potion effect to the class.
     *
     * @param effect the potion effect to add
     */
    void addEffect(PotionEffect effect);

}
