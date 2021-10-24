package pl.q1zz.data;


import org.bukkit.ChatColor;
import pl.q1zz.DiscordRewards;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLService {

    public static boolean isReceived(UUID uuid) {
        try {
            PreparedStatement statement = DiscordRewards.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM " + "q1zz_receivedrewards" + " WHERE UNIQUEID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void UserReceived(UUID uuid, Long id) {
        DiscordRewards plugin = DiscordRewards.getPlugin(DiscordRewards.class);
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + "q1zz_receivedrewards" + " WHERE UNIQUEID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            if (isReceived(uuid) != true) {
                PreparedStatement insert = plugin.getConnection()
                        .prepareStatement("INSERT INTO " + "q1zz_receivedrewards" + " (uniqueid,discordaccountid) VALUES (?,?)");
                insert.setString(1, uuid.toString());
                insert.setLong(2, id);
                insert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


