package net.nextinfinity.core.utils;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Serializer class handles the saving and loading of complex items to/from text files.
 */
public class Serializer {

	/**
	 * Gets the data for a serialized ItemStack and deserializes it into an ItemStack object.
	 *
	 * @param config the ConfigurationSection the serialized ItemStack is to be loaded from
	 * @return the deserialized ItemStack
	 */
	public static ItemStack loadItem(ConfigurationSection config) {
		if (config != null) {
			return ItemStack.deserialize(config.getValues(true));
		}
		return null;
	}

	/**
	 * Serializes an ItemStack and saves it to a ConfigurationSection.
	 *
	 * @param config the ConfigurationSection the serialized ItemStack is to be saved to
	 * @param path the path within the ConfigurationSection to save the ItemStack to
	 * @param item the ItemStack to serialize
	 */
	public static void saveItem(ConfigurationSection config, String path, ItemStack item) {
		config.createSection(path, item.serialize());
	}

	/**
	 * Gets the data for a serialized Location and deserializes it into a Location object.
	 *
	 * @param config the ConfigurationSection the serialized Location is to be loaded from
	 * @return the deserialized Location
	 */
	public static Location loadLocation(ConfigurationSection config) {
		return Location.deserialize(config.getValues(true));
	}

	/**
	 * Loads a spawn with the set World as a target.
	 *
	 * @param config the ConfigurationSection to load the spawn from
	 * @param world the World to set the spawn to
	 * @return the deserialized Location
	 */
	public static Location loadSpawn(ConfigurationSection config, World world) {
		Map<String, Object> serialized = config.getValues(true);
		serialized.put("world", world.getName());
		return Location.deserialize(serialized);
	}

	/**
	 * Serializes a Location and saves it to a ConfigurationSection
	 *
	 * @param config the ConfigurationSection the serialized Location is to be saved to
	 * @param path the path within the ConfigurationSection to save the Location to
	 * @param location the Location to serialize
	 */
	public static void saveLocation(ConfigurationSection config, String path, Location location) {
		config.createSection(path, location.serialize());
	}

	/**
	 * Gets the data for a list of serialized Locations and deserializes them into a list of Location objects.
	 *
	 * @param config the ConfigurationSection the list of serialized Locations is to be loaded from
	 * @param world the World to set the spawns to
	 * @return the list of deserialized Locations
	 */
	public static List<Location> loadSpawnList(ConfigurationSection config, World world) {
		List<Location> list = new ArrayList<>();
		if (config != null) {
			for (String key : config.getKeys(false)) {
				Map<String, Object> serialized = config.getConfigurationSection(key).getValues(false);
				serialized.put("world", world.getName());
				Location location = Location.deserialize(serialized);
				list.add(location);
			}
		}
		return list;
	}

	public static void saveSpawn(ConfigurationSection config, String path, Location location) {
		Map<String, Object> serialized = location.serialize();
		serialized.remove("world");
		config.createSection(path, serialized);
	}

	/**
	 * Serializes a list of Locations and saves it to a ConfigurationSection
	 *
	 * @param config the ConfigurationSection the serialized Locations are to be saved to
	 * @param locations the Locations to serialize
	 */
	public static void saveSpawnList(ConfigurationSection config, List<Location> locations) {
		config.set("spawns", null);
		int i = 0;
		for (Location location : locations) {
			Map<String, Object> serialized = location.serialize();
			serialized.remove("world");
			config.createSection("spawns." + i, serialized);
			i++;
		}
	}

	/**
	 * Gets the data for a list of serialized PotionEffects and deserializes them into a list of PotionEffect objects.
	 *
	 * @param config the ConfigurationSection to load effects from
	 * @return the List of deserialized PotionEffects
	 */
	public static PotionEffect loadPotionEffect(ConfigurationSection config) {
		Map<String, Object> serialized = config.getValues(true);
		return new PotionEffect(serialized);
	}

	/**
	 * Serializes a list of PotionEffects and saves it to a ConfigurationSection
	 *
	 * @param config the ConfigurationSection to save the effects in
	 * @param path the path within the Configuration to save the effects to
	 * @param effects the list of PotionEffects to serialize
	 */
	public static void savePotionEffects(ConfigurationSection config, String path, List<PotionEffect> effects) {
		if (config.getConfigurationSection(path) != null) {
			config.set(path, null);
		}
		for (PotionEffect effect : effects) {
			config.createSection(path + "." + effect.getType().getName().toLowerCase(), effect.serialize());
		}
	}
}
