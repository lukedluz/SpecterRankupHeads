package PombaHeads.APIS;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import PombaHeads.Main;
import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;

public class PlaceHolder implements Listener {

	public static boolean registerRank() {
		if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") == null) {
			return false;
		}
		PlaceholderAPI.registerPlaceholder(Main.getInstance(), "pombarank",
				(PlaceholderReplacer) new PlaceholderReplacer() {
					public String onPlaceholderReplace(final PlaceholderReplaceEvent e) {
						final Player player = e.getPlayer();
						if (player == null) {
							return "";
						}
						Main.getInstance();
						return RankAPI.getRankPrefix(player);
					}
				});
		return true;
	}

	public static boolean registerNextRank() {
		if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") == null) {
			return false;
		}
		PlaceholderAPI.registerPlaceholder(Main.getInstance(), "pombanextrank",
				(PlaceholderReplacer) new PlaceholderReplacer() {
					public String onPlaceholderReplace(final PlaceholderReplaceEvent e) {
						final Player player = e.getPlayer();
						if (player == null) {
							return "";
						}
						Main.getInstance();
						if (!RankAPI.getProxRank(player).equalsIgnoreCase("Nenhum")) {
							return RankAPI.getProxRankPrefix(player);
						} else {
							return "§7Nenhum";
						}
					}
				});
		return true;
	}

	public static boolean registerPrestigio() {
		if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") == null) {
			return false;
		}
		PlaceholderAPI.registerPlaceholder(Main.getInstance(), "pombaprestigio",
				(PlaceholderReplacer) new PlaceholderReplacer() {
					public String onPlaceholderReplace(final PlaceholderReplaceEvent e) {
						final Player player = e.getPlayer();
						if (player == null) {
							return "";
						}
						Main.getInstance();
						return String.valueOf(RankAPI.getPrestigio(player));
					}
				});
		return true;
	}

	public static boolean registerProgresso() {
		if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") == null) {
			return false;
		}
		PlaceholderAPI.registerPlaceholder(Main.getInstance(), "pombaprogressoheads",
				(PlaceholderReplacer) new PlaceholderReplacer() {
					public String onPlaceholderReplace(final PlaceholderReplaceEvent e) {
						final Player player = e.getPlayer();
						if (player == null) {
							return "";
						}
						Main.getInstance();
						return RankAPI.getProgressHolder(player);
					}
				});
		return true;
	}

	public static boolean registerHeads() {
		if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") == null) {
			return false;
		}
		PlaceholderAPI.registerPlaceholder(Main.getInstance(), "pombaheads",
				(PlaceholderReplacer) new PlaceholderReplacer() {
					public String onPlaceholderReplace(final PlaceholderReplaceEvent e) {
						final Player player = e.getPlayer();
						if (player == null) {
							return "";
						}
						Main.getInstance();
						if (!(DataAPI.getHeads(player, RankAPI.getRankHead(player)) >= 1.0)) {
							return FormatAPI.format(0.0);
						} else {
							return FormatAPI.format(DataAPI.getHeads(player, RankAPI.getRankHead(player)));
						}
					}
				});
		return true;
	}

	public static boolean registerBooster() {
		if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") == null) {
			return false;
		}
		PlaceholderAPI.registerPlaceholder(Main.getInstance(), "pombabooster",
				(PlaceholderReplacer) new PlaceholderReplacer() {
					public String onPlaceholderReplace(final PlaceholderReplaceEvent e) {
						final Player player = e.getPlayer();
						if (player == null) {
							return "";
						}
						Main.getInstance();
						if (BoosterAPI.IsActiveGlobal()) {
							long endOfBan = BoosterAPI.GetTempoGlobal();
							long now = System.currentTimeMillis();
							long diff = endOfBan - now;
							if (diff > 0) {
								return " §fGlobal: §a" + BoosterAPI.GetBonusGlobal() + "x (" + TimeAPI.getMSG2(endOfBan)
										+ ")";
							} else {
								BoosterAPI.DesativarGlobal();
								if (BoosterAPI.IsActive(player)) {
									long endOfBan2 = BoosterAPI.GetTempo(player);
									long now2 = System.currentTimeMillis();
									long diff2 = endOfBan2 - now2;
									if (diff2 > 0) {
										return " §fBooster: §a" + BoosterAPI.GetBonus(player) + "x ("
												+ TimeAPI.getMSG2(endOfBan2) + ")";
									} else {
										return " §cNenhum booster ativo";
									}
								}
							}
						} else if (BoosterAPI.IsActive(player)) {
							long endOfBan2 = BoosterAPI.GetTempo(player);
							long now2 = System.currentTimeMillis();
							long diff2 = endOfBan2 - now2;
							if (diff2 > 0) {
								return " §fBooster: §a" + BoosterAPI.GetBonus(player) + "x ("
										+ TimeAPI.getMSG2(endOfBan2) + ")";
							} else {
								return " §cNenhum booster ativo";
							}
						} else {
							return " §cNenhum booster ativo";
						}
						return " §cNenhum booster ativo";
					}
				});
		return true;
	}

	@EventHandler
	public void onChat(ChatMessageEvent e) {
		Player p = e.getSender();
		if (e.getTags().contains("pomba_rank")) {
			e.setTagValue("pomba_rank", RankAPI.getRankPrefix(p));
		}
		if (e.getTags().contains("pomba_prestigio")) {
			e.setTagValue("pomba_prestigio", String.valueOf(RankAPI.getPrestigio(p)));
		}
	}
}
