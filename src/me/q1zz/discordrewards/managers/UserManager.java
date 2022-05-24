package me.q1zz.discordrewards.managers;

import me.q1zz.discordrewards.DiscordReward;
import me.q1zz.discordrewards.objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    private static final Map<UUID, User> users = new ConcurrentHashMap<>();

    public static void createUser(UUID uniqueID, long discordAccountID) {
        if(!users.containsKey(uniqueID)) {
            User user = new User(uniqueID, discordAccountID);
            users.put(uniqueID, user);
            createUserData(user);
        }
    }

    private static void createUserData(User user) {
        try {
            PreparedStatement insert = DiscordReward.getInstance().getConnection()
                    .prepareStatement("INSERT INTO " + "q1zz_receivedrewards" + " (UniqueID,DiscordAccountID) VALUES (?,?)");
            insert.setString(1, user.getUniqueID().toString());
            insert.setLong(2, user.getDiscordAccountID());
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void createUser(ResultSet results) {
        try {
            UUID uuid = UUID.fromString(results.getString("UniqueID"));
            if (!users.containsKey(uuid)) {
                users.put(uuid, new User(results));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void loadUsers() {
        try {
            int i = 0;
            PreparedStatement statement = DiscordReward.getInstance().getConnection().prepareStatement("SELECT * FROM `q1zz_receivedrewards`");
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                createUser(results);
                i++;
            }
            DiscordReward.getInstance().getLogger().info("Loaded " + i + " users from database!");
        } catch (SQLException e) {
            DiscordReward.getInstance().getLogger().severe("Error while loading users from database...");
            e.printStackTrace();
        }
    }
    public static User getUser(UUID uniqueID) {
        return users.values().stream().filter(user -> user.getUniqueID().equals(uniqueID)).findFirst().orElse(null);
    }
    public static User getPlayerFromDiscordID(long discordAccountID) {
        return users.values().stream().filter(user -> user.getDiscordAccountID() == discordAccountID).findFirst().orElse(null);
    }
}
