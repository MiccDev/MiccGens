package me.miccdev.miccgens.schedules;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.items.CustomItem;

public class Generator extends Schedule {
	
	private static FileConfiguration config;
	private static Main plugin;
	
	public static void init(Main plugin) {
		Generator.config = Main.generatorData.getConfig();
		Generator.plugin = plugin;
		
		Set<String> worlds = config.getKeys(false);
		
		for(String worldName : worlds) {
			World world = Bukkit.getWorld(worldName);
			initGenerators(world, worldName);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void initGenerators(World world, String lastString) {
		Set<String> generators = config.getConfigurationSection(lastString).getKeys(false);
		for(String generatorType : generators) {
			List<Map<String, Object>> generatorList = (List<Map<String, Object>>) config.getList(lastString + "." + generatorType);
			for(Map<String, Object> generator : generatorList) {
				initGenerator(world, generatorType, generator);
			}
		}
	}
	
	private static void initGenerator(World world, String item, Map<String, Object> generator) {
		Vector vec = getPosition(generator);
		Location position = new Location(world, vec.getX(), vec.getY(), vec.getZ());
		
		String type = item.toLowerCase();
		int count = getCount(generator);
		
		ItemStack spawns;
		if(CustomItem.hasItem(type)) {
			spawns = CustomItem.getItem(type);
			spawns.setAmount(count);
		} else {			
			spawns = new ItemStack(Material.matchMaterial(type), count);
		}
		
		new Generator(
				plugin,
				spawns,
				position, 
				getSpeed(generator)
		);
	}
	
	@SuppressWarnings("unchecked")
	private static Vector getPosition(Map<String, Object> generator) {
		LinkedHashMap<String, Integer> position = (LinkedHashMap<String, Integer>) generator.get("pos");
		int x = position.get("x");
		int y = position.get("y");
		int z = position.get("z");
		
		return new Vector(x, y, z);
	}
	
	private static int getCount(Map<String, Object> generator) {
		return generator.containsKey("count") ? (int) generator.get("count") : 1;
	}
	
	private static long getSpeed(Map<String, Object> generator) {
		return generator.containsKey("speed") ? ((Double) generator.get("speed")).longValue() : 1L;
	}
	
	public static Generator getGeneratorByPosition(int x, int y, int z) {
		return allGenerators.stream().filter(sp -> {
			Location pos = sp.location;
			return ((int) pos.getX()) == x &&
					((int) pos.getY()) == y &&
					((int) pos.getZ()) == z;
		}).collect(Collectors.toList()).get(0);
	}
	
	public static List<Generator> allGenerators = new ArrayList<Generator>();
	
	private ItemStack spawns;
	private Location location;
	
	public Generator(Main plugin, ItemStack spawns) {
		this(plugin, spawns, null);
	}
	
	public Generator(Main plugin, ItemStack spawns, Location location) {
		this(plugin, spawns, location, 1);
	}
	
	public Generator(Main plugin, ItemStack spawns, Location location, float speed) {
		super(plugin, (long) speed * 20);
		this.location = location;
		this.spawns = spawns;
		Generator.allGenerators.add(this);
	}
	
	@Override
	public void run() {
		List<Player> inRange = new ArrayList<Player>();
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!player.getWorld().getName().equals(location.getWorld().getName())) continue;
			if(player.getLocation().distance(location) <= 10) inRange.add(player);
		}
		if(inRange.size() < 1) return;
		
		World world = location.getWorld();
		
		if(location != null) {
			world.dropItem(location, spawns);
		}
	}
	
}
