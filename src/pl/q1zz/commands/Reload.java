package pl.q1zz.commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import pl.q1zz.Main;
import pl.q1zz.storage.Storage;

public class Reload implements CommandExecutor, Listener {

    public Reload(Main m) {
        m.getCommand("q1zzReload").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.hasPermission("q1zz.reload")) {
            if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("pl_PL")) {
                sender.sendMessage(ChatColor.GRAY + "Nie posiadasz permisji " + ChatColor.BLUE + "q1zz.reload");
                return false;
            } else if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("en_US")) {
                sender.sendMessage(ChatColor.GRAY + "You don't have permission " + ChatColor.BLUE + "q1zz.reload" + ChatColor.GRAY + " to use this command!");
                return false;
            } else if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("CustomMessages")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("CustomMessages.NoReloadPermission")));
                return false;
            }
        }
        long time = System.currentTimeMillis();
        Storage.getInstance().reloadData();
        Main.getInstance().reloadConfig();
        long resoult = System.currentTimeMillis() - time;
        sender.sendMessage(ChatColor.GREEN + "Successfully reloaded config.yml and date.yml! (" + resoult + "ms)");
        return false;
    }
}
