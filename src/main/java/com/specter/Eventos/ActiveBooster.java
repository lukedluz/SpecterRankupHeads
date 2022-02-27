package PombaHeads.Eventos;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import PombaHeads.APIS.BoosterAPI;
import PombaHeads.APIS.TimeAPI;

public class ActiveBooster implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	private void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand() != null && p.getItemInHand().getTypeId() != 0
				&& p.getItemInHand().getType() == Material.EXP_BOTTLE) {

			if (p.getItemInHand().hasItemMeta()) {
				if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6Booster de Heads") && p.getItemInHand().getItemMeta().hasLore()) {
					e.setCancelled(true);
					if (BoosterAPI.IsActive(p) == true) {
						long endOfBan = BoosterAPI.GetTempo(p);
						long now = System.currentTimeMillis();
						long diff = endOfBan - now;
						if (diff > 0) {
							p.sendMessage("§cVocê já possui um booster de " + BoosterAPI.GetBonus(p) + "x ativo");
							p.sendMessage("§cTempo restante: " + TimeAPI.getMSG(endOfBan));
							return;
						} else {
							Double multiplicador = Double.valueOf(p.getItemInHand().getItemMeta().getLore().get(2).replace("§fMultiplicador: §a", ""));
							Double tempo = Double.valueOf(p.getItemInHand().getItemMeta().getLore().get(3).replace("§fTempo: §a", "").replace(" Minutos", ""));
							long end = System.currentTimeMillis() + TimeAPI.getTicks("minutos", tempo.intValue());
							BoosterAPI.RemoveBooster(p);
							BoosterAPI.AddBooster(p, multiplicador, end);
							ItemStack item = p.getItemInHand();
							if (item.getAmount() > 1) {
								item.setAmount(item.getAmount() - 1);
							} else {
								p.setItemInHand(new ItemStack(Material.AIR));
							}
						}
					} else {
						Double multiplicador = Double.valueOf(p.getItemInHand().getItemMeta().getLore().get(2).replace("§fMultiplicador: §a", ""));
						Double tempo = Double.valueOf(p.getItemInHand().getItemMeta().getLore().get(3).replace("§fTempo: §a", "").replace(" Minutos", ""));
						long end = System.currentTimeMillis() + TimeAPI.getTicks("minutos", tempo.intValue());
						BoosterAPI.AddBooster(p, multiplicador, end);
						ItemStack item = p.getItemInHand();
						if (item.getAmount() > 1) {
							item.setAmount(item.getAmount() - 1);
						} else {
							p.setItemInHand(new ItemStack(Material.AIR));
						}
					}
				}
			}
		}
	}
}
