package com.lrsvmb;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class WorldBorder extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Startup logic started");
        try {
            PluginCommand command = getCommand("wb");
            if(command == null) {
                getLogger().log(Level.SEVERE, "Command 'wb' not found");
                return;
            }
            getLogger().info("Initialized command");
            command.setExecutor(new CommandHandler());
            NewDayEvent event = new NewDayEvent(Bukkit.getWorld("world"));
            new EventListener(this);
            getLogger().info("Created EventHandlers");
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                World world = Bukkit.getWorld("world");
                if(world == null) {
                    getLogger().log(Level.SEVERE, "World not found");
                    return;
                }
                if(!DataHandler.hasDetectedNewDay && world.isDayTime()) {
                    try {
                        Bukkit.getPluginManager().callEvent(event);
                        DataHandler.hasDetectedNewDay = true;
                    } catch (IllegalStateException e) {
                        getLogger().log(Level.WARNING, e.toString());
                    }
                } else if(DataHandler.hasDetectedNewDay && !world.isDayTime()) {
                    DataHandler.hasDetectedNewDay = false;
                }
            }, 0, 10);
            getLogger().info("Created repeating background task");
        } catch (Exception e) {
            getLogger().log(Level.WARNING, e.toString());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Shutting down...");
        Bukkit.getScheduler().cancelTasks(this);
        getLogger().info("Successfully shut down!");
    }
}
