package me.miccdev.miccgens.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.utils.AnimatedBoard;
import me.miccdev.miccgens.utils.Utils;
import net.kyori.adventure.text.Component;

public class PlayerLeave extends CustomEvent {

	public PlayerLeave(Main plugin) {
		super(plugin);
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.quitMessage(Component.text(Utils.toColour("&e[&3&lMiccGens&r&e]: &r&bGoodbye, " + player.getName() + "&b!")));
		
		if(AnimatedBoard.hasID(player.getUniqueId())) 
			AnimatedBoard.stopTask(player.getUniqueId());
	}
	
}
