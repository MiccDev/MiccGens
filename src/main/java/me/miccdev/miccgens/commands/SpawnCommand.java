package me.miccdev.miccgens.commands;

import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.utils.Utils;

public class SpawnCommand extends CustomCommand {

	public SpawnCommand(Main plugin) {
		super(plugin, "spawn");
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Utils.Prefix + Utils.toColour(" &4Only players can use this command!"));
			return true;
		}
		
		Player plr = (Player) sender;
		World world = plr.getWorld();
		
		plr.teleport(world.getSpawnLocation());
		
		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String label, @NotNull String[] args) {
		return null;
	}
	
}
