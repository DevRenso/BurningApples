package dev.deqrenso.burningdev.bapples.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.deqrenso.burningdev.bapples.bApples;
import dev.deqrenso.burningdev.bapples.files.ConfigFile;
import dev.deqrenso.burningdev.bapples.utils.CC;

public class AppleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length < 3) {
			sender.sendMessage(CC.translate("&7Usage: /bapple <PLAYER-OR-ALL> <APPLE-NAME> <AMOUNT>"));
			return true;
		}
		String idPlayer = args[0];

		String name = args[1];
		Integer amount = CC.tryParse(args[2]);
		if (!bApples.getInstance().getManager().isApple(name)) {
			sender.sendMessage(CC.translate("&7Apple " + name + " does not exist"));
			return true;
		}
		if (amount == null) {
			amount = 1;
		}
		if (idPlayer.equalsIgnoreCase("all")) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				bApples.getInstance().getManager().giveApple(on, name, amount);
				if (!(sender instanceof Player)) {
					on.sendMessage(CC.translate(ConfigFile.getConfig().getString("GIVE-APPLE"))
							.replace("%player%", ConfigFile.getConfig().getString("CONSOLE-NAME"))
							.replace("%apple%", name));
				}
			}
			return true;
		}
		if (Bukkit.getPlayer(idPlayer) == null) {
			sender.sendMessage(CC.translate("&7Player with name " + idPlayer + " does not exist"));
			return true;
		}
		bApples.getInstance().getManager().giveApple(Bukkit.getPlayer(idPlayer), name, amount);
		if (!(sender instanceof Player)) {
			sender.sendMessage(CC.translate(ConfigFile.getConfig().getString("GIVE-APPLE")).replace("%player%", ConfigFile.getConfig().getString("CONSOLE-NAME")).replace("%apple%", name));
		}
		sender.sendMessage(CC.translate(ConfigFile.getConfig().getString("GIVE-APPLE"))
				.replace("%player%", sender.getName()).replace("%apple%", name));
		return false;
	}

}
