package me.miccdev.miccgens.commands;

import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.utils.Utils;

public abstract class CustomCommand implements CommandExecutor, TabCompleter {

	public static void init(Main plugin) {
		new GeneratorCommand(plugin);
		new MGItemCommand(plugin);
		
		createCommand(plugin, "tutorial", new CommandRunnable() {
			@Override
			public boolean run(CommandSender sender, Command command, String label, String[] args) {
				
				return false;
			}
		});
		
		createCommand(plugin, "spawn", new CommandRunnable() {
			@Override
			public boolean run(CommandSender sender, Command command, String label, String[] args) {
				if(!(sender instanceof Player)) {
					sender.sendMessage(Utils.Prefix + Utils.toColour(" &4Only players can use this command!"));
					return true;
				}
				
				Player plr = (Player) sender;
				World world = plr.getWorld();
				
				plr.teleport(world.getSpawnLocation());
				
				return false;
			}
		});
	}
	
	public static void createCommand(Main plugin, String name, CommandRunnable commandRunner) {
		class CCommand extends CustomCommand {

			public CCommand(Main plugin) {
				super(plugin, name);
			}

			@Override
			public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command,
					@NotNull String label, @NotNull String[] args) {
				return commandRunner.run(sender, command, label, args);
			}

			@Override
			public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
					@NotNull Command command, @NotNull String label, @NotNull String[] args) {
				return null;
			}
			
		}
		
		new CCommand(plugin);
	}
	
	private final Main plugin;
	private String name;
	
	public CustomCommand(Main plugin, String name) {
		this.plugin = plugin;
		this.name = name;
		plugin.getCommand(name).setExecutor(this);
		plugin.getCommand(name).setTabCompleter(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Main getPlugin() {
		return plugin;
	}
	
}
