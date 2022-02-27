package PombaHeads.Comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import PombaHeads.APIS.ActionBar;
import PombaHeads.APIS.MenuAPI;
import PombaHeads.APIS.RankAPI;

public class Rankup implements Listener, CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player p = (Player) sender;

		if (label.equalsIgnoreCase("rankup")) {
			if (RankAPI.RankCompleted(p) == true) {
				ActionBar.sendActionBarMessage(p, "§aVocê finalizou o grupo de ranks, digite /prestigio para resetar seus ranks");
				return true;
			}
			if (args.length != 0) {
				p.sendMessage("§cUtilize: /rankup");
				return true;
			}
			MenuAPI.OpenRanks(p);
		}
		return false;
	}
}
