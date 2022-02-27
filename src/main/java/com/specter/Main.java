package PombaHeads;

import java.text.NumberFormat;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import PombaHeads.APIS.BoosterAPI;
import PombaHeads.APIS.DataAPI;
import PombaHeads.APIS.FormatAPI;
import PombaHeads.APIS.MenuAPI;
import PombaHeads.APIS.PlaceHolder;
import PombaHeads.APIS.RankAPI;
import PombaHeads.Comandos.Booster;
import PombaHeads.Comandos.DarBooster;
import PombaHeads.Comandos.DarHeads;
import PombaHeads.Comandos.Prestigio;
import PombaHeads.Comandos.Prestigios;
import PombaHeads.Comandos.Ranks;
import PombaHeads.Comandos.Rankup;
import PombaHeads.Comandos.StopBooster;
import PombaHeads.DataBases.BoosterDb;
import PombaHeads.DataBases.DataBase;
import PombaHeads.Eventos.ActiveBooster;
import PombaHeads.Eventos.Events;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	public static Main m;
	public static Economy economy;

	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		m = this;
		
		PombaHeads.DataBases.Ranks.create();
		PombaHeads.DataBases.Ranks.SaveConfig();
		DataBase.create();
		DataBase.SaveConfig();
		BoosterDb.create();
		BoosterDb.SaveConfig();
		
		setupEconomy();
		
		PlaceHolder.registerRank();
		PlaceHolder.registerNextRank();
		PlaceHolder.registerPrestigio();
		PlaceHolder.registerProgresso();
		PlaceHolder.registerHeads();
		PlaceHolder.registerBooster();
		
		Bukkit.getPluginManager().registerEvents(new PlaceHolder(), this);
		Bukkit.getPluginManager().registerEvents(new DataAPI(), this);
		Bukkit.getPluginManager().registerEvents(new RankAPI(), this);
		Bukkit.getPluginManager().registerEvents(new FormatAPI(), this);
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		Bukkit.getPluginManager().registerEvents(new Ranks(), this);
		Bukkit.getPluginManager().registerEvents(new Rankup(), this);
		Bukkit.getPluginManager().registerEvents(new Prestigio(), this);
		Bukkit.getPluginManager().registerEvents(new Prestigios(), this);
		Bukkit.getPluginManager().registerEvents(new MenuAPI(), this);
		Bukkit.getPluginManager().registerEvents(new BoosterAPI(), this);
		Bukkit.getPluginManager().registerEvents(new ActiveBooster(), this);
		Bukkit.getPluginManager().registerEvents(new DarHeads(), this);
		getCommand("ranks").setExecutor(new Ranks());
		getCommand("prestigio").setExecutor(new Prestigio());
		getCommand("prestigios").setExecutor(new Prestigios());
		getCommand("rankup").setExecutor(new Rankup());
		getCommand("booster").setExecutor(new Booster());
		getCommand("darbooster").setExecutor(new DarBooster());
		getCommand("stopbooster").setExecutor(new StopBooster());
		getCommand("givehead").setExecutor(new DarHeads());

	}
	
	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static String format(final double valor) {
		final NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
		format.setMaximumFractionDigits(1);
		return format.format(valor);
	}
	
	public static Main getInstance() {
		return Main.m;
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
			return true;
		} else {
			return false;
		}
	}
}
