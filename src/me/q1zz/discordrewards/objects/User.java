package me.q1zz.discordrewards.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {

    private final UUID uniqueID;
    private final long discordAccountID;

    public User(UUID uniqueID, long discordAccountID) {
        this.uniqueID = uniqueID;
        this.discordAccountID = discordAccountID;
    }
    public User(ResultSet results) throws SQLException {
        this.uniqueID = UUID.fromString(results.getString("UniqueID"));
        this.discordAccountID = results.getLong("DiscordAccountID");
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public long getDiscordAccountID() {
        return discordAccountID;
    }

}
