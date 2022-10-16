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

public class Spawner extends Schedule {
	
	private static FileConfiguration config;
	private static Main plugin;
	
	public static void init(Main plugin, FileConfiguration config) {
		Spawner.config = config;
		Spawner.plugin = plugin;
		
		Set<String> worlds = config.getKeys(false);
		
		for(String worldName : worlds) {
			World world = Bukkit.getWorld(worldName);
			initSpawners(world, worldName);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void initSpawners(World world, String lastString) {
//		initCoalSpawners(world, config);
		
		Set<String> spawners = config.getConfigurationSection(lastString).getKeys(false);
		for(String spawnerType : spawners) {
			List<Map<String, Object>> spawnerList = (List<Map<String, Object>>) config.getList(lastString + "." + spawnerType);
			for(Map<String, Object> spawner : spawnerList) {
				initSpawner(world, spawnerType, spawner);
			}
		}
	}
	
	private static void initSpawner(World world, String item, Map<String, Object> spawner) {
		Vector vec = getPosition(spawner);
		Location position = new Location(world, vec.getX(), vec.getY(), vec.getZ());
		
		String type = item.toLowerCase();
		int count = getCount(spawner);
		
		ItemStack spawns;
		if(CustomItem.hasItem(type)) {
			spawns = CustomItem.getItem(type);
			spawns.setAmount(count);
		} else {			
			spawns = new ItemStack(Material.matchMaterial(type), count);
		}
		
		new Spawner(
				plugin,
				spawns,
				position, 
				getSpeed(spawner)
		);
	}
	
	@SuppressWarnings("unchecked")
	private static Vector getPosition(Map<String, Object> spawner) {
		LinkedHashMap<String, Integer> position = (LinkedHashMap<String, Integer>) spawner.get("pos");
		int x = position.get("x");
		int y = position.get("y");
		int z = position.get("z");
		
		return new Vector(x, y, z);
	}
	
	private static int getCount(Map<String, Object> spawner) {
		return spawner.containsKey("count") ? (int) spawner.get("count") : 1;
	}
	
	private static long getSpeed(Map<String, Object> spawner) {
		return spawner.containsKey("speed") ? ((Double) spawner.get("speed")).longValue() : 1L;
	}
	
	public static Spawner getSpawnerByPosition(int x, int y, int z) {
		return allSpawners.stream().filter(sp -> {
			Location pos = sp.location;
			return ((int) pos.getX()) == x &&
					((int) pos.getY()) == y &&
					((int) pos.getZ()) == z;
		}).collect(Collectors.toList()).get(0);
	}
	
	public static List<Spawner> allSpawners = new ArrayList<Spawner>();
	
	private ItemStack spawns;
	private Location location;
	
	public Spawner(Main plugin, ItemStack spawns) {
		this(plugin, spawns, null);
	}
	
	public Spawner(Main plugin, ItemStack spawns, Location location) {
		this(plugin, spawns, location, 1);
	}
	
	public Spawner(Main plugin, ItemStack spawns, Location location, float speed) {
		super(plugin, (long) speed * 20);
		this.location = location;
		this.spawns = spawns;
		Spawner.allSpawners.add(this);
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
			world.dropItemNaturally(location, spawns);
		}
	}
	
}
