package PombaHeads.APIS;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import PombaHeads.DataBases.BoosterDb;

public class BoosterAPI implements Listener {

	public static void AddBooster(Player p, Double bonus, Long tempo) {
		BoosterDb.fc.set(p.getUniqueId() + ".Booster", true);
		BoosterDb.fc.set(p.getUniqueId() + ".Bonus", bonus);
		BoosterDb.fc.set(p.getUniqueId() + ".Tempo", tempo);
		BoosterDb.SaveConfig();
	}

	public static void RemoveBooster(Player p) {
		BoosterDb.fc.set(p.getUniqueId().toString(), null);
		BoosterDb.SaveConfig();
	}

	public static String GetBonus(Player p) {
		return BoosterDb.fc.getString(p.getUniqueId() + ".Bonus");
	}

	public static Long GetTempo(Player p) {
		return BoosterDb.fc.getLong(p.getUniqueId() + ".Tempo");
	}

	public static Boolean IsActive(Player p) {
		if (BoosterDb.fc.getBoolean(p.getUniqueId() + ".Booster") == true) {
			return true;
		} else {
			return false;
		}
	}

	public static void AtivarGlobal(Double bonus, Long tempo) {
		BoosterDb.fc.set("Global.Booster", true);
		BoosterDb.fc.set("Global.Bonus", bonus);
		BoosterDb.fc.set("Global.Tempo", tempo);
		BoosterDb.SaveConfig();
	}

	public static void DesativarGlobal() {
		BoosterDb.fc.set("Global", null);
		BoosterDb.SaveConfig();
	}

	public static String GetBonusGlobal() {
		return BoosterDb.fc.getString("Global.Bonus");
	}

	public static Long GetTempoGlobal() {
		return BoosterDb.fc.getLong("Global.Tempo");
	}

	public static Boolean IsActiveGlobal() {
		if (BoosterDb.fc.getBoolean("Global.Booster") == true) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
