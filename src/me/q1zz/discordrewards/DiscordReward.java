package me.q1zz.discordrewards;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.q1zz.discordrewards.bStats.Metrics;
import me.q1zz.discordrewards.discord.BotManager;
import me.q1zz.discordrewards.managers.UserManager;
import me.q1zz.discordrewards.variables.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DiscordReward extends JavaPlugin {

    private static DiscordReward instance;

    public static DiscordReward getInstance() {
        return instance;
    }

    private Connection connection;
    private HikariDataSource dataSource;

    @Override
    public void onEnable() {
        instance = this;

        new Metrics(this, 13115);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        mysqlSetup();
        UserManager.loadUsers();

        BotManager.startBot(getConfig().getString("bot.token"), getConfig().getString("bot.status"));

        Config.configureVariables();

    }
    @Override
    public void onDisable() {
        BotManager.stopBot();
    }
    private void mysqlSetup() {
        dataSource = new HikariDataSource(this.getHikariConfig(getConfig().getString("mysql.host"), getConfig().getInt("mysql.port"), getConfig().getString("mysql.database"), getConfig().getString("mysql.username"), getConfig().getString("mysql.password")));
        getLogger().info("Successfully connected to MySQL database!");
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `q1zz_receivedrewards` (`UniqueID` VARCHAR(36) PRIMARY KEY, `DiscordAccountID` LONG);");
        } catch (SQLException e) {
            getLogger().severe("Error while creating table in database...");
            e.printStackTrace();
        }
    }
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
        }
        return connection;
    }
    private HikariConfig getHikariConfig(String host, int port, String database, String username, String password) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s",
                host,
                port,
                database));
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");

        return hikariConfig;
    }
}
