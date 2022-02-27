package PombaHeads.Comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import PombaHeads.Main;
import PombaHeads.APIS.MenuAPI;
import PombaHeads.APIS.RankAPI;

public class Prestigio implements Listener, CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player p = (Player) sender;

		if (label.equalsIgnoreCase("prestigio")) {
			if (!RankAPI.RankCompleted(p)) {
				p.sendMessage("§cVocê não completou todos os ranks");
				return true;
			}
			if (Main.m.getConfig().getString("Prestigios." + RankAPI.getProxPrestigio(p) + ".Porcentagem") == null) {
				p.sendMessage("§cVocê já atingiu o máximo de prestigios");
				return true;
			}
			if (args.length != 0) {
				p.sendMessage("§cDigite /prestigio");
				return true;
			}
			MenuAPI.OpenPrestigio(p);
		}
		return false;
	}

}
