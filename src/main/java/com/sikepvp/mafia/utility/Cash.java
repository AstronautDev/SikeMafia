package com.sikepvp.mafia.utility;

import com.sikepvp.mafia.Mafia;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cash {

    //Load Data into Map from cash.yml
    private static HashMap<UUID, Double> cashMap = new HashMap<UUID, Double>();

    Mafia plugin;
    public Cash(Mafia plugin) {
        this.plugin = plugin;
    }

    public static double getCash(UUID playerUUID) {
        if(hasCash(playerUUID)) {
            return cashMap.get(playerUUID);
        } else {
            return -1;
        }
    }

    public static void setCash(UUID playerUUID, double cashAmount) {
        if(hasCash(playerUUID)) {
            cashMap.put(playerUUID, roundTwoPlaces(cashAmount));
        } else {
            return;
        }
    }

    public static void depositCash(UUID playerUUID, double depositAmount) {
        if(hasCash(playerUUID)) {
            cashMap.put(playerUUID, getCash(playerUUID) + roundTwoPlaces(depositAmount));
        } else {
            return;
        }
    }

    public static void withdrawCash(UUID playerUUID, double withdrawAmount) {
        if(hasCash(playerUUID)) {
            cashMap.put(playerUUID, getCash(playerUUID) - roundTwoPlaces(withdrawAmount));
        } else {
            return;
        }
    }

    public static void transferToPlayer(UUID fromPlayer, UUID toPlayer, double transferAmount) {
        if(hasCash(fromPlayer) && hasCash(toPlayer)) {
            if(getCash(fromPlayer) >= roundTwoPlaces(transferAmount)) {
                withdrawCash(fromPlayer, roundTwoPlaces(transferAmount));
                depositCash(toPlayer, roundTwoPlaces(transferAmount));
            }
        } else {
            return;
        }
    }

    public static boolean hasCash(UUID playerUUID) {
        if(cashMap.containsKey(playerUUID)) {
            return true;
        } else {
            return false;
        }
    }

    public static void openBankAccount(UUID playerUUID) {
        if(!hasCash(playerUUID)) {
            cashMap.put(playerUUID, 0.00);
        } else {
            return;
        }
    }

    public static void closeBankAccount(UUID playerUUID) {
        if(hasCash(playerUUID)) {
            cashMap.remove(playerUUID);
        } else {
            return;
        }
    }

    //Leaderstats & Checks

    private static double roundTwoPlaces(double amount) {
        BigDecimal a = new BigDecimal(amount);
        return a.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    public static String displayBalance(double amount) {
        Map<String, Double> suffixes = new HashMap<>();
        final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
        suffixes.put("K", 1000.00);
        suffixes.put("M", 1000000.00);
        suffixes.put("B", 1000000000.00);
        suffixes.put("T", 1000000000000.00);

        for(Map.Entry<String, Double> entry : suffixes.entrySet()) {
            if(Math.abs(amount) >= entry.getValue()) {
                amount = amount / entry.getValue();
                return DECIMAL_FORMAT.format(amount) + entry.getKey();
            }
        }
        return DECIMAL_FORMAT.format(amount);
    }

}
