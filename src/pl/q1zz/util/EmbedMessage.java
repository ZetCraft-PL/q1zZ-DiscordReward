package pl.q1zz.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;


public class EmbedMessage {

    public static void SendEmbed(TextChannel channel, String title, String hexColor, String description) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(title, null);
        embed.setColor(Color.decode(hexColor));
        embed.setDescription(description);
        channel.sendMessage(embed.build()).queue();
    }
}
