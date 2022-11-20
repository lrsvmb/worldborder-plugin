package com.lrsvmb;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Command implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(args.length == 1) return false;
        String commandType = args[1].toLowerCase();
        if(commandType == "config") {
            if(args[2].toLowerCase() == "expansionMessage") {
                DataHandler.expansionMessage = args[3];
            } else if(args[2].toLowerCase() == "shrinkMessage") {
                DataHandler.shrinkMessage = args[3];
            } else if(args[2].toLowerCase() == "expansionAmount") {
                DataHandler.expansionAmount = Integer.parseInt(args[3]);
            } else if(args[2].toLowerCase() == "shrinkAmount") {
                DataHandler.shrinkAmount = Integer.parseInt(args[3]);
            } else if(args[2].toLowerCase() == "shrinkAmount") {
                DataHandler.doOfflineCycle = Boolean.parseBoolean(args[3]);
            }
        }
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> results = new ArrayList<>();
        if(args.length == 1) {
            results.add("config");
        }
        else if(args.length == 2) {
            String[] configs = {"expansionMessage", "shrinkMessage", "expansionAmount", "shrinkAmount"};
            results.addAll(Arrays.asList(configs));
        } else if(args.length == 3) {
            if(args[1] == "expansionBlockAmount" || args[1] == "shrinkBlockAmount") {
                for(int i = 0; i < 100; i++) results.add(String.valueOf(i));
            }
        }
        return results;
    }
}
