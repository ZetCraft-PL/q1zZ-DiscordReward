package pl.q1zz.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import pl.q1zz.DiscordRewards;
import pl.q1zz.listeners.RewardClaim;
import pl.q1zz.varables.Config;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;

public class BotManager {

    public static JDA jda;

    public static void registerListeners() {
        jda.addEventListener(new RewardClaim());
    }

    public static void setStatus(String status) {
        if(!status.isEmpty()) {
            jda.getPresence().setActivity(Activity.playing(status));
        }
    }

    public static void startBot() throws LoginException, InterruptedException {
        JDABuilder builder = JDABuilder.createDefault(Config.TOKEN);

        jda = builder.build();

        registerListeners();
        setStatus(Config.STATUS);

        jda.awaitReady();

        DiscordRewards.getInstance().log("Bot was successfully started!");
    }

    public static void stopBot() {
        if(jda != null) {
            jda.shutdownNow();
            DiscordRewards.getInstance().log("Successful disconnected from JDA!");
        }
    }
}

