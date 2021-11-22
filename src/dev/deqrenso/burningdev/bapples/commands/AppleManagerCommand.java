package dev.deqrenso.burningdev.bapples.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import dev.deqrenso.burningdev.bapples.bApples;
import dev.deqrenso.burningdev.bapples.files.ApplesFile;
import dev.deqrenso.burningdev.bapples.utils.CC;

public class AppleManagerCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;
		if(!player.hasPermission("bapple.command.manager")){
			return true;
		}
		
		if (args.length == 0) {
			this.getUsage(player, label);
			return true;
		}
		if (args[0].equalsIgnoreCase("create")) {
			String name = args[1];
			String displayName = args[2];
			String permission = args[4];
			Integer cd = CC.tryParse(args[3]);
			if (name == null) {
				this.getUsage(player, label);
				return true;
			} else if (displayName == null) {
				bApples.getInstance().getManager().createApple(name, "&cExample Apple", 60, null);
				player.sendMessage(CC.translate("&a&l✦ &7¡Effect added! Parametters:"));
				player.sendMessage(CC.translate("&c&l✦ &cName: &7"+name));
				player.sendMessage(CC.translate("&b&l✦ &bDisplay Name: &cExample Apple"));
				player.sendMessage(CC.translate("&e&l✦ &bCooldown: &760"));
				player.sendMessage(CC.translate("&2&l✦ &2Permission: &7null"));
				return true;
			} else if (cd == null) {
				bApples.getInstance().getManager().createApple(name, displayName, 60, null);
				player.sendMessage(CC.translate("&a&l✦ &7¡Effect added! Parametters:"));
				player.sendMessage(CC.translate("&c&l✦ &cName: &7"+name));
				player.sendMessage(CC.translate("&b&l✦ &bDisplay Name: "+displayName));
				player.sendMessage(CC.translate("&e&l✦ &bCooldown: &760"));
				player.sendMessage(CC.translate("&2&l✦ &2Permission: &7null"));
				return true;
			} else if (permission == null) {
				bApples.getInstance().getManager().createApple(name, displayName, cd, null);
				player.sendMessage(CC.translate("&a&l✦ &7¡Effect added! Parametters:"));
				player.sendMessage(CC.translate("&c&l✦ &cName: &7"+name));
				player.sendMessage(CC.translate("&b&l✦ &bDisplay Name: "+displayName));
				player.sendMessage(CC.translate("&e&l✦ &bCooldown: &7"+String.valueOf(cd)));
				player.sendMessage(CC.translate("&2&l✦ &2Permission: &7null"));
				return true;
			} else {
				bApples.getInstance().getManager().createApple(name, displayName, cd, permission);
				player.sendMessage(CC.translate("&a&l✦ &7¡Effect added! Parametters:"));
				player.sendMessage(CC.translate("&c&l✦ &cName: &7"+name));
				player.sendMessage(CC.translate("&b&l✦ &bDisplay Name: "+displayName));
				player.sendMessage(CC.translate("&e&l✦ &bCooldown: &7"+String.valueOf(cd)));
				player.sendMessage(CC.translate("&2&l✦ &2Permission: &7"+permission));
				return true;
			}
		} else if (args[0].equalsIgnoreCase("delete")) {
			String name = args[1];
			if(!bApples.getInstance().getManager().isApple(name)){
				player.sendMessage(CC.translate("&4&l✦ &7Apple with name "+name+" does not exist"));
				return true;
			}
			if (bApples.getInstance().getManager().isApple(name)) {
				player.sendMessage(CC.translate("&c&l✦ &7Apple with name &7"+name+"&4&l has been deleted!"));
				bApples.getInstance().getManager().deleteApple(name);
				return true;
			}
		} else if (args[0].equalsIgnoreCase("permission")) {
			String name = args[1];
			if(!bApples.getInstance().getManager().isApple(name)){
				player.sendMessage(CC.translate("&4&l✦ &7Apple with name "+name+" does not exist"));
				return true;
			}
			String permission = args[2];
			if (permission != null) {
				player.sendMessage(CC.translate("&c&l✦ &7Permission setted &7"+permission+"!"));
				bApples.getInstance().getManager().setPermission(name, permission);
				return true;
			}

		} else if (args[0].equalsIgnoreCase("cooldown")) {
			String name = args[1];
			if(!bApples.getInstance().getManager().isApple(name)){
				player.sendMessage(CC.translate("&4&l✦ &7Apple with name "+name+" does not exist"));
				return true;
			}
			Integer cooldown = CC.tryParse(args[2]);
			if (cooldown != null) {
				player.sendMessage(CC.translate("&c&l✦ &7Cooldown setted &7"+String.valueOf(cooldown)+"!"));
				bApples.getInstance().getManager().setCooldown(name, cooldown);
				return true;
			}else{
				this.getUsage(player, label);
			}

		} else if (args[0].equalsIgnoreCase("list")) {
			player.sendMessage(CC.translate("&8&m-----*--------*-----"));
			for (String key : ApplesFile.getConfig().getConfigurationSection("APPLE").getKeys(false)) {
				player.sendMessage(CC.translate("&7"
						+ key + " &a(" + bApples.getInstance()
								.getManager().getDisplayName(key)
						+ "&a)"));
			}
			player.sendMessage(CC.translate("&8&m-----*--------*-----"));
			return true;
		} else if (args[0].equalsIgnoreCase("reload")) {
			bApples.getInstance().getManager().reloadConfig();
			player.sendMessage(CC.translate("&a&l✦ &aAll files has been succesfully reloaded"));
			return true;
		} else if (args[0].equalsIgnoreCase("message")) {
			String name = args[1];
			if(!bApples.getInstance().getManager().isApple(name)){
				player.sendMessage(CC.translate("&4&l✦ &7Apple with name "+name+" does not exist"));
				return true;
			}
			String message = StringUtils.join((Object[])args, ' ', 2, args.length);
			player.sendMessage(CC.translate("&c&l✦ &7Message setted &7"+message+"!"));
			bApples.getInstance().getManager().setMessage(name, message);
			return true;
		} else if (args[0].equalsIgnoreCase("displayname")) {
			String name = args[1];
			if(!bApples.getInstance().getManager().isApple(name)){
				player.sendMessage(CC.translate("&4&l✦ &7Apple with name "+name+" does not exist"));
				return true;
			}
			String displayName = args[2];
			player.sendMessage(CC.translate("&a&l✦ &7¡Display name setted! Parametters:"));
			player.sendMessage(CC.translate("&c&l✦ &cNew display name: &7"+displayName));
			player.sendMessage(CC.translate("&c&l✦ &cOld display name: &7"+bApples.getInstance().getManager().getDisplayName(name)));
			bApples.getInstance().getManager().setDisplayName(name, displayName);
			return true;
		} else if (args[0].equalsIgnoreCase("add-effect")) {
			String name = args[1];
			if(!bApples.getInstance().getManager().isApple(name)){
				player.sendMessage(CC.translate("&4&l✦ &7Apple with name "+name+" does not exist"));
				return true;
			}
			String effect = args[2];
			
			if(!effect.contains(";")){
				this.getUsage(player, label);
				return true;
			}
			if(PotionEffectType.getByName(effect.split(";")[0]) == null){
				this.getUsage(player, label);
				return true;
			}
			Integer time = Integer.valueOf(effect.split(";")[1]);
			Integer level = Integer.valueOf(effect.split(";")[2])-1;
			bApples.getInstance().getManager().addEffect(name, effect, time, level);
			player.sendMessage(CC.translate("&a&l✦ &7¡Effect added! Parametters:"));
			player.sendMessage(CC.translate("&c&l✦ &cName: &7"+effect.split(";")[0]));
			player.sendMessage(CC.translate("&b&l✦ &bTime: &7"+time+" seconds"));
			player.sendMessage(CC.translate("&e&l✦ &bLevel: &7"+level));
			return true;
		} else if (args[0].equalsIgnoreCase("del-effect")) {
			String name = args[1];
			if(!bApples.getInstance().getManager().isApple(name)){
				player.sendMessage(CC.translate("&4&l✦ &7Apple with name "+name+" does not exist"));
				return true;
			}
			Integer effect = CC.tryParse(args[2]);
			bApples.getInstance().getManager().removeEffect(name, effect);
			player.sendMessage(CC.translate("&a&l✦ &7Effect with number "+effect+" has been removed!"));
			return true;
		} else {
			this.getUsage(player, label);
		}
		return false;
	}

	public void getUsage(Player player, String label) {
		player.sendMessage(CC.translate("&a&m-----*-----------------*-----"));
		send(player, "&a&l✦ &7/" + label + " create <NAME> <DISPLAYNAME> <COOLDOWN-IN-SECONDS> <PERMISSION>");
		send(player, "&a&l✦ &7/" + label +  " delete <NAME>");
		send(player, "&a&l✦ &7/" + label +  " displayname <NAME> <NEW-DISPLAYNAME>");
		send(player, "&a&l✦ &7/" + label +  " add-effect <NAME> <EFFECT-WITH-SPLITEATORS> (/"+label+" add-effect Test FIRE_RESISTANCE;60;1)");
		send(player, "&a&l✦ &7/" + label +  " remove-effect <NAME> <NUMBER>");
		send(player, "&a&l✦ &7/" + label +  " message <NAME> <NEW-MESSAGE>");
		send(player, "&a&l✦ &7/" + label +  " permission <NAME> <NEW-PERMISSION> (You put null in 'NEW PERMISSION' to not need permission to eat apple)");
		send(player, "&a&l✦ &7/" + label +  " cooldown <NAME> <NEW-COOLDOWN>");


		player.sendMessage(CC.translate("&a&m-----*-----------------*-----"));
	}

	public void send(Player player, String msg) {
		player.sendMessage(CC.translate(msg));
	}

}
