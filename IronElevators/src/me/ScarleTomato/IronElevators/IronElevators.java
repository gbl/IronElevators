package me.ScarleTomato.IronElevators;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

public class IronElevators extends JavaPlugin {
	public static final Sound WHOOSH = Sound.IRONGOLEM_THROW;
	public static final int MAX_ELEVATION = 14,
							MIN_ELEVATION = 3;
	public static final Material ELEVATOR_MATERIAL = Material.IRON_BLOCK;
	
	EventListener listener;
	
	public void onEnable(){
		//Register event listener
		listener = new EventListener(this);
		getServer().getPluginManager().registerEvents(this.listener, this);
	}
}
