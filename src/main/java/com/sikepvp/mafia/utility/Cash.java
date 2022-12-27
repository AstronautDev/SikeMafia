package com.sikepvp.mafia.utility;

import com.sikepvp.mafia.Mafia;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.UUID;

public class Cash {

    //Load Data into Map from cash.yml
    private HashMap<UUID, Double> cashMap = new HashMap<UUID, Double>();

    Mafia plugin;
    public Cash(Mafia plugin) {
        this.plugin = plugin;
    }

    public double getCash(UUID playerUUID) {
        if(hasCash(playerUUID)) {
            return cashMap.get(playerUUID);
        } else {
            return -1;
        }
    }

    public void setCash(UUID playerUUID, double cashAmount) {
        if(hasCash(playerUUID)) {
            cashMap.put(playerUUID, roundTwoPlaces(cashAmount));
        } else {
            return;
        }
    }

    public void depositCash(UUID playerUUID, double depositAmount) {
        if(hasCash(playerUUID)) {
            cashMap.put(playerUUID, getCash(playerUUID) + roundTwoPlaces(depositAmount));
        } else {
            return;
        }
    }

    public void withdrawCash(UUID playerUUID, double withdrawAmount) {
        if(hasCash(playerUUID)) {
            cashMap.put(playerUUID, getCash(playerUUID) - roundTwoPlaces(withdrawAmount));
        } else {
            return;
        }
    }

    public void transferToPlayer(UUID fromPlayer, UUID toPlayer, double transferAmount) {
        if(hasCash(fromPlayer) && hasCash(toPlayer)) {
            if(getCash(fromPlayer) >= roundTwoPlaces(transferAmount)) {
                withdrawCash(fromPlayer, roundTwoPlaces(transferAmount));
                depositCash(toPlayer, roundTwoPlaces(transferAmount));
            }
        } else {
            return;
        }
    }

    public boolean hasCash(UUID playerUUID) {
        if(cashMap.containsKey(playerUUID)) {
            return true;
        } else {
            return false;
        }
    }

    public void openBankAccount(UUID playerUUID) {
        if(!hasCash(playerUUID)) {
            cashMap.put(playerUUID, 0.00);
        } else {
            return;
        }
    }

    public void closeBankAccount(UUID playerUUID) {
        if(hasCash(playerUUID)) {
            cashMap.remove(playerUUID);
        } else {
            return;
        }
    }

    //Leaderstats & Checks

    private double roundTwoPlaces(double amount) {
        BigDecimal a = new BigDecimal(amount);
        return a.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

}
