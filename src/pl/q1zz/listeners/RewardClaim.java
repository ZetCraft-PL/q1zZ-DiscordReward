package pl.q1zz.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.q1zz.DiscordRewards;
import pl.q1zz.data.LocalData;
import pl.q1zz.objects.User;
import pl.q1zz.util.EmbedMessage;
import pl.q1zz.util.UserUtil;
import pl.q1zz.varables.Config;

import java.util.logging.Level;

public class RewardClaim extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(!event.getChannelType().isGuild()) {
            return;
        }
        if (event.getAuthor().isBot() || event.isWebhookMessage()) {
            return;
        }
        if(event.getChannel().getId().equals(Config.CHANNEL_ID)) {
            String message = event.getMessage().getContentRaw();
            if(message.length() < 3 || message.length() > 16) {
                event.getTextChannel().sendMessageEmbeds(EmbedMessage.createEmbed(Config.WRONG_NICKNAME_EMBED_TITLE, Config.WRONG_NICKNAME_EMBED_COLOR, Config.WRONG_NICKNAME_EMBED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()))).queue();
                return;
            }
            Player player = Bukkit.getPlayer(message);
            if(player == null) {
                event.getTextChannel().sendMessageEmbeds(EmbedMessage.createEmbed(Config.PLAYER_OFFLINE_EMBED_TITLE, Config.PLAYER_OFFLINE_EMBED_COLOR, Config.PLAYER_OFFLINE_EMBED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()))).queue();
                return;
            }
            if(DiscordRewards.getInstance().saveMethod.equals("flat")) {
                if (LocalData.getInstance().getData().getBoolean("RewardsReceived." + message + ".Received")) {
                    event.getTextChannel().sendMessageEmbeds(EmbedMessage.createEmbed(Config.REWARD_ALREADY_CLAIMED_EMBED_TITLE, Config.REWARD_ALREADY_CLAIMED_COLOR, Config.REWARD_ALREADY_CLAIMED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()))).queue();
                    return;
                }
                if (LocalData.getInstance().getData().getString(event.getAuthor().getId()) != null ) {
                    event.getTextChannel().sendMessageEmbeds(EmbedMessage.createEmbed(Config.REWARD_ALREADY_CLAIMED_EMBED_TITLE, Config.REWARD_ALREADY_CLAIMED_COLOR, Config.REWARD_ALREADY_CLAIMED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()))).queue();
                    return;
                }
                LocalData.saveData(player, event.getAuthor().getIdLong());
                executeCommands(player);
                event.getTextChannel().sendMessageEmbeds(EmbedMessage.createEmbed(Config.SUCCES_RECEIVED_EMBED_TITLE, Config.SUCCES_RECEIVED_COLOR, Config.SUCCES_RECEIVED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()).replace("%nick%", player.getName()))).queue();

                giveRole(event.getMember());
                return;
            }
            if(DiscordRewards.getInstance().saveMethod.equals("mysql")) {
                if(!DiscordRewards.getInstance().MySQL) {
                    Bukkit.getLogger().log(Level.INFO, "MySQL is not configured!");
                    return;
                }
                User user = UserUtil.getUser(player.getUniqueId());
                if(user != null) {
                    event.getTextChannel().sendMessageEmbeds(EmbedMessage.createEmbed(Config.REWARD_ALREADY_CLAIMED_EMBED_TITLE, Config.REWARD_ALREADY_CLAIMED_COLOR, Config.REWARD_ALREADY_CLAIMED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()))).queue();
                    return;
                }
               if(Config.DOUBLE_VERIFICATION) {
                   if (UserUtil.getUserFromID(event.getAuthor().getIdLong()) != null) {
                       event.getTextChannel().sendMessageEmbeds(EmbedMessage.createEmbed(Config.REWARD_ALREADY_CLAIMED_EMBED_TITLE, Config.REWARD_ALREADY_CLAIMED_COLOR, Config.REWARD_ALREADY_CLAIMED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()))).queue();
                       return;
                   }
               }
                UserUtil.redeemReward(player.getUniqueId(), event.getAuthor().getIdLong());
                executeCommands(player);

                event.getTextChannel().sendMessageEmbeds(EmbedMessage.createEmbed(Config.SUCCES_RECEIVED_EMBED_TITLE, Config.SUCCES_RECEIVED_COLOR, Config.SUCCES_RECEIVED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()).replace("%nick%", player.getName()))).queue();

                giveRole(event.getMember());
                return;
            }
        }
    }
    private void executeCommands(Player player) {
        for(String command : Config.COMMANDS) {
            Bukkit.getServer().getScheduler().runTask(DiscordRewards.getPlugin(DiscordRewards.class), () -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("%player%", player.getName()));
            });
        }
        for(String msg : Config.BROADCAST) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", player.getName())));
        }
    }
    private void giveRole(Member member) {
        if(!Config.ROLE_ID.equalsIgnoreCase("disable")) {
            Role role = member.getGuild().getRoleById(Config.ROLE_ID);
            if(role == null) {
                Bukkit.getLogger().log(Level.SEVERE, "The role was not given to user " + member.getUser().getAsTag() + " because the role is null!");
                return;
            }
            if(!member.canInteract(role)) {
                Bukkit.getLogger().log(Level.SEVERE, "The role was not given to user " + member.getUser().getAsTag() + " because role member is highest bot role!");
                return;
            }
            member.getGuild().addRoleToMember(member, role);
        }
    }
}
