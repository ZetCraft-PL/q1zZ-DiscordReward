package pl.q1zz.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;


public class EmbedMessage {

    public static MessageEmbed createEmbed(String title, String hexColor, String description) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(title, null);
        embed.setColor(Color.decode(hexColor));
        embed.setDescription(description);

        return embed.build();
    }
}
