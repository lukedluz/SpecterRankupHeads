package PombaHeads.APIS;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import PombaHeads.Main;

public class MenuAPI implements Listener {

	public static ItemStack Sim;
	public static ItemMeta Simmeta;
	public static ItemStack Nao;
	public static ItemMeta Naometa;
	public static ItemStack I1;
	public static ItemMeta I1meta;
	public static ItemStack I2;
	public static ItemMeta I2meta;

	@EventHandler
	public void Click(InventoryClickEvent e) {
		if ((e.getCurrentItem() != null) && (e.getCurrentItem().getItemMeta() != null)) {
			Inventory inv = e.getInventory();
			Player p = (Player) e.getWhoClicked();

			if (inv.getTitle().equals(Main.m.getConfig().getString("Gui.Rankup.Nome").replace("&", "§"))) {
				e.setCancelled(true);
				if (e.getCurrentItem().isSimilar(Sim)) {
					p.closeInventory();

					String preco = RankAPI.getRankPreco(p);

					String mob = RankAPI.getRankHead(p);
					String q = RankAPI.getRankQuantidade(p);
					if (Main.economy.getBalance(p) >= Double.valueOf(preco)) {
						if (DataAPI.getHeads(p, mob) >= Double.valueOf(q)) {
							DataAPI.removeHeads(p, Double.valueOf(q), mob);
							Main.economy.withdrawPlayer(p, Double.valueOf(preco));
							RankAPI.UpRank(p);
						} else {
							p.sendMessage("§cVocê não tem mobs mortos suficiente, você precisa de " + q
									+ " para upar de rank");
						}
					} else {
						p.sendMessage("§cVocê não tem coins suficiente, você precisa de " + RankAPI.getRankPreco(p)
								+ " para upar de rank");
					}
				}
				if (e.getCurrentItem().isSimilar(Nao)) {
					p.closeInventory();
					ActionBar.sendActionBarMessage(p, "§cAção cancelada");
				}
			}
		}
	}

