package com.lrsvmb;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CommandHandler implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        if(args.length == 1) return false;
        String commandType = args[1];
        if(commandType.equals("config")) {
            switch (args[2]) {
                case "expansionMessage":
                    DataHandler.expansionMessage = args[3];
                    break;
                case "shrinkMessage":
                    DataHandler.shrinkMessage = args[3];
                    break;
                case "expansionAmount":
                    DataHandler.expansionAmount = Integer.parseInt(args[3]);
                    break;
                case "shrinkAmount":
                    DataHandler.shrinkAmount = Integer.parseInt(args[3]);
                    break;
                case "doOfflineCycle":
                    DataHandler.doOfflineCycle = Boolean.parseBoolean(args[3]);
                    break;
                default:
                    return false;
            }
            return true;
        }
        return false;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> results = new ArrayList<>();
        if(args.length == 1) {
            results.add("config");
        }
        else if(args.length == 2) {
            String[] configs = {"expansionMessage", "shrinkMessage", "expansionAmount", "shrinkAmount", "doOfflineCycle"};
            results.addAll(Arrays.asList(configs));
        } else if(args.length == 3) {
            if(args[2].equals("expansionAmount") || args[2].equals("shrinkAmount")) {
                for (int i = 0; i < 50; i++) {
                    results.add(String.valueOf(i));
                }
            } else if(args[2].equals("doOfflineCycle")) {
                results.add("true");
                results.add("false");
            }
        }
        return results;
    }
}
