package me.miccdev.miccgens.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.items.CustomItem;
import me.miccdev.miccgens.schedules.Generator;
import me.miccdev.miccgens.utils.Utils;

public class GeneratorCommand extends CustomCommand {

	public GeneratorCommand(Main plugin) {
		super(plugin, "generator");
	}

	// /generator <type> <speed> [count=1]
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Utils.Prefix + Utils.toColour(" &4Only players can use this command!"));
			return true;
		}
		
		Player plr = (Player) sender;
		
		if(!plr.hasPermission("miccgens.gens")) {
			plr.sendMessage(Utils.Prefix + Utils.toColour(" &4Only staff members can create generators."));
			plr.playSound(plr, Sound.ENTITY_VILLAGER_NO, 0.5f, 1);
			return true;
		}
		
		switch(args[0]) {
			case "create":
				return create(plr, args);
			case "remove":
				return remove(plr, args);
			default:
				plr.sendMessage(Utils.Prefix + Utils.toColour(" &4Expected 'create' or 'remove'."));
				plr.playSound(plr, Sound.ENTITY_VILLAGER_NO, 0.5f, 1);
				return false;
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	public boolean remove(Player plr, String[] args) {
		if(args.length < 2) {
			plr.sendMessage(Utils.Prefix + Utils.toColour(" &4Expected 1 arguments"));
			plr.playSound(plr, Sound.ENTITY_VILLAGER_NO, 0.5f, 1);
			return true;
		}
		
		FileConfiguration config = Main.generatorData.getConfig();
		
		String world = plr.getWorld().getName();
		Location position = plr.getLocation();
		String type = args[1];
		
		List<Map<String, Object>> spawnerList = (List<Map<String, Object>>) config.getList(world + "." + type);
		
		List<Map<String, Object>> selected = spawnerList.stream().filter(map -> {
			HashMap<String, Integer> spawnerPos = (HashMap<String, Integer>) map.get("pos");
			
			return ((int) position.getX()) == spawnerPos.get("x") && 
					((int) position.getY()) == spawnerPos.get("y") && 
					((int) position.getZ()) == spawnerPos.get("z");
		}).collect(Collectors.toList());
		
		if(spawnerList.size() < 1) {
			plr.sendMessage(Utils.Prefix + Utils.toColour(" &4There is no spawner at your current location."));
			plr.playSound(plr, Sound.ENTITY_VILLAGER_NO, 0.5f, 1);
			return true;
		}
		
		spawnerList.remove(selected.get(0));
		
		int id = Generator.getGeneratorByPosition(
				(int) position.getX(), 
				(int) position.getY(), 
				(int) position.getZ()
		).getTaskId();
		
		Bukkit.getScheduler().cancelTask(id);
		
		plr.sendMessage(Utils.Prefix + Utils.toColour(" &bSuccessfully removed spawner."));
		
		config.set(world + "." + type, spawnerList);
		Main.generatorData.saveConfig();
		return false;
	}
	
	@SuppressWarnings({ "serial", "unchecked" })
	private boolean create(Player plr, String[] args) {
		if(args.length < 3) {
			plr.sendMessage(Utils.Prefix + Utils.toColour(" &4Expected 2 arguments"));
			plr.playSound(plr, Sound.ENTITY_VILLAGER_NO, 0.5f, 1);
			return true;
		}
		
		FileConfiguration config = Main.generatorData.getConfig();
		
		String world = plr.getWorld().getName();
		Location position = plr.getLocation();
		String type = args[1].toLowerCase();
		if(!Utils.isNumeric(args[2])) {
			plr.sendMessage(Utils.Prefix + Utils.toColour(" &4Expected a number."));
			plr.playSound(plr, Sound.ENTITY_VILLAGER_NO, 0.5f, 1);
			return true;
		}
		float speed = Float.parseFloat(args[2]);
		int count = args.length > 4 ? Integer.parseInt(args[3]) : 1;
		
		ItemStack spawns;
		
		if(CustomItem.hasItem(type)) {
			spawns = CustomItem.getItem(type);
			spawns.setAmount(count);
		} else {
			if(Material.matchMaterial(type) == null) {
				plr.sendMessage(Utils.Prefix + Utils.toColour(" &4Item '" + type + "' does not exist."));
				plr.playSound(plr, Sound.ENTITY_VILLAGER_NO, 0.5f, 1);
				return true;
			}
			
			spawns = new ItemStack(Material.matchMaterial(type), count);
		}
		
		List<Map<String, Object>> spawnerList = (List<Map<String, Object>>) config.getList(world + "." + type);
		
		if(spawnerList == null) {
			config.set(world + "." + type, new ArrayList<Map<String, Object>>());
			spawnerList = (List<Map<String, Object>>) config.getList(world + "." + type);
		}
		
		Map<String, Object> generator = new HashMap<String, Object>();
		
		generator.put("pos", new HashMap<String, Integer>() {
			{ 
				put("x", (int) position.getX()); 
				put("y", (int) position.getY()); 
				put("z", (int) position.getZ()); 
			}
		});
		generator.put("speed", speed);
		if(count != -1) generator.put("count", count);
		spawnerList.add(generator);
		
		config.set(world + "." + type, spawnerList);

		new Generator(
				getPlugin(),
				spawns, 
				position,
				speed
		);
		
		plr.sendMessage(Utils.Prefix + Utils.toColour(" &bSuccessfully created a generator of type '" + type + "'."));
		
		Main.generatorData.saveConfig();
		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String label, @NotNull String[] args) {
//		if(args.length == 1) {
//			return Arrays.asList("create", "remove");
//		} else if(args.length == 2) {
//			if(args[1].length() > 0) {
//				return getPlugin().itemList.stream().filter(m -> m.contains(args[1])).collect(Collectors.toList());
//			}
//			return getPlugin().itemList;
//
//		}
		return null;
	}
	
}
