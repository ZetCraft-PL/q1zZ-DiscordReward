package pl.q1zz.varables;

import pl.q1zz.DiscordRewards;

import java.util.List;

public class Config {


    public static List<String> COMMANDS, BROADCAST;
    public static String CHANNEL_ID, WRONG_NICKNAME_EMBED_TITLE, WRONG_NICKNAME_EMBED_DESCRIPTION,
            WRONG_NICKNAME_EMBED_COLOR, PLAYER_OFFLINE_EMBED_TITLE, PLAYER_OFFLINE_EMBED_DESCRIPTION,
            PLAYER_OFFLINE_EMBED_COLOR, REWARD_ALREADY_CLAIMED_EMBED_TITLE, REWARD_ALREADY_CLAIMED_DESCRIPTION, REWARD_ALREADY_CLAIMED_COLOR
            , SUCCES_RECEIVED_EMBED_TITLE, SUCCES_RECEIVED_DESCRIPTION, SUCCES_RECEIVED_COLOR, TOKEN, STATUS, ROLE_ID;
    public static boolean DOUBLE_VERIFICATION;

    public static void configureVarables() {

         CHANNEL_ID = DiscordRewards.getInstance().getConfig().getString("settings.channelid");
         DOUBLE_VERIFICATION = DiscordRewards.getInstance().getConfig().getBoolean("settings.doubleverification");

         COMMANDS = DiscordRewards.getInstance().getConfig().getStringList("reward.commands");
         BROADCAST = DiscordRewards.getInstance().getConfig().getStringList("reward.broadcast");
         ROLE_ID = DiscordRewards.getInstance().getConfig().getString("reward.roleid");


        //wrongNickname
        WRONG_NICKNAME_EMBED_TITLE = DiscordRewards.getInstance().getConfig().getString("messages.embeds.wrongNickname.title");
        WRONG_NICKNAME_EMBED_DESCRIPTION = DiscordRewards.getInstance().getConfig().getString("messages.embeds.wrongNickname.description").replace("%newline%", "\n");
        WRONG_NICKNAME_EMBED_COLOR = DiscordRewards.getInstance().getConfig().getString("messages.embeds.wrongNickname.color");
        //playerOffline
        PLAYER_OFFLINE_EMBED_TITLE = DiscordRewards.getInstance().getConfig().getString("messages.embeds.playerOffline.title");
        PLAYER_OFFLINE_EMBED_DESCRIPTION = DiscordRewards.getInstance().getConfig().getString("messages.embeds.playerOffline.description").replace("%newline%", "\n");
        PLAYER_OFFLINE_EMBED_COLOR = DiscordRewards.getInstance().getConfig().getString("messages.embeds.playerOffline.color");
        //rewardAlreadyClaimed
        REWARD_ALREADY_CLAIMED_EMBED_TITLE = DiscordRewards.getInstance().getConfig().getString("messages.embeds.alreadyReceived.title");
        REWARD_ALREADY_CLAIMED_DESCRIPTION = DiscordRewards.getInstance().getConfig().getString("messages.embeds.alreadyReceived.description").replace("%newline%", "\n");
        REWARD_ALREADY_CLAIMED_COLOR = DiscordRewards.getInstance().getConfig().getString("messages.embeds.alreadyReceived.color");
        //successfulReceived
        SUCCES_RECEIVED_EMBED_TITLE = DiscordRewards.getInstance().getConfig().getString("messages.embeds.successfulReceived.title");
        SUCCES_RECEIVED_DESCRIPTION = DiscordRewards.getInstance().getConfig().getString("messages.embeds.successfulReceived.description").replace("%newline%", "\n");
        SUCCES_RECEIVED_COLOR = DiscordRewards.getInstance().getConfig().getString("messages.embeds.successfulReceived.color");
        //bot
        TOKEN = DiscordRewards.getInstance().getConfig().getString("bot.token");
        STATUS = DiscordRewards.getInstance().getConfig().getString("bot.status");
    }
}
