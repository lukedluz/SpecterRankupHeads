package PombaHeads.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import PombaHeads.APIS.BoosterAPI;

public class StopBooster implements CommandExecutor, Listener {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CommandSender p = (sender);
		if (cmd.getName().equalsIgnoreCase("stopbooster")) {
			if (args.length != 1) {
				p.sendMessage("§cUse: /stopbooster (player/global)");
				return true;
			}
			if (!p.hasPermission("pomba.booster")) {
				p.sendMessage("§cVoce nao tem permissão para fazer isso.");
				return true;
			}
			if (args[0].equalsIgnoreCase("global")) {
				if (BoosterAPI.IsActiveGlobal() == true) {
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage("§aBooster Global cancelado por §f" + p.getName());
					Bukkit.broadcastMessage("");
					BoosterAPI.DesativarGlobal();
				} else {
					p.sendMessage("§cO booster global não está ativo");
				}
			} else {
				if (Bukkit.getPlayer(args[0]) != null) {
					Player target = Bukkit.getPlayer(args[0]);
					target.sendMessage("§aSeu booster foi cancelado por §f" + p.getName());
					p.sendMessage("§aBooster cancelado!");
					BoosterAPI.RemoveBooster(target);
					return true;
				} else {
					p.sendMessage("§cPlayer Inválido");
				}
			}
		}
		return false;
	}
}
