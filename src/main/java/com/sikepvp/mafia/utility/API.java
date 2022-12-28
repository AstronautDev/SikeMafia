package com.sikepvp.mafia.utility;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class API {

    public static boolean isNumber(String input) {
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isPlayer(String playerName) {
        Player target = Bukkit.getPlayer(playerName);
        OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(playerName);
        if(target != null) return true;
        if(offlineTarget.hasPlayedBefore())
            return true;
        else return false;

    }

}
