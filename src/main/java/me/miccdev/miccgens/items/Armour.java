package me.miccdev.miccgens.items;

import org.bukkit.Material;

public class Armour extends CustomItem {

	private float def;
	private float toughness;
	
	public Armour(String id, Material material, float def, float toughness) {
		super(id, material, CustomItemType.ARMOUR, false);
		this.def = def;
		this.toughness = toughness;
	}

	public float getDef() {
		return def;
	}

	public void setDef(float def) {
		this.def = def;
	}

	public float getToughness() {
		return toughness;
	}

	public void setToughness(float toughness) {
		this.toughness = toughness;
	}
	
}
