package PombaHeads.Eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import PombaHeads.APIS.ActionBar;
import PombaHeads.APIS.BoosterAPI;
import PombaHeads.APIS.DataAPI;
import PombaHeads.APIS.FormatAPI;
import PombaHeads.APIS.RankAPI;

public class Events implements Listener {

	@EventHandler
	public void onjoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		RankAPI.CreateAccount(p);
		DataAPI.CreateAccount(p);
	}

	@EventHandler
	public void onKill(EntityDeathEvent e) {
		double stack = 0.0;
		double booster = 0.0;
		double total = 0.0;
		double inicial = 0.0;
		if (e.getEntity() instanceof Player)
			return;

		if (!(e.getEntity().getKiller() instanceof Player))
			return;

		Player p = e.getEntity().getKiller();
		if (e.getEntity().hasMetadata("atlas_spawners_quantidade")) {
			if (e.getEntity().getMetadata("atlas_spawners_quantidade") != null) {
				if (!e.getEntity().getMetadata("atlas_spawners_quantidade").isEmpty()) {
					if (e.getEntity().getMetadata("atlas_spawners_quantidade").get(0) != null) {
						stack = e.getEntity().getMetadata("atlas_spawners_quantidade").get(0).asDouble();
					}
				}
			}
		}
		if (BoosterAPI.IsActive(p)) {
			booster = booster + Double.valueOf(BoosterAPI.GetBonus(p));
		}
		if (BoosterAPI.IsActiveGlobal()) {
			booster = booster + Double.valueOf(BoosterAPI.GetBonusGlobal());
		}
		if (RankAPI.getLooting(p) > 0.0) {
			inicial = stack + (stack * RankAPI.getLooting(p));
		} else {
			inicial = stack;
		}
		Double prestigio = RankAPI.getPrestigioPCT(p);
		if (prestigio >= 1.0) {
			total = inicial * (prestigio * 0.25) * booster;
		} else {
			total = inicial * booster;
		}
		DataAPI.addHeads(p, total, e.getEntity().getType().toString().toUpperCase());
		ActionBar.sendActionBarMessage(p, "§7Você recebeu §a" + FormatAPI.format(total) + " §7heads de "
				+ e.getEntity().getType().toString().toLowerCase());
	}
}
