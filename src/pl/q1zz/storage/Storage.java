package pl.q1zz.storage;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Storage {

    static Storage instance;
    Plugin p;
    FileConfiguration data;
    public static File rfile;

    static {
        instance = new Storage();
    }

    public static Storage getInstance() {
        return instance;
    }

    public void setup(Plugin p) {
        if(p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
        File path = new File(p.getDataFolder() + File.separator + "/storage");
        rfile = new File(path, String.valueOf(File.separator + "date.yml"));
        if(!rfile.exists()) {
            try {
                path.mkdirs();
                rfile.createNewFile();
            } catch(IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Unable to create date.yml Details: \n " + e.getMessage());

            }
        }
        data = YamlConfiguration.loadConfiguration(rfile);
    }

    public FileConfiguration getData() {
        return data;
    }

    public void saveData() {
        try {
            data.save(rfile);
        } catch(IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Unable to save date.yml Details: \n " + e.getMessage());
        }
    }

    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(rfile);
    }
}


