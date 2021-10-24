package pl.q1zz.objects;

import pl.q1zz.DiscordRewards;

import java.util.List;

public class Config {

    public static String CHANNEL_ID = DiscordRewards.getInstance().getConfig().getString("settings.channelid");

    public static List<String> COMMANDS = DiscordRewards.getInstance().getConfig().getStringList("reward.commands");
    public static List<String> BROADCAST = DiscordRewards.getInstance().getConfig().getStringList("reward.broadcast");

    //wrongNickname
    public static String WRONG_NICKNAME_EMBED_TITLE = DiscordRewards.getInstance().getConfig().getString("messages.embeds.wrongNickname.title");
    public static String WRONG_NICKNAME_EMBED_DESCRIPTION = DiscordRewards.getInstance().getConfig().getString("messages.embeds.wrongNickname.description").replace("%newline%", "\n");
    public static String WRONG_NICKNAME_EMBED_COLOR = DiscordRewards.getInstance().getConfig().getString("messages.embeds.wrongNickname.color");
    //playerOffline
    public static String PLAYER_OFFLINE_EMBED_TITLE = DiscordRewards.getInstance().getConfig().getString("messages.embeds.playerOffline.title");
    public static String PLAYER_OFFLINE_EMBED_DESCRIPTION = DiscordRewards.getInstance().getConfig().getString("messages.embeds.playerOffline.description").replace("%newline%", "\n");
    public static String PLAYER_OFFLINE_EMBED_COLOR = DiscordRewards.getInstance().getConfig().getString("messages.embeds.playerOffline.color");
    //rewardAlreadyClaimed
    public static String REWARD_ALREADY_CLAIMED_EMBED_TITLE = DiscordRewards.getInstance().getConfig().getString("messages.embeds.alreadyReceived.title");
    public static String REWARD_ALREADY_CLAIMED_DESCRIPTION = DiscordRewards.getInstance().getConfig().getString("messages.embeds.alreadyReceived.description").replace("%newline%", "\n");
    public static String REWARD_ALREADY_CLAIMED_COLOR = DiscordRewards.getInstance().getConfig().getString("messages.embeds.alreadyReceived.color");
    //successfulReceived
    public static String SUCCES_RECEIVED_EMBED_TITLE = DiscordRewards.getInstance().getConfig().getString("messages.embeds.successfulReceived.title");
    public static String SUCCES_RECEIVED_DESCRIPTION = DiscordRewards.getInstance().getConfig().getString("messages.embeds.successfulReceived.description").replace("%newline%", "\n");
    public static String SUCCES_RECEIVED_COLOR = DiscordRewards.getInstance().getConfig().getString("messages.embeds.successfulReceived.color");

}
