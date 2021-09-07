package pl.q1zz.discord;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.q1zz.Main;
import pl.q1zz.storage.Storage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RewardClaimer extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        if (event.getChannel().getId().equals(Main.getInstance().getConfig().get("ReceivingAwardsSettings.ReceivingRewardChannelID"))) {
            if (!(event.getMessage().getContentRaw().length() >= 3) && !(event.getMessage().getContentRaw().length() >= 16)) {
                if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("pl_PL")) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > Podaj poprawny nick!").queue();
                    return;
                } else if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("en_US")) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > Enter the correct nickname!").queue();
                    return;
                } else if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("CustomMessages")) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > " + Main.getInstance().getConfig().getString("CustomMessages.WrongNickname")).queue();
                    return;
                } else
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > Enter the correct nickname!").queue();
                Bukkit.getConsoleSender().sendMessage("§cEnter correct language in config.yml!");
                return;
            }
            if (Storage.getInstance().getData().getBoolean("RewardsReceived." + event.getMessage().getContentRaw() + ".Received") == true) {
                if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("pl_PL")) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > Odebrano juz nagrode na ten nick!").queue();
                    return;
                } else if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("en_US")) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > Reward already been claimed!").queue();
                    return;
                } else if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("CustomMessages")) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > " + Main.getInstance().getConfig().getString("CustomMessages.RewardAlreadyClaimed")).queue();
                    return;
                }
                event.getChannel().sendMessage(event.getMember().getAsMention() + " > Reward already been claimed!").queue();
                Bukkit.getConsoleSender().sendMessage("§cEnter correct language in config.yml!");
                return;
            }
            Player p = Bukkit.getServer().getPlayer(event.getMessage().getContentRaw());
            if (p == null) {
                if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("pl_PL")) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > Musisz być online aby odebrać nagrodę!").queue();
                    return;
                } else if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("en_US")) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > You must be online to claim reward!").queue();
                    return;
                } else if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("CustomMessages")) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > " + Main.getInstance().getConfig().getString("CustomMessages.PlayerOffline")).queue();
                    return;
                } else
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > You must be online to claim reward!").queue();
                Bukkit.getConsoleSender().sendMessage("§cEnter correct language in config.yml!");
                return;
            }
            SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
            Date date = new Date();
            Storage.getInstance().getData().set("RewardsReceived." + p.getName() + ".Received", true);
            Storage.getInstance().getData().set("RewardsReceived." + p.getName() + ".DiscordAccountID", event.getMessage().getAuthor().getIdLong());
            Storage.getInstance().getData().set("RewardsReceived." + p.getName() + ".ReceivedDate", formater.format(date));
            Storage.getInstance().saveData();

            String command = Main.getInstance().getConfig().getString("ReceivingAwardsSettings.RewardSuccesCommand");
            command = command.replace("%nick%", event.getMessage().getContentRaw());
            String cmd = command;

            String bcast = Main.getInstance().getConfig().getString("ReceivingAwardsSettings.RewardSuccesBroadcast");
            bcast = bcast.replace("%nick%", event.getMessage().getContentRaw());
            String bcastt = bcast;

            String sukcesDC = Main.getInstance().getConfig().getString("CustomMessages.SuccessfullyReceived");
            sukcesDC = sukcesDC.replace("%nick%", event.getMessage().getContentRaw());
            String dcSukces = sukcesDC;
            Bukkit.getServer().getScheduler().runTask(Main.getPlugin(Main.class), () -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', bcastt));
                if (Main.getInstance().getConfig().getBoolean("ReceivingAwardsSettings.RewardSuccesGiveRole") == true) {
                   event.getGuild().addRoleToMember(event.getMessage().getMember(), event.getGuild().getRoleById(Main.getInstance().getConfig().getString("ReceivingAwardsSettings.RewardSuccesRoleID"))).queue();
                }
            });
                if (Main.getInstance().getConfig().getString("BotSettings.language").equals("pl_PL")) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > Pomyślnie odebrano nagrodę na nick **" + event.getMessage().getContentRaw() + "**!").queue();
                    return;
                } else if (Main.getInstance().getConfig().getString("BotSettings.language").equals("en_US")) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > You successfully received reward to **" + event.getMessage().getContentRaw() + "**!").queue();
                    return;
                } else if (Main.getInstance().getConfig().getString("BotSettings.language").equalsIgnoreCase("CustomMessages")) {
                    event.getChannel().sendMessage(event.getMember().getAsMention() + " > " + dcSukces).queue();
                    return;
                } else
                    Bukkit.getConsoleSender().sendMessage("§cEnter correct language in config.yml!");
                event.getChannel().sendMessage(event.getMember().getAsMention() + " > You successfully received reward to **" + event.getMessage().getContentRaw() + "**!").queue();
                return;
        }
    }
}
