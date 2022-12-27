package com.sikepvp.mafia.commands;

import com.sikepvp.mafia.Mafia;
import com.sikepvp.mafia.utility.SchematicLoader;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Mansion implements CommandExecutor {

    SchematicLoader mansionLevelOne;

    Mafia plugin;
    public Mansion(Mafia plugin) {
        this.plugin = plugin;

        mansionLevelOne = new SchematicLoader(plugin, "Mansion1");
        plugin.getCommand("mansion").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] arg) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if(cmd.getLabel().equalsIgnoreCase("mansion")) {
            Location playerLoc = p.getLocation();
            mansionLevelOne.pasteSchematic(playerLoc);
            mansionLevelOne.sendCompletionMessage(p);
            p.sendMessage("Spawned Mansion!");
            return true;
        }
        return false;
    }
}
