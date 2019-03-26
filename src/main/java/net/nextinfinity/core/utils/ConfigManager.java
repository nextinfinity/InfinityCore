package net.nextinfinity.core.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

/**
 * Bukkit's arbitrary config file implementation
 */
public class ConfigManager {

	private final String fileName;
	private final JavaPlugin plugin;

	private File configFile;
	private FileConfiguration fileConfiguration;

	/**
	 * Creates a new ConfigManager object for a file.
	 *
	 * @param plugin the associated plugin
	 * @param fileName the file for the ConfigManager object
	 */
	public ConfigManager(JavaPlugin plugin, String fileName) {
		this.plugin = plugin;
		this.fileName = fileName;
		this.configFile = new File(plugin.getDataFolder(), fileName);
	}

	/**
	 * Reloads the config.
	 */
	public void reloadConfig() {
		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

		// Look for defaults in the jar
		InputStream defConfigStream = plugin.getResource(fileName);
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
			fileConfiguration.setDefaults(defConfig);
		}
	}

	/**
	 * Returns the FileConfiguration object for the config file.
	 *
	 * @return the FileConfiguration object
	 */
	public FileConfiguration getConfig() {
		if (fileConfiguration == null) {
			this.reloadConfig();
		}
		return fileConfiguration;
	}

	/**
	 * Writes the config to the disk.
	 */
	public void saveConfig() {
		if (fileConfiguration != null && configFile != null) {
			try {
				getConfig().save(configFile);
			} catch (IOException ex) {
				plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
			}
		}
	}

	/**
	 * Writes the default config files to the disk if no file exists.
	 */
	public void saveDefaultConfig() {
		if (!configFile.exists()) {
			this.plugin.saveResource(fileName, false);
		}
	}
}
