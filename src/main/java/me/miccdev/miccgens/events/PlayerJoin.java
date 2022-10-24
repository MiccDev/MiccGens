package me.miccdev.miccgens.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.items.CustomItem;
import me.miccdev.miccgens.utils.AnimatedBoard;
import me.miccdev.miccgens.utils.Utils;

public class PlayerJoin extends CustomEvent {
	
	public PlayerJoin(Main plugin) {
		super(plugin);
	}

	@EventHandler	
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Location position = new Location(Bukkit.getWorld("world"), 53.5, -43, -19.5, -88.8f, 0.9f);
		player.teleport(position);
		
		player.setGameMode(GameMode.ADVENTURE);

		event.joinMessage(Utils.toComponent(Utils.Prefix + " &r&bWelcome, " + player.getName() + " &bto the server!"));
		
		player.getInventory().setItem(8, CustomItem.getItem("menu"));
		
		createTablist(player);
		createScoreboard(getPlugin(), player);
	}
	
	private void createTablist(Player player) {
		player.sendPlayerListHeader(Utils.toComponent(Utils.TITLE + "\n&8------------------"));
		player.sendPlayerListFooter(Utils.toComponent("&8------------------\n" + "&7Players: &b" + Bukkit.getOnlinePlayers().size() + "&7/&b" + Bukkit.getMaxPlayers()));
	}
	
	public static void createScoreboard(Main plugin, Player player) {
		AnimatedBoard scoreboard = new AnimatedBoard(plugin, player.getUniqueId(), "miccgens", Utils.TITLE);
		
		scoreboard.createScore("rank", "  &3| &7Rank: ");
		scoreboard.createScore("kd", "  &3| &7KD: ");
		scoreboard.createScore("players", "  &3| &7Players: ");
		scoreboard.createScore("world", "  &3| &7World: ");
		scoreboard.createScore("  ", "  &b&omiccgens.com");
		
		scoreboard.finalizeScoreboard(player);
	}
	
}
