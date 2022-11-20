package com.lrsvmb;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class EventListener implements Listener {

    private final JavaPlugin plugin;
    private boolean changed = false;

    public EventListener(JavaPlugin p) {
        plugin = p;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        World world = event.getPlayer().getWorld();
        DataHandler handler = new DataHandler();
        for (Player p: world.getPlayers()) {
            p.sendMessage(ChatColor.RED + handler.shrinkMessage);
        }
        world.getWorldBorder().setSize(world.getWorldBorder().getSize() - handler.shrinkAmount, 2);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                World world = event.getPlayer().getWorld();
                if(world.getTime() >= 6000) {
                    if(changed) return;
                    DataHandler handler = new DataHandler();
                    for (Player p: world.getPlayers()) {
                        p.sendMessage(ChatColor.GOLD + handler.expansionMessage);
                    }
                    world.getWorldBorder().setSize(world.getWorldBorder().getSize() + handler.expansionAmount, 2);
                    event.getPlayer().getScoreboard().getObjective("Erweitert").getScore("Blöcke").setScore(event.getPlayer().getScoreboard().getObjective("Erweitert").getScore("Blöcke") + 2);
                    changed = true;
                } else if (world.getTime() >= 10000) {
                    changed = false;
                }
            }
        }, 0, 100);
        event.getPlayer().sendMessage(ChatColor.GREEN + "Hello there!");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if(DataHandler.doOfflineCycle) {
            event.getPlayer().getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
            return;
        }
        if(event.getPlayer().getWorld().getPlayerCount() <= 1 && !event.getPlayer().getWorld().getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)) {
            event.getPlayer().getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        } else if(event.getPlayer().getWorld().getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)) {
            event.getPlayer().getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        }
        if(event.getPlayer().getWorld().getPlayerCount() == 0) {
            Bukkit.getScheduler().cancelTasks(plugin);
        }
    }
}
