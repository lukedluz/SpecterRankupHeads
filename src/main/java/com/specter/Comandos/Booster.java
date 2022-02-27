package PombaHeads.Comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import PombaHeads.APIS.ActionBar;
import PombaHeads.APIS.BoosterAPI;
import PombaHeads.APIS.TimeAPI;

public class Booster implements CommandExecutor, Listener {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) (sender);
		if (cmd.getName().equalsIgnoreCase("booster")) {
			if (args.length != 0) {
				p.sendMessage("§cUse: /booster");
				return true;
			}
			if (BoosterAPI.IsActive(p) == false) {
				ActionBar.sendActionBarMessage(p, "§cVocê não possui um booster ativo");
				return true;
			} else {
				long endOfBan = BoosterAPI.GetTempo(p);
				long now = System.currentTimeMillis();
				long diff = endOfBan - now;
				if (diff > 0) {
					p.sendMessage("§a• Você possui um booster de " + BoosterAPI.GetBonus(p) + "x ativo");
					p.sendMessage("§a• Tempo restante: " + TimeAPI.getMSG(endOfBan));
					return true;
				} else {
					BoosterAPI.RemoveBooster(p);
					ActionBar.sendActionBarMessage(p, "§cVocê não possui um booster ativo");
				}
			}
		}
		return false;
	}
}