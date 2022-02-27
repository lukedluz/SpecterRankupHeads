package PombaHeads.APIS;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import PombaHeads.Main;
import PombaHeads.DataBases.Ranks;
import net.rodrigocarvalho.pombaeconomy.PombaEconomy;

public class RankAPI implements Listener {

	public static void CreateAccount(Player p) {
		if (!Ranks.fc.contains(String.valueOf(p.getUniqueId()))) {
			Ranks.fc.set(String.valueOf(p.getUniqueId()) + ".Registrado", "true");
			Ranks.fc.set(String.valueOf(p.getUniqueId()) + ".Rank", "Inicial");
			Ranks.fc.set(String.valueOf(p.getUniqueId()) + ".RankC", "false");
			Ranks.fc.set(String.valueOf(p.getUniqueId()) + ".Prestigios", 0);
			Ranks.SaveConfig();
		}
	}

	public static Double getLooting(Player p) {
		if (p.getItemInHand().getEnchantments().containsKey(Enchantment.LOOT_BONUS_MOBS)) {
			return p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) * 0.2;
		} else {
			return 0.0;
		}
	}

	public static Integer getPrestigio(Player p) {
		return Ranks.fc.getInt(p.getUniqueId() + ".Prestigios");
	}

	public static Integer getProxPrestigio(Player p) {
		return Ranks.fc.getInt(p.getUniqueId() + ".Prestigios") + 1;
	}

	public static void SetPrestigio(Player p, Integer q) {
		Ranks.fc.set(String.valueOf(p.getUniqueId()) + ".Prestigios", q);
		Ranks.SaveConfig();
	}

	public static void AddPrestigio(Player p, Integer q) {
		Ranks.fc.set(String.valueOf(p.getUniqueId()) + ".Prestigios", getPrestigio(p) + q);
		Ranks.SaveConfig();
	}

	public static void ResetAccount(Player p) {
		Ranks.fc.set(String.valueOf(p.getUniqueId()) + ".Registrado", "true");
		Ranks.fc.set(String.valueOf(p.getUniqueId()) + ".Rank", "Inicial");
		Ranks.fc.set(String.valueOf(p.getUniqueId()) + ".RankC", "false");
		DataAPI.deleteHeads(p);
		Ranks.SaveConfig();
		PombaEconomy.getHandler().withdrawPlayer(p, PombaEconomy.getHandler().getBalance(p) - 1);
	}

	public static double getPrestigioPCT(Player p) {
		if (Main.m.getConfig().getConfigurationSection("Prestigios").contains(getPrestigio(p).toString())) {
			return Main.m.getConfig().getDouble("Prestigios." + getPrestigio(p) + ".Porcentagem");
		} else {
			return 0.0;
		}
	}

	public static String getRank(Player p) {
		return Ranks.fc.getString(p.getUniqueId() + ".Rank");
	}

	public static String getRankPrefix(Player p) {
		return Main.m.getConfig().getString("Ranks." + getRank(p) + ".Prefix").replace("&", "§");
	}

	public static String getProxRankPrefix(Player p) {
		return Main.m.getConfig().getString("Ranks." + getProxRank(p) + ".Prefix").replace("&", "§");
	}

	public static String getRankPreco(Player p) {
		if (getPrestigio(p) != 0) {
			String pct = Main.m.getConfig().getString("Prestigios." + RankAPI.getPrestigio(p) + ".Porcentagem");
			String preco = Main.m.getConfig().getString("Ranks." + getRank(p) + ".PrecoUp");
			Double custo = (Double.valueOf(pct) * Double.valueOf(preco)) + Double.valueOf(preco);
			return custo.toString();
		} else {
			return Main.m.getConfig().getString("Ranks." + getRank(p) + ".PrecoUp");
		}
	}

	public static String getRankHead(Player p) {
		return Main.m.getConfig().getString("Ranks." + getRank(p) + ".Head");
	}

	public static String getRankQuantidade(Player p) {
		return Main.m.getConfig().getString("Ranks." + getRank(p) + ".Quantidade");
	}

	public static String getProxRank(Player p) {
		return Main.m.getConfig().getString("Ranks." + getRank(p) + ".ProxRank");
	}

	public static Boolean RankCompleted(Player p) {
		if (Ranks.fc.getString(p.getUniqueId() + ".RankC").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public static void UpRank(Player p) {
		String Rank = getRank(p);
		String ProxRank = Main.m.getConfig().getString("Ranks." + Rank + ".ProxRank");
		String ProxRank2 = Main.m.getConfig().getString("Ranks." + ProxRank + ".ProxRank");

		if (RankCompleted(p))
			return;

		for (String cmd : Main.m.getConfig().getStringList("Ranks." + RankAPI.getRank(p) + ".Comandos")) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					cmd.replace("%p", p.getName()).replace("%nextrank", getProxRank(p)));
		}

		Ranks.fc.set(p.getUniqueId() + ".Rank", ProxRank);
		Ranks.SaveConfig();
		if (ProxRank2.equalsIgnoreCase("Nenhum")) {
			Ranks.fc.set(p.getUniqueId() + ".RankC", "true");
			Ranks.SaveConfig();
		}
	}
	
	public static String getProgressBarAlt(Integer porcentagem) {
		String greenBar = "";
		String redBar = "";
		
		int bars = 10;
		int barPercentage = 100 / bars;
		int percentage = porcentagem;
		
		while(percentage >= barPercentage) {
		    percentage -= barPercentage;
		    greenBar += "▌";
		    bars -= 1;
		}

		while(bars > 0) {
		     redBar += "▌";
		     bars -= 1;
		}
		
		String fullBar = "§a" + greenBar + "§7" + redBar;
		
		return fullBar;
	}

	public static String getProgressBar(int current, int max, int totalBars, String symbol, String completedColor,
			String notCompletedColor) {

		float percent = current / max;

		int progressBars = (int) ((int) totalBars * percent);

		int leftOver = (totalBars - progressBars);

		StringBuilder sb = new StringBuilder();
		sb.append(ChatColor.translateAlternateColorCodes('&', completedColor));
		for (int i = 0; i < progressBars; i++) {
			sb.append(symbol);
		}
		sb.append(ChatColor.translateAlternateColorCodes('&', notCompletedColor));
		for (int i = 0; i < leftOver; i++) {
			sb.append(symbol);
		}
		return sb.toString();
	}
	
	public static String getProgress(Double current, Double max) {
		double percent = current * 100 / max;
		double b = Math.round(percent * 10.0) / 10.0;
		if (b <= 100) {
			return b + "%";
		} else {
			return 100 + "%";
		}
	}

	public static String getProgressHolder(Player p) {

		Double rankq = Double.parseDouble(RankAPI.getRankQuantidade(p));
		Double moneyq = Double.parseDouble(RankAPI.getRankPreco(p));
		Double moneyp = Main.economy.getBalance(p);

		String percentagerank = RankAPI.getProgress(DataAPI.getHeads(p, RankAPI.getRankHead(p)), rankq);
		String percentagemoney = RankAPI.getProgress(moneyp, moneyq);
		Double percentagesomado = Double.valueOf(percentagemoney.replace("%", ""))
				+ Double.valueOf(percentagerank.replace("%", ""));
		Double percentagetotal = percentagesomado / 2;

		String bar = RankAPI.getProgressBarAlt(percentagetotal.intValue());
		String percentage = RankAPI.getProgress(percentagetotal, 100.0);
		String progress = bar + " " + percentage;

		return progress;
	}
}
