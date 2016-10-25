package me.ScarleTomato.IronElevators;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class IronElevators extends JavaPlugin {
	public static final int FILE_COPY_MAX_BYTE_SIZE = 4096;
	
	//default config values
	public int maxElevation = 14, minElevation = 3;
	public Material elevatorMaterial = Material.IRON_BLOCK;
	public Sound elevatorWhoosh = Sound.ENTITY_IRONGOLEM_ATTACK;
	
	EventListener listener;
	FileConfiguration config;
	File configFile;
	
	public void onEnable(){
		//load config
		loadConfig();
		getConfigValues();
		
		//Register event listener
		listener = new EventListener(this);
		getServer().getPluginManager().registerEvents(this.listener, this);
	}
	
	void getConfigValues(){
		//integer values
		maxElevation = config.getInt("maxElevation");
		minElevation = config.getInt("minElevation");
		elevatorMaterial = Material.valueOf(config.getString("elevatorMaterial"));
		elevatorWhoosh = Sound.valueOf(config.getString("elevatorWhoosh"));
	}
	
	void loadConfig(){
		try{
			config = new YamlConfiguration();
			configFile = new File(getDataFolder(), "config.yml");
			if(!configFile.exists()){
				configFile.getParentFile().mkdirs();
				//copy(getResource("config.yml"), configFile);
				
				//build config from defaults
				config.set("minElevation", minElevation);
				config.set("maxElevation", maxElevation);
				config.set("elevatorMaterial", elevatorMaterial.toString());
				config.set("elevatorWhoosh", elevatorWhoosh.toString());
				config.save(configFile);
			}
			config.load(configFile);
		}catch(Exception e){
			Bukkit.getLogger().log(Level.WARNING, "{0}Exception {1}when loading configuration file.\n{2}", new Object[]{ChatColor.RED, Color.white, e.getMessage()});
		}
	}
	
	public static void copy(InputStream in, File file){
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[FILE_COPY_MAX_BYTE_SIZE];
			int len;
			while((len=in.read(buf))>0){
				out.write(buf,0,len);
			}
			out.close();
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
