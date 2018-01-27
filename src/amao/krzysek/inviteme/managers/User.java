package amao.krzysek.inviteme.managers;

import amao.krzysek.inviteme.InviteMe;
import amao.krzysek.inviteme.mysql.MySQL;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class User {

    protected MySQL mysql;

    protected Player player;

    public User(final Player player) {
        this.mysql = InviteMe.getMySQL();
        this.player = player;
    }

    public boolean exists() throws SQLException {
        Connection connection = this.mysql.getConnection();
        if (connection != null && !(connection.isClosed())) {
            Statement statement = connection.createStatement();
            final String query = "SELECT * FROM `users` WHERE `nickname`='" + this.player.getName() + "'";
            boolean bool = statement.executeQuery(query).next();
            statement.close();
            return bool;
        } else {
            Bukkit.getLogger().warning("Can not create user profile in MySQL database ! Check your MySQL configuration section in config.yml and restart the server !");
            return false;
        }
    }

    public void create() throws SQLException {
        Connection connection = this.mysql.getConnection();
        if (connection != null && !(connection.isClosed())) {
            Statement statement = connection.createStatement();
            final String update = "INSERT INTO `users`(`nickname`, `invites`, `points`, `recommended`, `ip`) VALUES ('" + this.player.getName() + "', '0', '0', '0', '" + this.player.getAddress().getAddress().toString().replace("/", "") + "')";
            statement.executeUpdate(update);
            statement.close();
        } else Bukkit.getLogger().warning("Can not create user profile in MySQL database ! Check your MySQL configuration section in config.yml and restart the server !");
    }

    public void sendMessage(final String message) {
        this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', InviteMe.prefix + message));
    }

    public void sendInfo(final String message) {
        this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public Player getPlayer() { return this.player; }

    public String getName() { return this.player.getName(); }

    public LinkedHashMap<String, Object> getStatistics() throws SQLException {
        Connection connection = this.mysql.getConnection();
        if (connection != null && !(connection.isClosed())) {
            Statement statement = connection.createStatement();
            final String query = "SELECT * FROM `users` WHERE `nickname`='" + this.player.getName() + "'";
            ResultSet resultSet = statement.executeQuery(query);
            LinkedHashMap<String, Object> stats = new LinkedHashMap<>();
            while (resultSet.next()) {
                stats.put("nickname", resultSet.getString("nickname"));
                stats.put("invites", resultSet.getInt("invites"));
                stats.put("points", resultSet.getInt("points"));
                stats.put("recommended", resultSet.getBoolean("recommended"));
                stats.put("ip", resultSet.getString("ip"));
            }
            statement.close();
            return stats;
        }
        return null;
    }

    public boolean setInvites(final int invites) throws SQLException {
        Connection connection = this.mysql.getConnection();
        if (connection != null && !(connection.isClosed())) {
            Statement statement = connection.createStatement();
            final String update = "UPDATE `users` SET `invites`='" + invites + "' WHERE `nickname`='" + this.player.getName() + "'";
            statement.executeUpdate(update);
            statement.close();
            return true;
        }
        return false;
    }

    public boolean setPoints(final int points) throws SQLException {
        Connection connection = this.mysql.getConnection();
        if (connection != null && !(connection.isClosed())) {
            Statement statement = connection.createStatement();
            final String update = "UPDATE `users` SET `points`='" + points + "' WHERE `nickname`='" + this.player.getName() + "'";
            statement.executeUpdate(update);
            statement.close();
            return true;
        }
        return false;
    }

    public boolean setRecommended(final boolean recommended) throws SQLException {
        Connection connection = this.mysql.getConnection();
        if (connection != null && !(connection.isClosed())) {
            Statement statement = connection.createStatement();
            int r = 0;
            if (recommended) r = 1;
            else r = 0;
            final String update = "UPDATE `users` SET `recommended`='" + r + "' WHERE `nickname`='" + this.player.getName() + "'";
            statement.executeUpdate(update);
            statement.close();
            return true;
        }
        return false;
    }

}
