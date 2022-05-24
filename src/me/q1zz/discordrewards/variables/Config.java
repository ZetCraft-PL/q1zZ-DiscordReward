package me.q1zz.discordrewards.variables;

import me.q1zz.discordrewards.DiscordReward;
import me.q1zz.discordrewards.discord.BotManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.List;

public class Config {

    public static String
            SETTINGS_NICKNAME_REGEX, SETTINGS_DISCORD_CHANNEL_ID,
            MESSAGE_EMBED_WRONG_NICKNAME_TITLE, MESSAGE_EMBED_WRONG_NICKNAME_DESCRIPTION, MESSAGE_EMBED_WRONG_NICKNAME_COLOR, MESSAGE_EMBED_WRONG_NICKNAME_THUMBNAIL_URL,
            MESSAGE_EMBED_PLAYER_OFFLINE_TITLE, MESSAGE_EMBED_PLAYER_OFFLINE_DESCRIPTION, MESSAGE_EMBED_PLAYER_OFFLINE_COLOR, MESSAGE_EMBED_PLAYER_OFFLINE_THUMBNAIL_URL,
            MESSAGE_EMBED_ALREADY_RECEIVED_TITLE, MESSAGE_EMBED_ALREADY_RECEIVED_DESCRIPTION,MESSAGE_EMBED_ALREADY_RECEIVED_COLOR, MESSAGE_EMBED_ALREADY_RECEIVED_THUMBNAIL_URL,
            MESSAGE_EMBED_SUCCESSFULLY_RECEIVED_TITLE, MESSAGE_EMBED_SUCCESSFULLY_RECEIVED_DESCRIPTION, MESSAGE_EMBED_SUCCESSFULLY_RECEIVED_COLOR, MESSAGE_EMBED_SUCCESSFULLY_RECEIVED_THUMBNAIL_URL;
    public static List<String>
            REWARDS_COMMANDS, REWARDS_ROLES, REWARDS_BROADCAST;
    public static void configureVariables() {

        SETTINGS_NICKNAME_REGEX = DiscordReward.getInstance().getConfig().getString("settings.nickname-regex");
        SETTINGS_DISCORD_CHANNEL_ID = DiscordReward.getInstance().getConfig().getString("settings.discordChannelID");


        REWARDS_COMMANDS = DiscordReward.getInstance().getConfig().getStringList("rewards.commands");
        REWARDS_ROLES = DiscordReward.getInstance().getConfig().getStringList("rewards.roles");
        REWARDS_BROADCAST = DiscordReward.getInstance().getConfig().getStringList("rewards.broadcast");

        //wrongNickname
        MESSAGE_EMBED_WRONG_NICKNAME_TITLE = DiscordReward.getInstance().getConfig().getString("messages.embeds.wrongNickname.title");
        MESSAGE_EMBED_WRONG_NICKNAME_DESCRIPTION = DiscordReward.getInstance().getConfig().getString("messages.embeds.wrongNickname.description");
        MESSAGE_EMBED_WRONG_NICKNAME_COLOR = DiscordReward.getInstance().getConfig().getString("messages.embeds.wrongNickname.color");
        MESSAGE_EMBED_WRONG_NICKNAME_THUMBNAIL_URL = DiscordReward.getInstance().getConfig().getString("messages.embeds.wrongNickname.color");

        //playerOffline
        MESSAGE_EMBED_PLAYER_OFFLINE_TITLE = DiscordReward.getInstance().getConfig().getString("messages.embeds.playerOffline.title");
        MESSAGE_EMBED_PLAYER_OFFLINE_DESCRIPTION = DiscordReward.getInstance().getConfig().getString("messages.embeds.playerOffline.description");
        MESSAGE_EMBED_PLAYER_OFFLINE_COLOR = DiscordReward.getInstance().getConfig().getString("messages.embeds.playerOffline.color");
        MESSAGE_EMBED_PLAYER_OFFLINE_THUMBNAIL_URL = DiscordReward.getInstance().getConfig().getString("messages.embeds.playerOffline.thumbnail_url");

        //alreadyReceived
        MESSAGE_EMBED_ALREADY_RECEIVED_TITLE = DiscordReward.getInstance().getConfig().getString("messages.embeds.alreadyReceived.title");
        MESSAGE_EMBED_ALREADY_RECEIVED_DESCRIPTION = DiscordReward.getInstance().getConfig().getString("messages.embeds.alreadyReceived.description");
        MESSAGE_EMBED_ALREADY_RECEIVED_COLOR = DiscordReward.getInstance().getConfig().getString("messages.embeds.alreadyReceived.color");
        MESSAGE_EMBED_ALREADY_RECEIVED_THUMBNAIL_URL = DiscordReward.getInstance().getConfig().getString("messages.embeds.alreadyReceived.thumbnail_url");

        //successfullyReceived
        MESSAGE_EMBED_SUCCESSFULLY_RECEIVED_TITLE = DiscordReward.getInstance().getConfig().getString("messages.embeds.successfulReceived.title");
        MESSAGE_EMBED_SUCCESSFULLY_RECEIVED_DESCRIPTION = DiscordReward.getInstance().getConfig().getString("messages.embeds.successfulReceived.description");
        MESSAGE_EMBED_SUCCESSFULLY_RECEIVED_COLOR = DiscordReward.getInstance().getConfig().getString("messages.embeds.successfulReceived.color");
        MESSAGE_EMBED_SUCCESSFULLY_RECEIVED_THUMBNAIL_URL = DiscordReward.getInstance().getConfig().getString("messages.embeds.successfulReceived.thumbnail_url");


    }
    public static MessageEmbed getWrongNicknameEmbed(net.dv8tion.jda.api.entities.User user) {
        EmbedBuilder embed_wrongNickname = new EmbedBuilder();
        embed_wrongNickname.setTitle(MESSAGE_EMBED_WRONG_NICKNAME_TITLE);
        embed_wrongNickname.setDescription(MESSAGE_EMBED_WRONG_NICKNAME_DESCRIPTION
                        .replace("%mention%", user.getAsMention())
                        .replace("%newline%", "\n"));
        embed_wrongNickname.setColor(Color.decode(MESSAGE_EMBED_WRONG_NICKNAME_COLOR));
        String embed_wrongNickname_THUMBNAIL_URL = MESSAGE_EMBED_WRONG_NICKNAME_THUMBNAIL_URL;
        if (!embed_wrongNickname_THUMBNAIL_URL.isEmpty()) {
            embed_wrongNickname.setThumbnail(embed_wrongNickname_THUMBNAIL_URL);
        }
        embed_wrongNickname.setFooter(BotManager.getJDA().getSelfUser().getName(), BotManager.getJDA().getSelfUser().getAvatarUrl());

        return embed_wrongNickname.build();
    }
    public static MessageEmbed getPlayerOfflineEmbed(net.dv8tion.jda.api.entities.User user) {
        EmbedBuilder embed_playerOffline = new EmbedBuilder();
        embed_playerOffline.setTitle(MESSAGE_EMBED_PLAYER_OFFLINE_TITLE);
        embed_playerOffline.setDescription(MESSAGE_EMBED_PLAYER_OFFLINE_DESCRIPTION
                        .replace("%mention%", user.getAsMention())
                        .replace("%newline%", "\n"));
        embed_playerOffline.setColor(Color.decode(MESSAGE_EMBED_PLAYER_OFFLINE_COLOR));
        String embed_playerOffline_THUMBNAIL_URL = MESSAGE_EMBED_PLAYER_OFFLINE_THUMBNAIL_URL;
        if (!embed_playerOffline_THUMBNAIL_URL.isEmpty()) {
            embed_playerOffline.setThumbnail(embed_playerOffline_THUMBNAIL_URL);
        }
        embed_playerOffline.setFooter(BotManager.getJDA().getSelfUser().getName(), BotManager.getJDA().getSelfUser().getAvatarUrl());

        return embed_playerOffline.build();
    }
    public static MessageEmbed getAlreadyReceivedEmbed(net.dv8tion.jda.api.entities.User user) {
        EmbedBuilder embed_alreadyReceived = new EmbedBuilder();
        embed_alreadyReceived.setTitle(MESSAGE_EMBED_ALREADY_RECEIVED_TITLE);
        embed_alreadyReceived.setDescription(MESSAGE_EMBED_ALREADY_RECEIVED_DESCRIPTION
                .replace("%mention%", user.getAsMention())
                        .replace("%newline%", "\n"));
        embed_alreadyReceived.setColor(Color.decode(MESSAGE_EMBED_ALREADY_RECEIVED_COLOR));
        String embed_alreadyReceived_THUMBNAIL_URL = MESSAGE_EMBED_ALREADY_RECEIVED_THUMBNAIL_URL;
        if (!embed_alreadyReceived_THUMBNAIL_URL.isEmpty()) {
            embed_alreadyReceived.setThumbnail(embed_alreadyReceived_THUMBNAIL_URL);
        }
        embed_alreadyReceived.setFooter(BotManager.getJDA().getSelfUser().getName(), BotManager.getJDA().getSelfUser().getAvatarUrl());

        return embed_alreadyReceived.build();
    }
    public static MessageEmbed getSuccessfullyReceivedEmbed(net.dv8tion.jda.api.entities.User user, String nick) {
        EmbedBuilder embed_successfullyReceived = new EmbedBuilder();
        embed_successfullyReceived.setTitle(MESSAGE_EMBED_SUCCESSFULLY_RECEIVED_TITLE);
        embed_successfullyReceived.setDescription(MESSAGE_EMBED_SUCCESSFULLY_RECEIVED_DESCRIPTION
                        .replace("%nick%", nick)
                        .replace("%mention%", user.getAsMention())
                        .replace("%newline%", "\n"));
        embed_successfullyReceived.setColor(Color.decode(MESSAGE_EMBED_SUCCESSFULLY_RECEIVED_COLOR));
        String embed_successfullyReceived_THUMBNAIL_URL = MESSAGE_EMBED_SUCCESSFULLY_RECEIVED_THUMBNAIL_URL;
        if (!embed_successfullyReceived_THUMBNAIL_URL.isEmpty()) {
            embed_successfullyReceived.setThumbnail(embed_successfullyReceived_THUMBNAIL_URL);
        }
        embed_successfullyReceived.setFooter(BotManager.getJDA().getSelfUser().getName(), BotManager.getJDA().getSelfUser().getAvatarUrl());

        return embed_successfullyReceived.build();
    }
}
