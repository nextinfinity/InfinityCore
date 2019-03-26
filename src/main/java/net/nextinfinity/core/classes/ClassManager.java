package net.nextinfinity.core.classes;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.player.GamePlayer;
import org.bukkit.inventory.ItemStack;

/**
 * The ClassManager object serves as a management interface for individual classes.
 * <br>It is created with the initialization of the game and should be accessed using Game#getClassManager.
 *
 * @see Game#getClassManager()
 */
public interface ClassManager {

    /**
     * Loads classes from the classes.yml file, which is contained in the resources folder of the game.
     * <br>This method should only be called once.
     */
    void loadClasses();

    /**
     * Opens the class selection GUI for a specific entity.
     *
     * @param player the GamePlayer object of the entity to open the GUI for
     */
    void openGUI(GamePlayer player);

    /**
     * Returns the GameClass object with a specific name.
     *
     * @param name the name of the desired class
     * @return the class with the specified name
     */
    GameClass getClass(String name);

    /**
     * Deletes a class from memory and storage with the specified name.
     *
     * @param name the name of the class to delete
     * @return true if the class exists and is deleted, false if no class is found
     */
    boolean deleteClass(String name);

    /**
     * Scans classes in a random order for one the player has permission for.
     *
     * @param player the player to find a class for
     * @return the GameClass found for the player, or null if no suitable class it found
     */
    GameClass getRandomClass(GamePlayer player);

    /**
     * Gets the class selector item, which may be clicked to open the class selection menu.
     *
     * @return the ItemStack of the class selector item
     */
    ItemStack getClassSelector();
}
