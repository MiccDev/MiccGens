package me.miccdev.miccgens.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

import me.miccdev.miccgens.Main;

public abstract class CustomCommand implements CommandExecutor, TabCompleter {

	public static void init(Main plugin) {
		new GeneratorCommand(plugin);
		new MGItemCommand(plugin);
		new SpawnCommand(plugin);
	}
	
	private final Main plugin;
	private String name;
	
	public CustomCommand(Main plugin, String name) {
		this.plugin = plugin;
		this.name = name;
		plugin.getCommand(name).setExecutor(this);
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
