package com.sikepvp.mafia.commands;

import com.sikepvp.mafia.Mafia;
import com.sikepvp.mafia.utility.API;
import com.sikepvp.mafia.utility.Cash;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankCommand implements CommandExecutor {

    Mafia plugin;
    StringUtils stringUtils;
    Cash cashUtil;
    BankSubs subCommands;
    public BankCommand(Mafia plugin) {
        this.plugin = plugin;
        this.cashUtil = new Cash(plugin);
        subCommands = new BankSubs(plugin);

        plugin.getCommand("Bank").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(cmd.getLabel().equalsIgnoreCase("Bank")) {
                if(args.length == 0) {
                    p.sendMessage("Bank Cmds Here");
                } else if(args.length == 1) subCommands.executeSingleArgs(p, args);
                    else if(args.length == 2) subCommands.executeDoubleArgs(p, args);
            }
        } else {
            return false;
        }
        return false;
    }
}
