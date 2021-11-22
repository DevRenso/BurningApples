package dev.deqrenso.burningdev.bapples.files;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import dev.deqrenso.burningdev.bapples.bApples;


public class ApplesFile extends YamlConfiguration {
	
	private static ApplesFile config;
	private bApples plugin;
	private final File configFile;

	public static ApplesFile getConfig() {
		if (ApplesFile.config == null) {
			ApplesFile.config = new ApplesFile();
		}
		return ApplesFile.config;
	}
	private static bApples main() {
		return bApples.getInstance();
	}
	// TODO: make a new file or create if not exits!
	public ApplesFile() {
		this.plugin = main();
		this.configFile = new File(this.plugin.getDataFolder(), "apples.yml");
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
		main().saveResource("apples.yml", false);
	}

	public static void saveConfig() {
		ApplesFile.config.save();
		ApplesFile.config.reload();
	}
}