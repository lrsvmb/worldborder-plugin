package com.lrsvmb;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldBorder extends JavaPlugin {

    private boolean newDay = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Plugin wurde gestartet!");
        getCommand("wb").setExecutor(new Command());
        NewDayEvent event = new NewDayEvent(Bukkit.getWorld("world"));
        new EventListener(this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if(!newDay && Bukkit.getWorld("world").isDayTime()) {
                    Bukkit.getPluginManager().callEvent(event);
                } else if(newDay && !Bukkit.getWorld("world").isDayTime()) {
                    newDay = false;
                }
            }
        }, 0, 10);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin wurde beendet!");
        Bukkit.getScheduler().cancelTasks(this);
    }
}
