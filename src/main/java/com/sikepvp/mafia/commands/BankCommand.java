package com.sikepvp.mafia.commands;

import com.sikepvp.mafia.Mafia;
import com.sikepvp.mafia.utility.Cash;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankCommand implements CommandExecutor {

    Mafia plugin;
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
                            p.sendMessage(ChatColor.GREEN + "Balance: $" + cashUtil.getCash(p.getUniqueId()));
                            return true;
                        } else {
                            p.sendMessage("u dont even have account bru");
                            return false;
                        }
                    } else if(args[0].equalsIgnoreCase("Set")) {
                        if(cashUtil.hasCash(p.getUniqueId())) {
                            cashUtil.depositCash(p.getUniqueId(), 566.72834);
                            p.sendMessage("added heaps of dosh to ur account");
                            return true;
                        } else {
                            p.sendMessage("no acc bro fr");
                            return false;
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
