package PombaHeads.Comandos;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import PombaHeads.Main;

public class Ranks implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player s = (Player) sender;
		if (label.equalsIgnoreCase("ranks")) {
			if (args.length != 0) {
				sender.sendMessage("§cUtilize: /ranks");
				return true;
			}
			
			List<String> messagesranks = Main.m.getConfig().getStringList("Mensagens.Ranks");
			String messageranks = "";
			for (String mranks : messagesranks) {
				messageranks += mranks.replace("&", "§");
				messageranks += "\n";
			}
			s.sendMessage(messageranks);
			
		}
		return false;
	}
}