package me.q1zz.discordrewards.util;

import net.md_5.bungee.api.ChatColor;

public class FixColor {

    public static String fix(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
