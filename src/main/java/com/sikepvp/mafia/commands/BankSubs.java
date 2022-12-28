package com.sikepvp.mafia.commands;

import com.sikepvp.mafia.Mafia;
import com.sikepvp.mafia.utility.API;
import com.sikepvp.mafia.utility.Cash;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BankSubs {

    Mafia plugin;
    public BankSubs(Mafia plugin) {
        this.plugin = plugin;
    }

    public void executeSingleArgs(Player p, String[] args) {
        if (args[0].equalsIgnoreCase("Open")) {
            if(p.hasPermission("Mafia.Economy.Player.Open")) {
                if (Cash.hasCash(p.getUniqueId())) {
                    p.sendMessage(ChatColor.RED + "You already have a bank account.");
                } else {
                    Cash.openBankAccount(p.getUniqueId());
                    p.sendMessage(ChatColor.YELLOW + "Bank account now open.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Unknown or incomplete command.");
            }
        } else if (args[0].equalsIgnoreCase("Close")) {
            if(p.hasPermission("Mafia.Economy.Player.Close")) {
                if (Cash.hasCash(p.getUniqueId())) {
                    p.sendMessage(ChatColor.YELLOW + "Bank account now closed.");
                    Cash.closeBankAccount(p.getUniqueId());
                } else {
                    p.sendMessage(ChatColor.RED + "You don't have a bank account.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Unknown or incomplete command.");
            }
        } else if (args[0].equalsIgnoreCase("Balance")) {
            if (p.hasPermission("Mafia.Economy.Player.Balance")) {
                if (Cash.hasCash(p.getUniqueId())) {
                    p.sendMessage(ChatColor.GREEN + "Balance: $" + Cash.getCash(p.getUniqueId()));
                } else {
                    p.sendMessage(ChatColor.RED + "You don't have a bank account.");
                }
            }
        } else {
            p.sendMessage(ChatColor.RED + "Unknown or incomplete command.");
        }
    }

    public void executeDoubleArgs(Player p, String[] args) {
        if (args[0].equalsIgnoreCase("Open")) {
            if(p.hasPermission("Mafia.Economy.Admin.Open")) {
                if (API.isPlayer((args[1]))) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (!Cash.getCashMap().containsKey(target.getUniqueId())) {
                            Cash.openBankAccount(target.getUniqueId());
                            p.sendMessage(ChatColor.YELLOW + "Account opened for " + target.getName());
                            target.sendMessage(ChatColor.YELLOW + "A bank account has been opened for you.");
                        } else {
                            p.sendMessage(ChatColor.RED + target.getName() + " already has an account.");
                        }
                    } else {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        if (!Cash.getCashMap().containsKey(target.getUniqueId())) {
                            Cash.openBankAccount(target.getUniqueId());
                            p.sendMessage(ChatColor.YELLOW + "Account opened for " + target.getName());
                        } else {
                            p.sendMessage(ChatColor.RED + target.getName() + " already has an account.");
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + args[1] + " hasn't played here before.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Unknown or incomplete command.");
            }
        } else if (args[0].equalsIgnoreCase("Close")) {
            if(p.hasPermission("Mafia.Economy.Admin.Close")) {
                if (API.isPlayer((args[1]))) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (Cash.getCashMap().containsKey(target.getUniqueId())) {
                            Cash.closeBankAccount(target.getUniqueId());
                            p.sendMessage(ChatColor.YELLOW + "Account closed for " + target.getName());
                            target.sendMessage(ChatColor.YELLOW + "Your bank account has been closed for you.");
                        } else {
                            p.sendMessage(ChatColor.RED + target.getName() + " does not have an account.");
                        }
                    } else {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        if (Cash.getCashMap().containsKey(target.getUniqueId())) {
                            Cash.closeBankAccount(target.getUniqueId());
                            p.sendMessage(ChatColor.YELLOW + "Account closed for " + target.getName());
                        } else {
                            p.sendMessage(ChatColor.RED + target.getName() + " does not have an account.");
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + args[1] + " hasn't played here before.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Unknown or incomplete command.");
            }
        } else if (args[0].equalsIgnoreCase("Set")) {
            if (p.hasPermission("Mafia.Economy.Admin.Set")) {
                if (API.isNumber(args[1])) {
                    Cash.setCash(p.getUniqueId(), Double.parseDouble(args[1]));
                    p.sendMessage(ChatColor.YELLOW + "Your balance has been set to " + Cash.displayBalance(Cash.getCash(p.getUniqueId())));
                } else {
                    p.sendMessage(ChatColor.RED + "Oops! Please specify an amount.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Unknown or incomplete command.");
            }
        }   else if (args[0].equalsIgnoreCase("Balance")) {
            if(p.hasPermission("Mafia.Economy.Admin.Balance")) {
                if (API.isPlayer((args[1]))) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (Cash.getCashMap().containsKey(target.getUniqueId())) {
                            p.sendMessage(ChatColor.GREEN + target.getName() + "'s Balance: $" + Cash.getCash(target.getUniqueId()));
                        } else {
                            p.sendMessage(ChatColor.RED + target.getName() + " does not have an account.");
                        }
                    } else {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        if (Cash.getCashMap().containsKey(target.getUniqueId())) {
                            p.sendMessage(ChatColor.GREEN + target.getName() + "'s Balance: $" + Cash.getCash(target.getUniqueId()));
                        } else {
                            p.sendMessage(ChatColor.RED + target.getName() + " does not have an account.");
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + args[1] + " hasn't played here before.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Unknown or incomplete command.");
            }
        }
    }
}
