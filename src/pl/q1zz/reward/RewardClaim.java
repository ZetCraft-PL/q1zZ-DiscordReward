package pl.q1zz.reward;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.q1zz.DiscordRewards;
import pl.q1zz.data.LocalData;
import pl.q1zz.data.MySQLService;
import pl.q1zz.objects.Config;
import pl.q1zz.objects.Reward;
import pl.q1zz.util.EmbedMessage;

import java.util.logging.Level;

public class RewardClaim extends ListenerAdapter {


    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        if(event.getChannel().getId().equals(Config.CHANNEL_ID)) {
            String message = event.getMessage().getContentRaw();
            if (!(message.length() >= 3) && !(message.length() >= 16)) {
                EmbedMessage.SendEmbed(event.getChannel(), Config.WRONG_NICKNAME_EMBED_TITLE, Config.WRONG_NICKNAME_EMBED_COLOR, Config.WRONG_NICKNAME_EMBED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()));
                return;
            }
            Player player = Bukkit.getPlayer(message);
            if(player == null) {
                EmbedMessage.SendEmbed(event.getChannel(), Config.PLAYER_OFFLINE_EMBED_TITLE, Config.PLAYER_OFFLINE_EMBED_COLOR, Config.PLAYER_OFFLINE_EMBED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()));
                return;
            }
            if(DiscordRewards.getInstance().savetype.equals("flat")) {
                if (LocalData.getInstance().getData().getBoolean("RewardsReceived." + event.getMessage().getContentRaw() + ".Received") == true) {
                    EmbedMessage.SendEmbed(event.getChannel(), Config.REWARD_ALREADY_CLAIMED_EMBED_TITLE, Config.REWARD_ALREADY_CLAIMED_COLOR, Config.REWARD_ALREADY_CLAIMED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()));
                    return;
                }
                LocalData.saveData(player, event.getAuthor().getIdLong());
                Reward.claimReward(player);
                EmbedMessage.SendEmbed(event.getChannel(), Config.SUCCES_RECEIVED_EMBED_TITLE, Config.SUCCES_RECEIVED_COLOR, Config.SUCCES_RECEIVED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()).replace("%nick%", player.getName()));

                return;
            }
            if(DiscordRewards.getInstance().savetype.equals("mysql")) {
                if(DiscordRewards.getInstance().mysql != true) {
                    Bukkit.getLogger().log(Level.WARNING, "MySQL is not configured!");
                    return;
                }
                if(MySQLService.isReceived(player.getUniqueId())) {
                    EmbedMessage.SendEmbed(event.getChannel(), Config.REWARD_ALREADY_CLAIMED_EMBED_TITLE, Config.REWARD_ALREADY_CLAIMED_COLOR, Config.REWARD_ALREADY_CLAIMED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()));
                    return;
                }
                MySQLService.UserReceived(player.getUniqueId(), event.getAuthor().getIdLong());
                Reward.claimReward(player);
                EmbedMessage.SendEmbed(event.getChannel(), Config.SUCCES_RECEIVED_EMBED_TITLE, Config.SUCCES_RECEIVED_COLOR, Config.SUCCES_RECEIVED_DESCRIPTION.replace("%mention%", event.getAuthor().getAsMention()).replace("%nick%", player.getName()));
            }
        }
    }
}
