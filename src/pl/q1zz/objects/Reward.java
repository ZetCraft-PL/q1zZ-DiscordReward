package pl.q1zz.objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.q1zz.DiscordRewards;

public class Reward {

    public static void claimReward(Player player) {
        for(String command : Config.COMMANDS) {
            Bukkit.getServer().getScheduler().runTask(DiscordRewards.getPlugin(DiscordRewards.class), () -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("%player%", player.getName()));
            });
        }
        for(String msg : Config.BROADCAST) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", player.getName())));
        }
    }
}
