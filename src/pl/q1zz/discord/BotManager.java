package pl.q1zz.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import pl.q1zz.objects.Bot;
import pl.q1zz.reward.RewardClaim;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;

public class BotManager {

    public static JDA jda;

    public static void registerListeners() {
        jda.addEventListener(new RewardClaim());
    }

    public static void setStatus(String status) {
        jda.getPresence().setActivity(Activity.playing(status));
    }

    public static void startBot() {
        try {

            JDABuilder builder = JDABuilder.createDefault(Bot.token);

            jda = builder.build();

            registerListeners();
            setStatus(Bot.status);

            jda.awaitReady();
        } catch (LoginException | InterruptedException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not login to bot token! Details:");
            Bukkit.getLogger().log(Level.SEVERE, " " + e.getMessage() + "");
            return;
        }
    }

    public static void stopBot() {
        if(jda != null) {
            jda.shutdownNow();
            Bukkit.getLogger().log(Level.SEVERE, "Successful disconnected from JDA!");
        }
    }
}

