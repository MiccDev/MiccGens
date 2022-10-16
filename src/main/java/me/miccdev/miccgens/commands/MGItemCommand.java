package me.miccdev.miccgens.commands;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.items.CustomItem;
import me.miccdev.miccgens.utils.Utils;

public class MGItemCommand extends CustomCommand {

	public MGItemCommand(Main plugin) {
		super(plugin, "mgitem");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Utils.toColour("&e[&l&3MiccGens&r&e]: &4Only players can use this command!"));
			return true;
		}
		
		Player plr = (Player) sender;
		
		if(!plr.hasPermission("miccgens.gens")) {
			plr.sendMessage(Utils.toColour("&e[&l&3MiccGens&r&e]: &4Only staff members can use this command."));
			plr.playSound(plr, Sound.ENTITY_VILLAGER_NO, 0.5f, 1);
			return true;
		}
		
		if(!CustomItem.hasItem(args[0])) {
			plr.sendMessage(Utils.toColour("&e[&l&3MiccGens&r&e]: &4That is not a custom item."));
			plr.playSound(plr, Sound.ENTITY_VILLAGER_NO, 0.5f, 1);
			return true;
		}
		
		CustomItem item = CustomItem.getItem(args[0]);
		
		plr.getInventory().addItem(item);
		plr.sendMessage(Utils.toColour("&e[&l&3MiccGens&r&e]: &bSuccessfully given you " + item.getItemMeta().getDisplayName() + "."));

		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String label, @NotNull String[] args) {
		if(args.length == 1) {
			return CustomItem.allItems.values().stream()
					.map(item -> {
						return item.id;
					}).collect(Collectors.toList());
		}
		return null;
	}
	
}