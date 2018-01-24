package amao.krzysek.inviteme;

import amao.krzysek.inviteme.commands.Commands;
import amao.krzysek.inviteme.listeners.InventoryInteractListener;
import amao.krzysek.inviteme.listeners.PlayerJoinListener;
import amao.krzysek.inviteme.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class InviteMe extends JavaPlugin {

    // default
    public static String prefix = ChatColor.translateAlternateColorCodes('&', "&7[&6InvMe&7]&f ");
    // configuration files
    protected static File configFile;
    protected static FileConfiguration config;
    // mysql
    protected static MySQL mysql;
    protected String user, password, host, port, database;

    @Override
    public void onEnable() {
        // handling listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryInteractListener(), this);
        // handling commands
        getCommand("inviteme").setExecutor(new Commands());
        getCommand("invme").setExecutor(new Commands());
        // configuration files
        createFiles();
        this.user = getConfig().getString("mysql.user");
        this.password = getConfig().getString("mysql.password");
        this.host = getConfig().getString("mysql.host");
        this.port = getConfig().getString("mysql.port");
        this.database = getConfig().getString("mysql.database");
        // mysql
        mysql = new MySQL();
        try {
            mysql.connect(this.user, this.password, this.host, this.port, this.database);
            Bukkit.getLogger().info("Successfully connected InviteMe with MySQL !");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // configuration files
        saveFiles();
        // mysql
        try {
            mysql.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // configuration files
    public static FileConfiguration getYaml() { return config; }

    protected void createFiles() {
        configFile = new File(getDataFolder(), "config.yml");
        if (!(configFile.exists())) {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", true);
        }
        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    protected void saveFiles() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadFiles() {
        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    // mysql
    public static MySQL getMySQL() { return mysql; }

}
