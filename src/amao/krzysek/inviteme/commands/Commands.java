package amao.krzysek.inviteme.commands;

import amao.krzysek.inviteme.InviteMe;
import amao.krzysek.inviteme.managers.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("inviteme") || commandLabel.equalsIgnoreCase("invme")) {
            if (sender instanceof Player) {
                final User user = new User((Player) sender);
                if (args.length == 0) {
                    user.sendInfo("");
                    user.sendInfo("&7--- [&6InvMe&7] ---------------------------");
                    user.sendInfo("&7/&einviteme &7- &ePlugin commands");
                    user.sendInfo("&7/&einviteme recommend [player] &7- &eRecommend player");
                    user.sendInfo("&7/&einviteme stats [player] &7- &eYour statistics");
                    user.sendInfo("&7/&einviteme prizes &7- &eGet your prizes");
                    if (user.getPlayer().hasPermission("inviteme.*") || user.getPlayer().isOp()) {
                        user.sendInfo("&7/&einviteme setinvites [player] [number] &7- &eSet player's invites");
                        user.sendInfo("&7/&einviteme setpoints [player] [number]&7- &eSet player's points");
                        user.sendInfo("&7/&einviteme reload &7- &eReload config");
                    }
                    user.sendInfo("&7--- [&6InvMe&7] ---------------------------");
                    user.sendInfo("");
                } else {
                    if (!(args[0].equalsIgnoreCase("stats") || args[0].equalsIgnoreCase("prizes") || args[0].equalsIgnoreCase("setinvites") || args[0].equalsIgnoreCase("setpoints") || args[0].equalsIgnoreCase("recommend") || args[0].equalsIgnoreCase("reload"))) user.sendMessage(InviteMe.getYaml().getString("messages.usage"));
                    else {
                        if (args.length == 1 && (args[0].equalsIgnoreCase("stats") || args[0].equalsIgnoreCase("prizes") || args[0].equalsIgnoreCase("reload"))) {
                            if (args[0].equalsIgnoreCase("stats")) {
                                try {
                                    final LinkedHashMap<String, Object> stats = user.getStatistics();
                                    if (stats != null) {
                                        user.sendInfo("");
                                        user.sendInfo("&7--- [&6InvMe&7] ---------------------------");
                                        user.sendInfo("&7" + InviteMe.getYaml().getString("messages.stats-command.nickname") + ": &e" + user.getName());
                                        user.sendInfo("&7" + InviteMe.getYaml().getString("messages.stats-command.invites") + ": &e" + stats.get("invites"));
                                        user.sendInfo("&7" + InviteMe.getYaml().getString("messages.stats-command.points") + ": &e" + stats.get("points"));
                                        user.sendInfo("&7" + InviteMe.getYaml().getString("messages.stats-command.recommended") + ": &e" + stats.get("recommended").toString().replaceAll("false", InviteMe.getYaml().getString("messages.stats-command.recommended-no")).replaceAll("true", InviteMe.getYaml().getString("messages.stats-command.recommended-yes")));
                                        user.sendInfo("&7--- [&6InvMe&7] ---------------------------");
                                        user.sendInfo("");
                                    } else user.sendInfo(ChatColor.translateAlternateColorCodes('&', InviteMe.getYaml().getString("messages.mysql-error")));
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (args[0].equalsIgnoreCase("prizes")) {
                                try {
                                    final LinkedHashMap<String, Object> stats = user.getStatistics();
                                    if (stats != null) {
                                        final String guiName = ChatColor.translateAlternateColorCodes('&', "&7InvMe &e| " + InviteMe.getYaml().getString("prizes.gui.name").replaceAll("%player%", user.getName()).replaceAll("%points%", String.valueOf(stats.get("points"))).replaceAll("%invites%", String.valueOf(stats.get("invites"))));
                                        Inventory inventory = Bukkit.createInventory(null, InviteMe.getYaml().getInt("prizes.gui.size"), guiName);
                                        final String activePrizes = InviteMe.getYaml().getString("prizes.gui.active");
                                        for (final String checkPrize : activePrizes.split(";")) {
                                            final String prizeName = ChatColor.translateAlternateColorCodes('&', InviteMe.getYaml().getString("prizes.gui.set." + checkPrize + ".name"));
                                            final String prizeDesc = ChatColor.translateAlternateColorCodes('&', InviteMe.getYaml().getString("prizes.gui.set." + checkPrize + ".description"));
                                            final int prizePrice = InviteMe.getYaml().getInt("prizes.gui.set." + checkPrize + ".price");
                                            final int prizePos = InviteMe.getYaml().getInt("prizes.gui.set." + checkPrize + ".position");
                                            final String prizeItem = InviteMe.getYaml().getString("prizes.gui.set." + checkPrize + ".item");
                                            ItemStack prizeStack = new ItemStack(Material.getMaterial(prizeItem), 1);
                                            ItemMeta prizeMeta = prizeStack.getItemMeta();
                                            prizeMeta.setDisplayName(prizeName);
                                            LinkedList<String> newLore = new LinkedList<>();
                                            newLore.add(ChatColor.translateAlternateColorCodes('&', "&6" + InviteMe.getYaml().getString("prizes.gui.texts.name") + ": " + prizeName));
                                            newLore.add(ChatColor.translateAlternateColorCodes('&', "&6" + InviteMe.getYaml().getString("prizes.gui.texts.description") + ": " + prizeDesc));
                                            newLore.add(ChatColor.translateAlternateColorCodes('&', "&6" + InviteMe.getYaml().getString("prizes.gui.texts.price") + ": &e" + prizePrice));
                                            prizeMeta.setLore(newLore);
                                            prizeStack.setItemMeta(prizeMeta);
                                            inventory.setItem(prizePos, prizeStack);
                                        }
                                        user.getPlayer().openInventory(inventory);
                                    } else user.sendInfo(ChatColor.translateAlternateColorCodes('&', InviteMe.getYaml().getString("messages.mysql-error")));
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (args[0].equalsIgnoreCase("reload")) {
                                if (user.getPlayer().hasPermission("inviteme.*") || user.getPlayer().isOp()) {
                                    user.sendMessage(InviteMe.getYaml().getString("messages.config-reloaded"));
                                    InviteMe.reloadFiles();
                                } else user.sendMessage(InviteMe.getYaml().getString("messages.no-access"));
                            }
                        } else if (args.length == 2 && (args[0].equalsIgnoreCase("stats") || args[0].equalsIgnoreCase("recommend"))) {
                            if (!(args[0].equalsIgnoreCase("stats") || args[0].equalsIgnoreCase("prizes") || args[0].equalsIgnoreCase("setinvites") || args[0].equalsIgnoreCase("setpoints") || args[0].equalsIgnoreCase("recommend"))) user.sendMessage(InviteMe.getYaml().getString("messages.usage"));
                            else {
                                if (args[0].equalsIgnoreCase("stats")) {
                                    final Player check = Bukkit.getPlayer(args[1]);
                                    if (check != null) {
                                        final User c = new User(check);
                                        try {
                                            if (c.exists()) {
                                                final LinkedHashMap<String, Object> stats = c.getStatistics();
                                                if (stats != null) {
                                                    user.sendInfo("");
                                                    user.sendInfo("&7--- [&6InvMe&7] ---------------------------");
                                                    user.sendInfo("&7" + InviteMe.getYaml().getString("messages.stats-command.nickname") + ": &e" + stats.get("nickname"));
                                                    user.sendInfo("&7" + InviteMe.getYaml().getString("messages.stats-command.invites") + ": &e" + stats.get("invites"));
                                                    user.sendInfo("&7" + InviteMe.getYaml().getString("messages.stats-command.points") + ": &e" + stats.get("points"));
                                                    user.sendInfo("&7" + InviteMe.getYaml().getString("messages.stats-command.recommended") + ": &e" + stats.get("recommended").toString().replaceAll("false", InviteMe.getYaml().getString("messages.stats-command.recommended-no")).replaceAll("true", InviteMe.getYaml().getString("messages.stats-command.recommended-yes")));
                                                    user.sendInfo("&7--- [&6InvMe&7] ---------------------------");
                                                    user.sendInfo("");
                                                } else user.sendInfo(ChatColor.translateAlternateColorCodes('&', InviteMe.getYaml().getString("messages.mysql-error")));
                                            } else user.sendMessage(InviteMe.getYaml().getString("messages.player-not-found-db"));
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    } else user.sendMessage(InviteMe.getYaml().getString("messages.player-not-found"));
                                }
                                if (args[0].equalsIgnoreCase("recommend")) {
                                    final Player check = Bukkit.getPlayer(args[1]);
                                    if (check != null) {
                                        final User c = new User(check);
                                        if (!(c.getName().equals(user.getName()))) {
                                            try {
                                                if (c.exists()) {
                                                    final LinkedHashMap<String, Object> stats = user.getStatistics();
                                                    if (stats != null) {
                                                        if (!((boolean) stats.get("recommended"))) {
                                                            final LinkedHashMap<String, Object> cstats = c.getStatistics();
                                                            if (cstats != null) {
                                                                final int cpoints = ((int) cstats.get("points")) + InviteMe.getYaml().getInt("recommendations.points");
                                                                final int cinvites = ((int) cstats.get("invites")) + 1;
                                                                if (user.setRecommended(true) && c.setInvites(cinvites) && c.setPoints(cpoints)) {
                                                                    user.sendMessage(InviteMe.getYaml().getString("messages.success-recommended").replaceAll("%player%", c.getName()));
                                                                    c.sendMessage(InviteMe.getYaml().getString("messages.success-recommended-recieved").replaceAll("%player%", user.getName()));
                                                                } else user.sendInfo(ChatColor.translateAlternateColorCodes('&', InviteMe.getYaml().getString("messages.mysql-error")));
                                                            } else user.sendInfo(ChatColor.translateAlternateColorCodes('&', InviteMe.getYaml().getString("messages.mysql-error")));
                                                        } else user.sendMessage(InviteMe.getYaml().getString("messages.already-recommended"));
                                                    } else user.sendInfo(ChatColor.translateAlternateColorCodes('&', InviteMe.getYaml().getString("messages.mysql-error")));
                                                } else user.sendMessage(InviteMe.getYaml().getString("messages.player-not-found-db"));
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        } else user.sendMessage(InviteMe.getYaml().getString("messages.fail-recommended"));
                                    } else user.sendMessage(InviteMe.getYaml().getString("messages.player-not-found"));
                                }
                            }
                        } else if (args.length == 3 && (args[0].equalsIgnoreCase("setinvites") || args[0].equalsIgnoreCase("setpoints"))) {
                            if (!(args[0].equalsIgnoreCase("stats") || args[0].equalsIgnoreCase("prizes") || args[0].equalsIgnoreCase("setinvites") || args[0].equalsIgnoreCase("setpoints") || args[0].equalsIgnoreCase("recommend"))) user.sendMessage(InviteMe.getYaml().getString("messages.usage"));
                            else {
                                if (args[0].equalsIgnoreCase("setinvites")) {
                                    if (user.getPlayer().hasPermission("inviteme.*") || user.getPlayer().isOp()) {
                                        final Player check = Bukkit.getPlayer(args[1]);
                                        if (check != null) {
                                            final User c = new User(check);
                                            try {
                                                if (c.exists()) {
                                                    if (args[2].chars().allMatch(Character::isDigit)) {
                                                        if (c.setInvites(Integer.parseInt(args[2]))) user.sendMessage(InviteMe.getYaml().getString("messages.invites-set").replaceAll("%player%", c.getName()).replaceAll("%amount%", args[2]));
                                                        else user.sendInfo(ChatColor.translateAlternateColorCodes('&', InviteMe.getYaml().getString("messages.mysql-error")));
                                                    } else
                                                        user.sendMessage(InviteMe.getYaml().getString("messages.not-numeric"));
                                                } else
                                                    user.sendMessage(InviteMe.getYaml().getString("messages.player-not-found-db"));
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        } else user.sendMessage(InviteMe.getYaml().getString("messages.player-not-found"));
                                    } else user.sendMessage(InviteMe.getYaml().getString("messages.no-access"));
                                }
                                if (args[0].equalsIgnoreCase("setpoints")) {
                                    if (user.getPlayer().hasPermission("inviteme.*") || user.getPlayer().isOp()) {
                                        final Player check = Bukkit.getPlayer(args[1]);
                                        if (check != null) {
                                            final User c = new User(check);
                                            try {
                                                if (c.exists()) {
                                                    if (args[2].chars().allMatch(Character::isDigit)) {
                                                        if (c.setPoints(Integer.parseInt(args[2]))) user.sendMessage(InviteMe.getYaml().getString("messages.points-set").replaceAll("%player%", c.getName()).replaceAll("%amount%", args[2]));
                                                        else user.sendInfo(ChatColor.translateAlternateColorCodes('&', InviteMe.getYaml().getString("messages.mysql-error")));
                                                    } else
                                                        user.sendMessage(InviteMe.getYaml().getString("messages.not-numeric"));
                                                } else
                                                    user.sendMessage(InviteMe.getYaml().getString("messages.player-not-found-db"));
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        } else user.sendMessage(InviteMe.getYaml().getString("messages.player-not-found"));
                                    } else user.sendMessage(InviteMe.getYaml().getString("messages.no-access"));
                                }
                            }
                        } else user.sendMessage(InviteMe.getYaml().getString("messages.usage"));
                    }
                }
            } else sender.sendMessage("Commands of InviteMe can be only executed by in-game players");
        }
        return false;
    }

}
