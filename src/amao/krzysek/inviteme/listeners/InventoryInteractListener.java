package amao.krzysek.inviteme.listeners;

import amao.krzysek.inviteme.InviteMe;
import amao.krzysek.inviteme.managers.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

public class InventoryInteractListener implements Listener {

    @EventHandler
    public void inventoryInteract(InventoryClickEvent e) {
        if (e.getInventory().getName().contains("InvMe")) {
            e.setCancelled(true);
            String prizeId = null;
            final String activePrizes = InviteMe.getYaml().getString("prizes.gui.active");
            for (final String checkPrize : activePrizes.split(";")) {
                final String prizeName = ChatColor.translateAlternateColorCodes('&', InviteMe.getYaml().getString("prizes.gui.set." + checkPrize + ".name"));
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(prizeName)) prizeId = checkPrize;
            }
            try {
                final User user = new User((Player) e.getWhoClicked());
                final LinkedHashMap<String, Object> stats = user.getStatistics();
                final int prizePrice = InviteMe.getYaml().getInt("prizes.gui.set." + prizeId + ".price");
                if ((int) stats.get("points") >= prizePrice) {
                    user.setPoints(((int) stats.get("points")) - prizePrice);
                    final String prizeMsg = ChatColor.translateAlternateColorCodes('&', InviteMe.getYaml().getString("prizes.gui.set." + prizeId + ".message"));
                    final List<String> commands = InviteMe.getYaml().getStringList("prizes.gui.set." + prizeId + ".commands");
                    user.sendMessage(prizeMsg);
                    for (final String command : commands) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", user.getName()));
                } else user.sendMessage(InviteMe.getYaml().getString("messages.not-enough-points"));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
