package pl.q1zz;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.q1zz.commands.Reload;
import pl.q1zz.discord.RewardClaimer;
import pl.q1zz.storage.Storage;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    private static Main instance;
    public static Main getInstance() {
        return instance;
    }
    private static JDA jda;

    @Override
    public void onEnable() {
        instance = this;
        new Reload(this);
        getConfig().options().copyDefaults(true);
        saveConfig();
        startBot();
        if (getInstance().getConfig().getString("StorageSettings.SaveType").equals("flat")) {
            new Storage();
            Storage.getInstance().setup(this);
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "SaveType: " + Main.getInstance().getConfig().getString("StorageSettings.SaveType") + " has not been found! Plugin has been disabled!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
       }
    @Override
    public void onDisable() {
        if (jda != null) {
            jda.shutdownNow();
            Bukkit.getConsoleSender().sendMessage("[q1zZ-DiscordReward] - Successfully logged out!");
        }
    }
    private void startBot() {
        try {
            jda = JDABuilder.createDefault(Main.getInstance().getConfig().getString("BotSettings.token")).build();
            JDABuilder builder = JDABuilder.createDefault(Main.getInstance().getConfig().getString("BotSettings.token"));
            builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
            builder.disableCache(CacheFlag.VOICE_STATE);
            builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
            builder.setMemberCachePolicy(MemberCachePolicy.ALL);
            builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
            builder.enableCache(CacheFlag.ACTIVITY);
            builder.setCompression(Compression.NONE);
            jda.addEventListener(new RewardClaimer());
            Bukkit.getConsoleSender().sendMessage("[q1zZ-Discord] - Plugin was launched successfully!");
            jda.getPresence().setActivity(Activity.playing(getConfig().getString("BotSettings.status")));
        } catch (LoginException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not login to bot token! Details:");
            Bukkit.getLogger().log(Level.SEVERE, " " + e.getMessage() + "");
        }
    }

}
