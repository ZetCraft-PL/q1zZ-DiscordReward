package pl.q1zz;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.q1zz.bStats.Metrics;
import pl.q1zz.data.LocalData;
import pl.q1zz.discord.BotManager;
import pl.q1zz.util.UserUtil;

import javax.security.auth.login.LoginException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import static pl.q1zz.varables.Config.configureVarables;

public class DiscordRewards extends JavaPlugin {

    private static DiscordRewards instance;

    private Connection connection;

    private String host, database, username, password;
    private int port;

    public boolean MySQL;
    public String saveMethod;

    public static DiscordRewards getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Metrics metrics = new Metrics(this, 13115);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        configureVarables();


        switch (getConfig().getString("data.savetype")) {
            case "Flat":
                LocalData.getInstance().setup(this);
                saveMethod = "flat";
                break;
            case "MySQL":
                try {
                    saveMethod = "mysql";
                    mysqlSetup();
                    UserUtil.loadUsers();
                } catch (SQLException | ClassNotFoundException e) {
                    getLogger().log(Level.INFO, "An error occured...");
                    getLogger().log(Level.INFO, "\n " + e.getMessage() + " \n");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return;
                }
                break;
            default:
                getLogger().log(Level.WARNING, "\n Unknown savetype method! Use: Flat or MySQL\n");
                Bukkit.getPluginManager().disablePlugin(this);
                break;
        }
        getLogger().info("Starting discord bot...");
        Bukkit.getServer().getScheduler().runTaskAsynchronously(this, () -> {
            try {
                BotManager.startBot();
            } catch (LoginException | InterruptedException e) {
                Bukkit.getLogger().log(Level.INFO, "Could not login to bot token! Details:");
                Bukkit.getLogger().log(Level.INFO, " " + e.getMessage() + " ");
                Bukkit.getPluginManager().disablePlugin(DiscordRewards.getInstance());
            }
        });
    }
    @Override
    public void onDisable() {
        BotManager.stopBot();
    }

    public void mysqlSetup() throws SQLException, ClassNotFoundException {
        host = getConfig().getString("datebase.hostname");
        port = getConfig().getInt("datebase.port");
        database = getConfig().getString("datebase.datebase");
        username = getConfig().getString("datebase.username");
        password = getConfig().getString("datebase.password");
        synchronized (this) {
            if (getConnection() != null && !getConnection().isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            setConnection(DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database, username, password));


            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `q1zz_receivedrewards` (`UniqueID` VARCHAR(96) PRIMARY KEY, `DiscordAccountID` VARCHAR(96));");

            MySQL = true;

            getLogger().log(Level.INFO, "Successful connected to MySQl datebase!");
        }
    }
    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public void log(String message) {
        getLogger().log(Level.INFO, message);
    }
}
