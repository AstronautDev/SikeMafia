package com.sikepvp.mafia.listener;

import com.sikepvp.mafia.Mafia;
import com.sikepvp.mafia.data.LoadData;
import com.sikepvp.mafia.utility.Cash;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    Mafia plugin;
    public JoinQuitListener(Mafia plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        LoadData.loadCashData(p.getUniqueId());
        plugin.getConsole().sendMessage("Loaded data for " + p.getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        LoadData.saveCashData(p.getUniqueId(), Cash.getCash(p.getUniqueId()));
        if(Cash.getCashMap().containsKey(p.getUniqueId())) Cash.getCashMap().remove(p.getUniqueId());
        plugin.getConsole().sendMessage("Saved data for " + p.getName());
    }

}
