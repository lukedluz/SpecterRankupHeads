package PombaHeads.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import PombaHeads.APIS.BoosterAPI;
import PombaHeads.APIS.DataAPI;

public class DarHeads implements Listener, CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CommandSender p = (sender);
		if (cmd.getName().equalsIgnoreCase("givehead")) {
			if (args.length != 3) {
				p.sendMessage("§cUse: /givehead (player) (mob) (quantidade)");
				return true;
			}
			if (!p.hasPermission("pomba.givehead")) {
				p.sendMessage("§cVoce nao tem permissão para fazer isso.");
				return true;
			}
			if (BoosterAPI.isDouble(args[2])) {
				if (Bukkit.getPlayer(args[0]) != null) {
					Player t = Bukkit.getPlayer(args[0]);
					DataAPI.addHeads(t, Double.valueOf(args[2]), args[1].toUpperCase());
					return true;
				} else {
					p.sendMessage("§cPlayer Inválido");
				}
			} else {
				p.sendMessage("§cValor Inválido");
			}
		}
		return false;
	}
}
