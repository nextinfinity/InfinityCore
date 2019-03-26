package net.nextinfinity.core.arena;

import net.nextinfinity.core.arena.impl.CoreArena;
import net.nextinfinity.core.arena.ArenaEditor;
import net.nextinfinity.core.player.GamePlayer;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;
import java.util.Map;

/**
 * The ArenaManager object is a management interface for individual Arena objects.
 */
public interface ArenaManager {

    /**
     * Gets the arena with a certain name.
     *
     * @param name the name of the arena to search for
     * @return the Arena object for the arena with the searched name
     */
    Arena getArena(String name);

    /**
     * Removes an Arena object from memory.
     *
     * @param arena the arena to remove
     */
    void removeArena(Arena arena);

    /**
     * Loads stored data for arenas into memory. This should usually only be called during game initialization.
     */
    void loadArenas();

    /**
     * Unload and delete all arena worlds.
     */
    void clearArenas();

    /**
     * Creates an editing session for an arena.
     *
     * @param name the name of the arena to create the edit session for
     * @param player the entity to apply the editing session to
     * @return the created ArenaEditor object
     * @see ArenaEditor
     */
    ArenaEditor createEditor(String name, GamePlayer player);

    /**
     * Gets the ArenaMenu object
     *
     * @return the ArenaMenu object
     */
    ArenaMenu getMenu();

    /**
     * Creates a schematic from the player's WorldEdit selection and places the schematic in the game's schematic folder.
     *
     * @param player the GamePlayer to whose Player is associated with the selection
     * @param name the name of the schematic
     * @return true if successful, false otherwise
     */
    boolean createSchematic(GamePlayer player, String name);

    /**
     * Sets the global lobby the location.
     *
     * @param location the new lobby location
     */
    void setLobby(Location location);

    /**
     * Gets the map of all active arenas, where the key is the name of the arena and the list is all arenas running for that map.
     *
     * @return the Map of all active arenas
     */
    Map<String, List<Arena>> getArenaMap();

    /**
     * Alters the Java class to be used for arenas, in case a game wishes to use a custom arena class.
     * The new class must implement Arena.
     * It may be helpful for custom arena classes to extend CoreArena, as this implements all necessary functions.
     *
     * @param newClass the new Java class to use for arenas
     * @see CoreArena
     */
    void setArenaClass(Class<? extends Arena> newClass);

    /**
     * Check if a World belongs to an Arena.
     *
     * @param world the world to check
     * @return true if the World belongs to an Arena, false otherwise
     */
    boolean isArenaWorld(World world);

    /**
     * Unloads a World from Bukkit and deletes it from the disk.
     *
     * @param world the World to delete
     */
    void deleteWorld(World world);
}
