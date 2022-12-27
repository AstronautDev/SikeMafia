package com.sikepvp.mafia;

import com.ibm.icu.impl.locale.XCldrStub;
import com.sikepvp.mafia.commands.BankCommand;
import com.sikepvp.mafia.commands.Mansion;
import com.sikepvp.mafia.utility.Cash;
import com.sikepvp.mafia.utility.Data;
import com.sikepvp.mafia.utility.Influence;
import com.sikepvp.mafia.utility.SchematicLoader;
import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class Mafia extends JavaPlugin {

    String prefix = "[" + ChatColor.RED + "MAFIA" + ChatColor.WHITE + "] ";
    ConsoleCommandSender console = getServer().getConsoleSender();

    SchematicLoader schematicLoader;
    Cash cash;
    Influence influence;
    Data data;

    boolean disableSuccess = true;

    public void onEnable() {
        if(!checkDependencies()) {
            console.sendMessage(prefix + "IMPORTANT DEPENDENCIES MISSING -- MAIN FUNCTIONALITY DISABLED");
        }
        postSchematics();
        loadClasses();
        loadCommands();
    }

    public void onDisable() {
        if(disableSuccess) {
            console.sendMessage(prefix + "SUCCESSFULLY DISABLED");
        } else {
            console.sendMessage(prefix + "ERROR WHILE SAFELY DISABLING");
        }
    }

    private void loadClasses() {
        schematicLoader = new SchematicLoader(this);
        cash = new Cash(this);
        influence = new Influence(this);
        data = new Data(this);

        data.saveInfluence();
        data.saveCash();
    }

    private void loadCommands() {
        Mansion mansion = new Mansion(this);
        BankCommand bankCommand = new BankCommand(this);
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

}
