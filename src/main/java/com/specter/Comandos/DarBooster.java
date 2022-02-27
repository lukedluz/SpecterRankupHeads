package PombaHeads.Comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import PombaHeads.APIS.BoosterAPI;
import PombaHeads.APIS.TimeAPI;

public class DarBooster implements CommandExecutor, Listener {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CommandSender p = (sender);
		if (cmd.getName().equalsIgnoreCase("darbooster")) {
			if (args.length != 3) {
				p.sendMessage("§cUse: /darbooster (player/global) (tempo) (bonus)");
				return true;
			}
			if (!p.hasPermission("pomba.booster")) {
				p.sendMessage("§cVoce nao tem permissão para fazer isso.");
				return true;
			}
			if (BoosterAPI.isDouble(args[1]) && BoosterAPI.isDouble(args[2])) {
				if (args[0].equalsIgnoreCase("global")) {
					if (BoosterAPI.IsActiveGlobal() == false) {
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("§aBooster Global ativo por §f" + args[1] + " §aminutos");
						Bukkit.broadcastMessage("");
						long end = System.currentTimeMillis() + TimeAPI.getTicks("minutos", Integer.parseInt(args[1]));
						BoosterAPI.AtivarGlobal(Double.valueOf(args[2]), end);
					} else {
						p.sendMessage("§cO booster global já está ativo");
					}
				} else {
					if (Bukkit.getPlayer(args[0]) != null) {
						Player target = Bukkit.getPlayer(args[0]);
						ItemStack booster = new ItemStack(Material.EXP_BOTTLE);
						ItemMeta boosterMeta = booster.getItemMeta();
						boosterMeta.setDisplayName("§6Booster de Heads");
						List<String> boosterLore = new ArrayList<String>();
						boosterLore.add("§aClique nesse item para ativar o booster de Heads");
						boosterLore.add("");
						boosterLore.add("§fMultiplicador: §a" + args[2]);
						boosterLore.add("§fTempo: §a" + args[1] + " Minutos");
						boosterMeta.setLore(boosterLore);
						booster.setItemMeta(boosterMeta);
						target.getInventory().addItem(booster);
						return true;
					} else {
						p.sendMessage("§cPlayer Inválido");
					}
				}
			} else {
				p.sendMessage("§cValor Inválido");
			}
		}
		return false;
	}
}
