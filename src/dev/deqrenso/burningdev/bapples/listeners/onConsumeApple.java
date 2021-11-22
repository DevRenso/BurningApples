package dev.deqrenso.burningdev.bapples.listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import dev.deqrenso.burningdev.bapples.bApples;
import dev.deqrenso.burningdev.bapples.files.ApplesFile;
import dev.deqrenso.burningdev.bapples.files.ConfigFile;
import dev.deqrenso.burningdev.bapples.utils.CC;

public class onConsumeApple implements Listener {

	private HashMap<UUID, Long> cooldown;

	public onConsumeApple() {
		this.cooldown = new HashMap<UUID, Long>();
	}

	public boolean isActive(final Player player) {
		return this.cooldown.containsKey(player.getUniqueId())
				&& System.currentTimeMillis() < this.cooldown.get(player.getUniqueId());
	}

	public long getMillisecondsLeft(final Player player) {
		if (this.cooldown.containsKey(player.getUniqueId())) {
			return Math.max(this.cooldown.get(player.getUniqueId()) - System.currentTimeMillis(), 0L);
		}
		return 0L;
	}

	@EventHandler
	public void onConsume(PlayerItemConsumeEvent e) {
		for (String key : ApplesFile.getConfig().getConfigurationSection("APPLE").getKeys(false)) {
			if (bApples.getInstance().getManager().getApple(key).isSimilar(e.getItem())) {
				if (this.isActive(e.getPlayer()) && ApplesFile.getConfig().getBoolean("COOLDOWN.ENABLE")) {
					e.getPlayer().sendMessage(CC.translate(ConfigFile.getConfig().getString("COOLDOWN-MESSAGE"))
							.replace("%time%", CC.getRemaining(this.getMillisecondsLeft(e.getPlayer()), true)));
					return;
				}
				if (bApples.getInstance().getManager().getPermission(key) != null
						&& !e.getPlayer().hasPermission(bApples.getInstance().getManager().getPermission(key))) {
					e.getPlayer()
							.sendMessage(CC.translate(ConfigFile.getConfig().getString("PLAYER-DONT-HAS-PERMISSION")
									.replace("%player%", e.getPlayer().getName()).replace("%apple%", key)));
					e.setCancelled(true);
				}
				if (bApples.getInstance().getManager().isStrikeEnable(key)) {
					e.getPlayer().getWorld().strikeLightningEffect(e.getPlayer().getLocation());
				}
				if (bApples.getInstance().getManager().isMessageEnable(key)) {
					bApples.getInstance().getManager().sendAll(e.getPlayer(), key,
							bApples.getInstance().getManager().getDisplayName(key));
				}
				for (int i = 0; i < bApples.getInstance().getManager().getEffects(key).size(); i++) {
					String effect = bApples.getInstance().getManager().getEffects(key).get(i);
					String[] div = effect.split(";");
					String name = div[0];
					Integer time = Integer.valueOf(div[1]) * 20;
					Integer level = Integer.valueOf(div[2]) - 1;
					e.getPlayer().removePotionEffect(PotionEffectType.getByName(name));
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.getByName(name), time, level));
				}
				this.cooldown.put(e.getPlayer().getUniqueId(),
				System.currentTimeMillis() + bApples.getInstance().getManager().getCooldown(key) * 1000);	
			}
		}
	}

}