	@EventHandler
	public void Click2(InventoryClickEvent e) {
		if ((e.getCurrentItem() != null) && (e.getCurrentItem().getItemMeta() != null)) {
			Inventory inv = e.getInventory();
			Player p = (Player) e.getWhoClicked();

			if (inv.getTitle().equals(Main.m.getConfig().getString("Gui.Prestigio.Nome").replace("&", "§"))) {
				e.setCancelled(true);
				if (e.getCurrentItem().isSimilar(Sim)) {
					p.closeInventory();
					RankAPI.AddPrestigio(p, 1);

					for (String cmd : Main.m.getConfig()
							.getStringList("Prestigios." + RankAPI.getPrestigio(p) + ".Comandos")) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%p", p.getName()));
					}

					RankAPI.ResetAccount(p);
					ActionBar.sendActionBarMessage(p, "§aSeu rank foi resetado e suas recompensas foram entregues");
				}
				if (e.getCurrentItem().isSimilar(Nao)) {
					p.closeInventory();
					ActionBar.sendActionBarMessage(p, "§cAção cancelada");
				}
			}
		}
	}

	public static void OpenRanks(Player p) {
		Inventory ranksgui = Bukkit.createInventory(null,
				Integer.valueOf(Main.m.getConfig().getString("Gui.Rankup.Tamanho")),
				Main.m.getConfig().getString("Gui.Rankup.Nome").replace("&", "§"));

		Sim = SkullCreator.Green;
		Simmeta = Sim.getItemMeta();
		Simmeta.setDisplayName(Main.m.getConfig().getString("Gui.Rankup.Itens.Sim.Nome").replace("&", "§"));
		List<String> SimList = Main.m.getConfig().getStringList("Gui.Rankup.Itens.Sim.Lore");
		List<String> SimLore = new ArrayList<String>();
		for (String string : SimList) {
			SimLore.add(string.replace("&", "§"));
			Simmeta.setLore(SimLore);
		}
		Sim.setItemMeta(Simmeta);

		Nao = SkullCreator.Red;
		Naometa = Nao.getItemMeta();
		Naometa.setDisplayName(Main.m.getConfig().getString("Gui.Rankup.Itens.Nao.Nome").replace("&", "§"));
		List<String> NaoList = Main.m.getConfig().getStringList("Gui.Rankup.Itens.Nao.Lore");
		List<String> NaoLore = new ArrayList<String>();
		for (String string : NaoList) {
			NaoLore.add(string.replace("&", "§"));
			Naometa.setLore(NaoLore);
		}
		Nao.setItemMeta(Naometa);

		I1 = SkullCreator.Info;
		I1meta = I1.getItemMeta();
		I1meta.setDisplayName(Main.m.getConfig().getString("Gui.Rankup.Itens.Info.Nome").replace("&", "§"));
		List<String> I1List = Main.m.getConfig().getStringList("Gui.Rankup.Itens.Info.Lore");
		List<String> I1Lore = new ArrayList<String>();
		for (String string : I1List) {
			I1Lore.add(string.replace("&", "§").replace("%nextrank", RankAPI.getProxRankPrefix(p)).replace("%rank",
					RankAPI.getRankPrefix(p)));
			I1meta.setLore(I1Lore);
		}
		I1.setItemMeta(I1meta);

		ranksgui.setItem(Integer.valueOf(Main.m.getConfig().getString("Gui.Rankup.Itens.Sim.Slot")) - 1, Sim);
		ranksgui.setItem(Integer.valueOf(Main.m.getConfig().getString("Gui.Rankup.Itens.Nao.Slot")) - 1, Nao);
		ranksgui.setItem(Integer.valueOf(Main.m.getConfig().getString("Gui.Rankup.Itens.Info.Slot")) - 1, I1);
		p.openInventory(ranksgui);
	}

	public static void OpenPrestigio(Player p) {
		Inventory ranksgui = Bukkit.createInventory(null,
				Integer.valueOf(Main.m.getConfig().getString("Gui.Prestigio.Tamanho")),
				Main.m.getConfig().getString("Gui.Prestigio.Nome").replace("&", "§"));

		Sim = SkullCreator.Green;
		Simmeta = Sim.getItemMeta();
		Simmeta.setDisplayName(Main.m.getConfig().getString("Gui.Prestigio.Itens.Sim.Nome").replace("&", "§"));
		List<String> SimList = Main.m.getConfig().getStringList("Gui.Prestigio.Itens.Sim.Lore");
		List<String> SimLore = new ArrayList<String>();
		for (String string : SimList) {
			SimLore.add(string.replace("&", "§"));
			Simmeta.setLore(SimLore);
		}
		Sim.setItemMeta(Simmeta);

		Nao = SkullCreator.Red;
		Naometa = Nao.getItemMeta();
		Naometa.setDisplayName(Main.m.getConfig().getString("Gui.Prestigio.Itens.Nao.Nome").replace("&", "§"));
		List<String> NaoList = Main.m.getConfig().getStringList("Gui.Prestigio.Itens.Nao.Lore");
		List<String> NaoLore = new ArrayList<String>();
		for (String string : NaoList) {
			NaoLore.add(string.replace("&", "§"));
			Naometa.setLore(NaoLore);
		}
		Nao.setItemMeta(Naometa);

		I2 = SkullCreator.Info;
		I2meta = I2.getItemMeta();
		I2meta.setDisplayName(Main.m.getConfig().getString("Gui.Prestigio.Itens.Info.Nome").replace("&", "§"));
		List<String> I2List = Main.m.getConfig().getStringList("Gui.Prestigio.Itens.Info.Lore");
		List<String> I2Lore = new ArrayList<String>();
		for (String string : I2List) {
			I2Lore.add(string.replace("&", "§").replace("%prestigio", RankAPI.getPrestigio(p).toString())
					.replace("%nextprestigio", RankAPI.getProxPrestigio(p).toString()));
			I2meta.setLore(I2Lore);
		}
		I2.setItemMeta(I2meta);

		ranksgui.setItem(Integer.valueOf(Main.m.getConfig().getString("Gui.Prestigio.Itens.Sim.Slot")) - 1, Sim);
		ranksgui.setItem(Integer.valueOf(Main.m.getConfig().getString("Gui.Prestigio.Itens.Nao.Slot")) - 1, Nao);
		ranksgui.setItem(Integer.valueOf(Main.m.getConfig().getString("Gui.Prestigio.Itens.Info.Slot")) - 1, I2);
		p.openInventory(ranksgui);
	}

}
