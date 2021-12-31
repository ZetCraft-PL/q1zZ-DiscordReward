package pl.q1zz.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {

    private long discordID;
    private UUID uniqueID;

    public User(UUID uniqueID, long discordID) {
        this.uniqueID = uniqueID;
        this.discordID = discordID;
    }
    public User(ResultSet result) throws SQLException {
        this.uniqueID = UUID.fromString(result.getString("UniqueID"));
        this.discordID = result.getLong("DiscordAccountID");
    }
    public void setUniqueID(UUID uuid) {
        this.uniqueID = uuid;
    }
    public void setDiscordID(long discordID) {
        this.discordID = discordID;
    }
    public UUID getUniqueID() {
        return this.uniqueID;
    }
    public long getDiscordID() {
        return this.discordID;
    }
}
