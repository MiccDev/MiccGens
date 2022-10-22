package me.miccdev.miccgens.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandRunnable {
	public boolean run(CommandSender sender, Command command, String label, String[] args);
}
