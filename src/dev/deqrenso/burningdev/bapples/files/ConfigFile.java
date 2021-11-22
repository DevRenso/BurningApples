package dev.deqrenso.burningdev.bapples.files;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import dev.deqrenso.burningdev.bapples.bApples;


public class ConfigFile extends YamlConfiguration {
	
	private static ConfigFile config;
	private bApples plugin;
	private final File configFile;

	public static ConfigFile getConfig() {
		if (ConfigFile.config == null) {
			ConfigFile.config = new ConfigFile();
		}
		return ConfigFile.config;
	}
	private static bApples main() {
		return bApples.getInstance();
	}
	// TODO: make a new file or create if not exits!
	public ConfigFile() {
		this.plugin = main();
		this.configFile = new File(this.plugin.getDataFolder(), "config.yml");
		this.saveDefault();
		this.reload();
	}

	public void reload() {
		try {
			super.load(this.configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			super.save(this.configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveDefault() {
		main().saveResource("config.yml", false);
	}

	public static void saveConfig() {
		ConfigFile.config.save();
		ConfigFile.config.reload();
	}
}