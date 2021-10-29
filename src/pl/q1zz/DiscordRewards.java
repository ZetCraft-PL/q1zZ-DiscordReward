package pl.q1zz;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.q1zz.bStats.Metrics;
import pl.q1zz.data.LocalData;
import pl.q1zz.discord.BotManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class DiscordRewards extends JavaPlugin {

    private static DiscordRewards instance;

    private Connection connection;

    public String host, database, username, password, savetype;

    public boolean mysql;

    public int port;

    public static DiscordRewards getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Metrics metrics = new Metrics(this, 13115);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        BotManager.startBot();

        switch (getConfig().getString("data.savetype")) {
            case "Flat":
                LocalData.getInstance().setup(this);
                savetype = "flat";
                break;
            case "MySQL":
                mysqlSetup();
                savetype = "mysql";
                break;
            default:
                getLogger().log(Level.WARNING, "\n Unknown savetype method! Use: Flat or MySQL\n");
                Bukkit.getPluginManager().disablePlugin(this);
        }
    }
    @Override
    public void onDisable() {
        BotManager.stopBot();
    }

    public void mysqlSetup() {
        host = DiscordRewards.getInstance().getConfig().getString("datebase.hostname");
        port = DiscordRewards.getInstance().getConfig().getInt("datebase.port");
        database = DiscordRewards.getInstance().getConfig().getString("datebase.datebase");
        username = DiscordRewards.getInstance().getConfig().getString("datebase.username");
        password = DiscordRewards.getInstance().getConfig().getString("datebase.password");
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":" + port + "/" + database, username, password));


                connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `q1zz_receivedrewards` (`UniqueID` VARCHAR(96) PRIMARY KEY, `DiscordAccountID` VARCHAR(96));");

                mysql = true;

                getLogger().log(Level.INFO, "Successful connected to MySQl datebase!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            getLogger().log(Level.INFO, "An error occured...");
            getLogger().log(Level.INFO, "\n " + e.getMessage() + " \n");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
    }
    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
