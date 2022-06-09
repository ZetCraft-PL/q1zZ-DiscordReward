package me.q1zz.discordrewards.listeners;

import me.q1zz.discordrewards.DiscordReward;
import me.q1zz.discordrewards.discord.BotManager;
import me.q1zz.discordrewards.managers.UserManager;
import me.q1zz.discordrewards.objects.User;
import me.q1zz.discordrewards.util.FixColor;
import me.q1zz.discordrewards.util.Utilis;
import me.q1zz.discordrewards.variables.Config;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ChannelMessage extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (!event.isFromGuild()) {
            return;
        }

        if (event.getAuthor().isBot() || event.isWebhookMessage()) {
            return;
        }

        if(!event.isFromType(ChannelType.TEXT)) {
            return;
        }

        if (!event.getTextChannel().getId().equals(Config.SETTINGS_DISCORD_CHANNEL_ID)) {
            return;
        }

        String message = event.getMessage().getContentRaw();

        if (!message.matches(Config.SETTINGS_NICKNAME_REGEX)) {

            event.getTextChannel().sendMessageEmbeds(Config.getWrongNicknameEmbed(event.getAuthor())).queue();

        } else {

            Player player = Bukkit.getPlayer(message);

            if (player == null) {

                event.getTextChannel().sendMessageEmbeds(Config.getPlayerOfflineEmbed(event.getAuthor())).queue();

            } else {

                User user = UserManager.getUser(player.getUniqueId());

                if (user != null) {

                    event.getTextChannel().sendMessageEmbeds(Config.getAlreadyReceivedEmbed(event.getAuthor())).queue();

                } else {

                    User userFromID = UserManager.getPlayerFromDiscordID(event.getAuthor().getIdLong());

                    if (userFromID != null) {

                        event.getTextChannel().sendMessageEmbeds(Config.getAlreadyReceivedEmbed(event.getAuthor())).queue();

                    } else {

                        Bukkit.getScheduler().runTaskAsynchronously(DiscordReward.getInstance(), () -> {

                            UserManager.createUser(player.getUniqueId(), event.getAuthor().getIdLong());

                            Config.REWARDS_COMMANDS.forEach(command -> Bukkit.getScheduler().runTask(DiscordReward.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()))));

                            Config.REWARDS_BROADCAST.forEach(broadcastMessage -> Bukkit.broadcastMessage(FixColor.fix(broadcastMessage.replace("%player%", player.getName()))));

                            event.getTextChannel().sendMessageEmbeds(Config.getSuccessfullyReceivedEmbed(event.getAuthor(), player.getName())).queue();

                            addRoles(event.getGuild(), event.getMember(), Config.REWARDS_ROLES);

                        });
                    }
                }
            }
        }
    }
    private void addRoles(Guild guild, Member member, List<String> roles) {
        roles.forEach(roleID -> {
            Role role = BotManager.getJDA().getRoleById(roleID);
            if (role == null) {
                DiscordReward.getInstance().getLogger().severe("Cannot give role with id: " + roleID + " to user " + member.getUser().getAsTag() + " (" + member.getId() + ") because role is null!");
            } else if (!Utilis.hasRole(member, role)) {
                if (!guild.getSelfMember().canInteract(role)) {
                    DiscordReward.getInstance().getLogger().severe("Cannot give role with id: " + roleID + " to user " + member.getUser().getAsTag() + " (" + member.getId() + ") because role is higher than the bot role.");
                } else {
                    guild.addRoleToMember(member.getUser(), role).queue();
                }
            }
        });
    }
}
