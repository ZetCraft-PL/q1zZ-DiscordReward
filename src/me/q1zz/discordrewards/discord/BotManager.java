package me.q1zz.discordrewards.discord;

import me.q1zz.discordrewards.DiscordReward;
import me.q1zz.discordrewards.listeners.ChannelMessage;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class BotManager {

    private static JDA jda;

    public static void registerListeners() {
        jda.addEventListener(new ChannelMessage());
    }

    public static void setStatus(String status) {
        if(!status.isEmpty()) {
            jda.getPresence().setActivity(Activity.playing(status));
        }
    }

    public static void startBot(String token, String status) {
        try {
            JDABuilder builder = JDABuilder.createDefault(token);

            jda = builder.build();

            registerListeners();
            setStatus(status);

            jda.awaitReady();

            DiscordReward.getInstance().getLogger().info("Discord bot was successfully started!");

        } catch (LoginException | InterruptedException e) {
            DiscordReward.getInstance().getLogger().severe("Error while enabling discord bot...");
            e.printStackTrace();
        }
    }

    public static void stopBot() {
        if(jda != null) {
            jda.shutdownNow();
            DiscordReward.getInstance().getLogger().info("Discord bot was successfully stopped!");
        }
    }
    public static JDA getJDA() {
        return jda;
    }
}

