package PombaHeads.DataBases;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import PombaHeads.Main;

public class BoosterDb {

	static File f;
	public static FileConfiguration fc;
	static BoosterDb m;

	public static void create() {
		f = new File(Main.m.getDataFolder() + "/databases/booster.db");
		fc = YamlConfiguration.loadConfiguration(f);
	}

	public static void SaveConfig() {
		try {
			fc.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BoosterDb config() {
		return m;
	}
	

}