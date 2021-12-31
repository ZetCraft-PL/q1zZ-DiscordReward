package pl.q1zz.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class LocalData {

    public static LocalData instance;

    public static File file;

    FileConfiguration data;

    public static LocalData getInstance() {
        return instance;
    }

    static {
        instance = new LocalData();
    }

    public void setup(Plugin p) {
        if(p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
        File path = new File(p.getDataFolder() + File.separator + "/storage");
        file = new File(path, File.separator + "date.yml");
        if(!file.exists()) {
            try {
                path.mkdirs();
                file.createNewFile();
            } catch(IOException e) {
                Bukkit.getLogger().log(Level.WARNING, "Unable to create date.yml Details: ");
                Bukkit.getLogger().log(Level.WARNING, "\n" + e.getMessage() + "\n");
            }
        }
        data = YamlConfiguration.loadConfiguration(file);
    }
    public FileConfiguration getData() {
        return data;
    }
    public void saveData() {
        try {
            data.save(file);
        } catch(IOException e) {
            Bukkit.getLogger().log(Level.WARNING, "Unable to save date.yml Details: ");
            Bukkit.getLogger().log(Level.WARNING, "\n" + e.getMessage() + "\n");
        }
    }
    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(file);
    }
    public static void saveData(Player player, Long id) {
        getInstance().getData().set("RewardsReceived." + player.getName() + ".Received", true);
        getInstance().getData().set("RewardsReceived." + player.getName() + ".DiscordAccountID", id);
        getInstance().saveData();
    }
}
