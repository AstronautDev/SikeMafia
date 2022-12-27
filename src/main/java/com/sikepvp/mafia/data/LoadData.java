package com.sikepvp.mafia.data;

import com.sikepvp.mafia.Mafia;
import com.sikepvp.mafia.utility.Cash;
import com.sikepvp.mafia.utility.Data;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LoadData {
    private static Data data;
    private static Mafia plugin;
    public LoadData(Mafia plugin) {
        this.plugin = plugin;
        this.data = new Data(plugin);
    }

    public static void loadCashData(UUID playerUUID) {
        if(data.getCashConfig().contains(playerUUID.toString())) {
            double cashBalance = data.getCashConfig().getDouble(playerUUID.toString());
            Cash.getCashMap().put(playerUUID, cashBalance);
        }
    }

    public static void removeCashData(UUID playerUUID) {
        if(data.getCashConfig().contains(playerUUID.toString())) {
            data.getCashConfig().set(playerUUID.toString(), null);
            data.saveCash();
        }
    }

    public static void saveCashData(UUID playerUUID, double cashBalance) {
        data.getCashConfig().set(playerUUID.toString(), cashBalance);
        data.saveCash();
    }

}
