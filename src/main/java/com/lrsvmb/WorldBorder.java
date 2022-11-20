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
        getLogger().info("Plugin wurde gestartet!");
        try {
            PluginCommand command = getCommand("wb");
            if(command == null) {
                getLogger().log(Level.SEVERE, "Command 'wb' not found");
                return;
            }
            command.setExecutor(new CommandHandler());
            NewDayEvent event = new NewDayEvent(Bukkit.getWorld("world"));
            new EventListener(this);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                World world = Bukkit.getWorld("world");
                if(world == null) {
                    getLogger().log(Level.SEVERE, "World not found");
                    return;
                }
                if(!DataHandler.dayState && world.isDayTime()) {
                    try {
                        Bukkit.getPluginManager().callEvent(event);
                    } catch (IllegalStateException e) {
                        getLogger().log(Level.WARNING, e.toString());
                    }
                } else if(DataHandler.dayState && !world.isDayTime()) {
                    DataHandler.dayState = false;
                }
            }, 0, 10);
        } catch (Exception e) {
            getLogger().log(Level.WARNING, e.toString());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin wurde beendet!");
        Bukkit.getScheduler().cancelTasks(this);
    }
}
