package dev.deqrenso.burningdev.bapples;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev.deqrenso.burningdev.bapples.commands.AppleCommand;
import dev.deqrenso.burningdev.bapples.commands.AppleManagerCommand;
import dev.deqrenso.burningdev.bapples.files.ApplesFile;
import dev.deqrenso.burningdev.bapples.listeners.onConsumeApple;
import dev.deqrenso.burningdev.bapples.managers.AppleManager;
import dev.deqrenso.burningdev.bapples.utils.CC;
import sun.security.provider.ConfigFile;

public class bApples extends JavaPlugin {
	private static bApples instance;
	private AppleManager appleManager;
	public void onEnable() {
		instance = this;
		this.loadAll();
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage(CC.translate("&c Burning Apples"));
		Bukkit.getConsoleSender()
				.sendMessage(CC.translate("&c Created by: DeqRenso#5119 (Admin of Burning Development)"));
		Bukkit.getConsoleSender()
				.sendMessage(CC.translate("&c Description: Basic plugin for Eat apples with Effects"));
		Bukkit.getConsoleSender().sendMessage(CC.translate("&c Last update: 05/04/21"));
		Bukkit.getConsoleSender().sendMessage(CC.translate("&c Discord: discord.gg/TwYSYQqMGs"));
		Bukkit.getConsoleSender().sendMessage(CC.translate("&c ¡Thank you for using!"));
		Bukkit.getConsoleSender().sendMessage("");
	}

	public void onDisable() {
		instance = null;
	}

	public void loadAll() {
		new ApplesFile();
		new ConfigFile();
		this.appleManager = new AppleManager();
		this.loadCommands();
		this.loadListeners();
	}

	public void loadCommands() {
		getCommand("applemanager").setExecutor(new AppleManagerCommand());
		getCommand("bapple").setExecutor(new AppleCommand());
	}

	public void loadListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new onConsumeApple(), this);
	}

	public AppleManager getManager() {
		return appleManager;
	}

	public static bApples getInstance() {
		return instance;
	}

}
