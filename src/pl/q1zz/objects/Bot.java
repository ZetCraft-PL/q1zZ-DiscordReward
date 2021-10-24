package pl.q1zz.objects;

import net.dv8tion.jda.api.JDA;
import pl.q1zz.DiscordRewards;

public class Bot {

    public static String token = DiscordRewards.getInstance().getConfig().getString("bot.token");
    public static String status = DiscordRewards.getInstance().getConfig().getString("bot.status");
}
