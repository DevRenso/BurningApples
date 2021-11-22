package dev.deqrenso.burningdev.bapples.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.deqrenso.burningdev.bapples.files.ApplesFile;
import dev.deqrenso.burningdev.bapples.files.ConfigFile;
import dev.deqrenso.burningdev.bapples.utils.CC;

public class AppleManager {

	public List<String> apples = new ArrayList<String>();
	public List<String> effects = new ArrayList<String>();

	public void createApple(String name, String displayName, int cooldown, String permission) {
		ApplesFile.getConfig().set("APPLE." + name + ".NAME", name);
		ApplesFile.getConfig().set("APPLE." + name + ".DISPLAYNAME", displayName);
		ApplesFile.getConfig().set("APPLE." + name + ".COOLDOWN.TIME", cooldown);
		ApplesFile.getConfig().set("APPLE." + name + ".PERMISSION", permission);
		ApplesFile.getConfig().set("APPLE." + name + ".MESSAGE.ENABLE", true);
		ApplesFile.getConfig().set("APPLE." + name + ".MESSAGE.TEXT", "&cExample message");
		ApplesFile.getConfig().set("APPLE." + name + ".EFFECTS", new ArrayList<String>());
		ApplesFile.getConfig().set("APPLE." + name + ".LORE", new ArrayList<String>());
		ApplesFile.getConfig().set("APPLE." + name + ".STRIKE-ON-EAT", true);
		ApplesFile.getConfig().set("APPLE." + name + ".COOLDOWN.MESSAGE", "&7Example cooldown message.");
		ApplesFile.getConfig().set("APPLE." + name + ".COOLDOWN.ENABLE", true);
		ApplesFile.getConfig().save();
	}

	public void deleteApple(String name) {
		ApplesFile.getConfig().set("APPLE." + name, null);
		ApplesFile.getConfig().save();
	}

	public void reloadConfig() {
		ApplesFile.getConfig().reload();
		ConfigFile.getConfig().reload();
	}

	public void setDisplayName(String name, String displayName) {
		ApplesFile.getConfig().set("APPLE." + name + ".DISPLAYNAME", displayName);
		ApplesFile.getConfig().save();
	}

	public void addEffect(String name, String effect, Integer time, Integer level) {
		for (int i = 0; i < this.getEffects(name).size(); i++) {
			effects.add(this.getEffects(name).get(i));
		}
		effects.add(effect + ";" + time + ";" + level);
		ApplesFile.getConfig().set("APPLE." + name + ".EFFECTS", effects);
		ApplesFile.getConfig().save();
	}

	public void removeEffect(String name, Integer effect) {
		for (int i = 0; i < this.getEffects(name).size(); i++) {
			effects.add(this.getEffects(name).get(i));
		}
		if (!(effects.size() < effect)) {
			ApplesFile.getConfig().set("APPLE." + name + ".EFFECTS", effects);
			ApplesFile.getConfig().save();
		} else {
			return;
		}
	}

	public void setPermission(String name, String perm) {
		if (perm == "null") {
			ApplesFile.getConfig().set("APPLE." + name + ".PERMISSION", null);
		}
		ApplesFile.getConfig().set("APPLE." + name + ".PERMISSION", perm);
		ApplesFile.getConfig().save();
	}

	public void setMessage(String name, String message) {
		ApplesFile.getConfig().set("APPLE." + name + ".MESSAGE.TEXT", message);
		ApplesFile.getConfig().save();
	}

	public void setCooldown(String name, Integer cooldown) {
		ApplesFile.getConfig().set("APPLE." + name + ".COOLDOWN.TIME", cooldown);
		ApplesFile.getConfig().save();
	}
 
	public String getDisplayName(String name) {
		return ApplesFile.getConfig().getString("APPLE." + name + ".DISPLAYNAME");
	}

	public ItemStack getApple(String name) {
		ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);
		ItemMeta appleMeta = apple.getItemMeta();
		appleMeta.setDisplayName(CC.translate(this.getDisplayName(name)));
		appleMeta.setLore(CC.translate(this.getLore(name)));
		apple.setItemMeta(appleMeta);
		return apple;
	}

	public boolean isStrikeEnable(String name) {
		return ApplesFile.getConfig().getBoolean("APPLE." + name + ".STRIKE-ON-EAT");
	}

	public boolean isMessageEnable(String name) {
		return ApplesFile.getConfig().getBoolean("APPLE." + name + ".MESSAGE.ENABLE");
	}

	public List<String> getEffects(String name) {
		return ApplesFile.getConfig().getStringList("APPLE." + name + ".EFFECTS");
	}

	public List<String> getLore(String name) {
		return ApplesFile.getConfig().getStringList("APPLE." + name + ".LORE");
	}

	public String getPermission(String name) {
		return ApplesFile.getConfig().getString("APPLE." + name + ".PERMISSION");
	}

	public int getCooldown(String name) {
		return ApplesFile.getConfig().getInt("APPLE." + name + ".COOLDOWN.TIME");
	}

	public String getMessage(String name) {
		return ApplesFile.getConfig().getString("APPLE." + name + ".MESSAGE.TEXT");
	}

	public List<String> getApples() {
		for (String apple : ApplesFile.getConfig().getConfigurationSection("APPLE").getKeys(false)) {
			apples.add(apple);
		}
		return apples;
	}

	public void giveApple(Player p, String name, int amount) {
		if (this.isApple(name)) {
			ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, amount, (short) 1);
			ItemMeta appleMeta = apple.getItemMeta();

			appleMeta.setDisplayName(CC.translate(this.getDisplayName(name)));
			appleMeta.setLore(CC.translate(this.getLore(name)));

			apple.setItemMeta(appleMeta);
			p.getInventory().addItem(new ItemStack[] { apple });
		}
	}

	public boolean isApple(String name) {
		return ApplesFile.getConfig().getConfigurationSection("APPLE").getKeys(false).contains(name);
	}

	public void sendCooldownMessage(Player p, String displayName) {
		ConfigFile.getConfig().getStringList("COOLDOWN-MESSAGE").forEach(msg -> {
			p.sendMessage(CC.translate(msg).replace("%player%", p.getName()).replace("%apple%", displayName));
		});
	}

	public void sendAll(Player p, String name, String displayName) {
		for (Player on : Bukkit.getOnlinePlayers()) {
			on.sendMessage(CC.translate(this.getMessage(name)).replace("%player%", p.getName()).replace("%apple%",
					this.getDisplayName(name)));
		}
	}

}
