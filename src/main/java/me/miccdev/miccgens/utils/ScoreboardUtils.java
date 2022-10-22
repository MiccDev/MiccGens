package me.miccdev.miccgens.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardUtils {
	
	public static Map<UUID, ScoreboardUtils> scoreboards = new HashMap<UUID, ScoreboardUtils>();
	
	private String id;
	private String displayName;
	private Map<String, String> lines;
	private Scoreboard sb;
	private Objective obj;
	
	public ScoreboardUtils(String id, String displayName) {
		this.id = id;
		this.displayName = displayName;
		lines = new HashMap<String, String>();
	}	
	
	public void createScore(String text) {
		lines.put(text, Utils.toColour(text));
	}
	
	public void createScore(String id, String text) {
		lines.put(id, Utils.toColour(text));
	}
	
	public void setSuffix(String id, String suffix) {
		sb.getTeam(id).suffix(Utils.toComponent(suffix));
	}
	
	public void finalizeScoreboard(Player player) {
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = sb.registerNewObjective(id, Criteria.DUMMY, Utils.toComponent(displayName), RenderType.INTEGER);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		int i = 0;
		for(Map.Entry<String, String> entry : lines.entrySet()) {
			String key = entry.getKey();
			String txt = entry.getValue();
			
			Team t = sb.registerNewTeam(key);
			t.addEntry(txt);
			obj.getScore(txt).setScore(i);
			i++;
		}
		
		scoreboards.put(player.getUniqueId(), this);
		player.setScoreboard(sb);
	}
	
	public void setScore(String id, String now) {
		lines.put(id, now);
	}
	
	public void setDisplayName(String display) {
		obj.displayName(Utils.toComponent(display));
	}
	
}
