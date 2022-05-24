package me.q1zz.discordrewards.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class Utilis {

    public static boolean hasRole(Member member, Role memberRole) {

        Role findRole = member.getRoles().stream().filter(role -> role.getId().equals(memberRole.getId())).findFirst().orElse(null);

        return findRole != null;
    }
}
