package me.miccdev.miccgens;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.PlaceholderAPI;
import me.miccdev.miccgens.commands.CustomCommand;
import me.miccdev.miccgens.events.CustomEvent;
import me.miccdev.miccgens.events.PlayerJoin;
import me.miccdev.miccgens.guis.GUI;
import me.miccdev.miccgens.items.CustomItem;
import me.miccdev.miccgens.schedules.Generator;
import me.miccdev.miccgens.utils.DataManager;
import me.miccdev.miccgens.utils.ScoreboardUtils;
import me.miccdev.miccgens.utils.Utils;

public class Main extends JavaPlugin {

	public static DataManager generatorData;
	public static DataManager playerData;
	public static DataManager itemData;
	
	public List<String> itemList;
	
	@Override
	public void onEnable() {
		getLogger().info("Enabling main module");
		
		getLogger().info("Obtaining config data.");
		Main.itemData = new DataManager(this, "items");
		Main.playerData = new DataManager(this, "player-data");
		Main.generatorData = new DataManager(this, "generators");
		saveDefaultConfig();
		getLogger().info("Finished obtaining config data.");
		
		getLogger().info("Enabling 'items' module");
		CustomItem.init();
		
		getLogger().info("Enabling 'spawners' module");
		Generator.init(this);
		
		getLogger().info("Enabling 'guis' module");
		GUI.init();
		
		getLogger().info("Enabling 'commands' module");
		CustomCommand.init(this);
		
		getLogger().info("Enabling 'events' module");
		CustomEvent.init(this);
		
		getLogger().info("Done!");
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				
			int count = 0;
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					ScoreboardUtils scoreboard = ScoreboardUtils.scoreboards.get(p.getUniqueId());
					if(count == 12)
						count = 0;
					
					double kd = p.getStatistic(Statistic.PLAYER_KILLS) / p.getStatistic(Statistic.DEATHS);
					String world = Utils.WORLDS.get(p.getWorld().getName());
					String rank = PlaceholderAPI.setPlaceholders(p, "%luckperms_prefix%");
					
					scoreboard.setSuffix("rank", rank);
					scoreboard.setSuffix("kd", "&3" + Utils.round(kd, 1));
					scoreboard.setSuffix("players", "&3" + Bukkit.getOnlinePlayers().size() + "&7/&3" + Bukkit.getMaxPlayers());
					scoreboard.setSuffix("world", "&3" + world);
					
					String text = Utils.TITLE;
					
					switch(count) {
					case 0:
						text = "&b&lM&l&3iccGens";
						break;
					case 1:
						text = "&3&lM&b&li&3&lccGens";
						break;
					case 2:
						text = "&3&lMi&b&lc&3&lcGens";
						break;
					case 3:
						text = "&3&lMic&b&lc&3&lGens";
						break;
					case 4:
						text = "&3&lMicc&b&lG&3&lens";
						break;
					case 5:
						text = "&3&lMiccG&b&le&3&lns";
						break;
					case 6:
						text = "&3&lMiccGe&b&ln&3&ls";
						break;
					case 7:
						text = "&3&lMiccGen&b&ls";
						break;
					case 8:
						text = "&3&lMiccGens";
						break;
					case 9:
						text = "&b&lMiccGens";
						break;
					case 10:
						text = "&3&lMiccGens";
						break;
					case 11:
						text = "&b&lMiccGens";
						break;
					}
					
					scoreboard.setDisplayName(text);
					
					p.sendPlayerListHeader(Utils.toComponent(text + "\n&8------------------"));
					p.sendPlayerListFooter(Utils.toComponent("&8------------------\n" + "&7Players: &b" + Bukkit.getOnlinePlayers().size() + "&7/&b" + Bukkit.getMaxPlayers()));
					p.playerListName(Utils.toComponent(rank + p.getName()));
				}
				count++;
			}
			
		}, 0, 10);
		
		if(!Bukkit.getOnlinePlayers().isEmpty()) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				PlayerJoin.createScoreboard(this, player);
			}
		}
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Disabling all modules!");
	}
	
}
