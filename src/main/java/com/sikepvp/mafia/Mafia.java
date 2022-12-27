package com.sikepvp.mafia;

import com.sikepvp.mafia.commands.BankCommand;
import com.sikepvp.mafia.commands.Mansion;
import com.sikepvp.mafia.data.LoadData;
import com.sikepvp.mafia.listener.JoinQuitListener;
import com.sikepvp.mafia.utility.Cash;
import com.sikepvp.mafia.utility.Data;
import com.sikepvp.mafia.utility.Influence;
import com.sikepvp.mafia.utility.SchematicLoader;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Mafia extends JavaPlugin {

    String prefix = "[" + ChatColor.RED + "MAFIA" + ChatColor.WHITE + "] ";
    ConsoleCommandSender console = getServer().getConsoleSender();

    SchematicLoader schematicLoader;
    Cash cash;
    Influence influence;
    Data data;
    LoadData loadData;

    boolean disableSuccess = true;

    public void onEnable() {
        if(!checkDependencies()) {
            console.sendMessage(prefix + "IMPORTANT DEPENDENCIES MISSING -- MAIN FUNCTIONALITY DISABLED");
        }
        postSchematics();
        loadClasses();
        loadCommands();
        loadListeners();

        for(Player p : Bukkit.getOnlinePlayers()) {
            LoadData.loadCashData(p.getUniqueId());
        }
        console.sendMessage("Loaded data for all players.");
    }

    public void onDisable() {
        if(disableSuccess) {
            console.sendMessage(prefix + "SUCCESSFULLY DISABLED");
        } else {
            console.sendMessage(prefix + "ERROR WHILE SAFELY DISABLING");
        }

        for(Player p : Bukkit.getOnlinePlayers()) {
            LoadData.saveCashData(p.getUniqueId(), Cash.getCash(p.getUniqueId()));
            Cash.getCashMap().remove(p.getUniqueId());
        }
        console.sendMessage("Saved data for all players.");
    }

    private void loadClasses() {
        schematicLoader = new SchematicLoader(this);
        cash = new Cash(this);
        influence = new Influence(this);
        data = new Data(this);
        loadData = new LoadData(this);

        data.saveInfluence();
        data.saveCash();
    }

    private void loadCommands() {
        Mansion mansion = new Mansion(this);
        BankCommand bankCommand = new BankCommand(this);
    }

    private void loadListeners() {
        JoinQuitListener joinQuitListener = new JoinQuitListener(this);
    }

    private void postSchematics() {
        try {
            FileUtils.copyToFile(this.getResource("Mansion1.schem"), new File(getDataFolder().getAbsolutePath() + "/schematics/Mansion1.schem"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkDependencies() {
        //WorldEdit
        if(getServer().getPluginManager().getPlugin("WorldEdit") != null) {
            return true;
        }
        return false;
    }

    public ConsoleCommandSender getConsole() {
        return console;
    }

}
