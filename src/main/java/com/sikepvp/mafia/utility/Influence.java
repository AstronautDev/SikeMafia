package com.sikepvp.mafia.utility;

import com.sikepvp.mafia.Mafia;

import java.util.HashMap;
import java.util.UUID;

public class Influence {

    //Load Data into Map from influence.yml
    private HashMap<UUID, Integer> influenceMap = new HashMap<UUID, Integer>();

    Mafia plugin;
    public Influence(Mafia plugin) {
        this.plugin = plugin;
    }

    public int getInfluence(UUID playerUUID) {
        if(hasInfluence(playerUUID)) {
            return influenceMap.get(playerUUID);
        } else {
            return -1;
        }
    }

    public void setInfluence(UUID playerUUID, int influenceAmount) {
        if(hasInfluence(playerUUID)) {
            influenceMap.put(playerUUID, Math.round(influenceAmount));
        } else {
            return;
        }
    }

    public void addInfluence(UUID playerUUID, int influenceAmount) {
        if(hasInfluence(playerUUID)) {
            influenceMap.put(playerUUID, getInfluence(playerUUID) + Math.round(influenceAmount));
        } else {
            return;
        }
    }

    public boolean hasInfluence(UUID playerUUID) {
        if(influenceMap.containsKey(playerUUID)) {
            return true;
        } else {
            return false;
        }
    }
}
