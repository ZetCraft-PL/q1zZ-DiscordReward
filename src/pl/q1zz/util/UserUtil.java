package pl.q1zz.util;

import org.bukkit.Bukkit;
import pl.q1zz.DiscordRewards;
import pl.q1zz.objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserUtil {

    private static Map<UUID, User> users = new ConcurrentHashMap<>();

    public static Map<UUID, User> getUsers() {
        return users;
    }

    public static User redeemReward(UUID uniqueID, long discordID) {
        User user = null;
        if (!users.containsKey(uniqueID)) {
            users.put(uniqueID, user = new User(uniqueID, discordID));
            createUserData(user);
        }
        return user;
    }
    public static void createUserData(User user) {
        try {
            PreparedStatement insert = DiscordRewards.getInstance().getConnection()
                    .prepareStatement("INSERT INTO " + "q1zz_receivedrewards" + " (UniqueID,DiscordAccountID) VALUES (?,?)");
            insert.setString(1, user.getUniqueID().toString());
            insert.setLong(2, user.getDiscordID());
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static User getUser(UUID uuid) {
        for (User user : users.values()) {
            if (user.getUniqueID().equals(uuid)) {
                return user;
            }
        }
        return null;
    }
    public static User getUserFromID(long id) {
        for (User user : users.values()) {
            if (user.getDiscordID() == id) {
                return user;
            }
        }
        return null;
    }
    public static void createUser(ResultSet results) {
        try {
            if (!users.containsKey(results.getString("UniqueID"))) {
                users.put(UUID.fromString(results.getString("UniqueID")), new User(results));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void loadUsers() throws SQLException {
        int i = 0;
        PreparedStatement statement = DiscordRewards.getInstance().getConnection()
                .prepareStatement("SELECT * FROM `q1zz_receivedrewards`");
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            createUser(results);
            i++;
        }
        DiscordRewards.getInstance().log("Loaded " + i + " records from datebase!");
    }
}
