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
    public BankCommand(Mafia plugin) {
        this.plugin = plugin;
        this.cashUtil = new Cash(plugin);

        plugin.getCommand("Bank").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(cmd.getLabel().equalsIgnoreCase("Bank")) {
                if(args.length == 0) {
                    p.sendMessage("Bank Cmds Here");
                } else if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("Open")) {
                        if(cashUtil.hasCash(p.getUniqueId())) {
                            p.sendMessage("You already have account stoopid");
                            return false;
                        } else {
                            cashUtil.openBankAccount(p.getUniqueId());
                            p.sendMessage("You now have bank n stuff");
                            return true;
                        }
                    } else if(args[0].equalsIgnoreCase("Balance")) {
                        if(cashUtil.hasCash(p.getUniqueId())) {
                            p.sendMessage(ChatColor.GREEN + "Balance: $" + cashUtil.displayBalance(cashUtil.getCash(p.getUniqueId())));
                            return true;
                        } else {
                            p.sendMessage("u dont even have account bru");
                            return false;
                        }
                    } else if(args[0].equalsIgnoreCase("Close")) {
                        if(cashUtil.hasCash(p.getUniqueId())) {
                            p.sendMessage(ChatColor.YELLOW + "Bank account now closed.");
                            cashUtil.closeBankAccount(p.getUniqueId());
                        } else {
                            p.sendMessage(ChatColor.RED + "You don't have a bank account.");
                            return false;
                        }
                    }
                } else if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("Set")) {
                        if(API.isNumber(args[1])) {
                            cashUtil.setCash(p.getUniqueId(), Double.parseDouble(args[1]));
                            p.sendMessage(ChatColor.YELLOW + "Your balance has been set to " + cashUtil.displayBalance(cashUtil.getCash(p.getUniqueId())));
                        } else {
                            p.sendMessage("Input was: " + args[1]);
                        }
                    }
                }
            }
        } else {
            return false;
        }
        return false;
    }
}
