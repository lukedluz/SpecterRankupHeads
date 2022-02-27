package PombaHeads.APIS;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import PombaHeads.DataBases.DataBase;

public class DataAPI implements Listener {

	public static void CreateAccount(Player p) {
		if (!DataBase.fc.contains(String.valueOf(p.getUniqueId()))) {
			DataBase.fc.set(String.valueOf(p.getUniqueId()) + ".Mobs.ZOMBIE", 0.0);
			DataBase.SaveConfig();
		}
	}

	public static Double getHeads(Player p, String Mob) {
		if (DataBase.fc.getConfigurationSection(p.getUniqueId() + ".Mobs").contains(Mob)
				&& DataBase.fc.contains(p.getUniqueId().toString())) {
			return DataBase.fc.getDouble(String.valueOf(p.getUniqueId()) + ".Mobs." + Mob);
		} else {
			return 0.0;
		}
	}

	public static void addHeads(Player p, Double quantidade, String Mob) {
		DataBase.fc.set(String.valueOf(p.getUniqueId()) + ".Mobs." + Mob, getHeads(p, Mob) + quantidade);
		DataBase.SaveConfig();
	}

	public static void removeHeads(Player p, Double quantidade, String Mob) {
		if (getHeads(p, Mob) - quantidade <= 0) {
			DataBase.fc.set(String.valueOf(p.getUniqueId()) + ".Mobs." + Mob, 0.0);
			DataBase.SaveConfig();
		} else {
			DataBase.fc.set(String.valueOf(p.getUniqueId()) + ".Mobs." + Mob, getHeads(p, Mob) - quantidade);
			DataBase.SaveConfig();
		}
	}

	public static void deleteHeads(Player p) {
		DataBase.fc.set(p.getUniqueId().toString(), null);
		DataBase.SaveConfig();
	}
}
