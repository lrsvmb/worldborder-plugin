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
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.logging.Level;

public class EventListener implements Listener {

    public EventListener(JavaPlugin p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        World world = event.getPlayer().getWorld();
        for (Player p: world.getPlayers()) {
            p.sendMessage(ChatColor.RED + DataHandler.shrinkMessage);
        }
        world.getWorldBorder().setSize(world.getWorldBorder().getSize() - DataHandler.shrinkAmount, 2);
        try {
            Objective objective = event.getPlayer().getScoreboard().getObjective("Erweitert");
            if(objective != null) {
                Score score = objective.getScore("Blöcke");
                score.setScore(score.getScore() - DataHandler.shrinkAmount);
            } else {
                Bukkit.getLogger().log(Level.WARNING, "Objective 'Erweitert' not found.");
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, e.toString());
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(ChatColor.GREEN + "Hello there!");
        event.getPlayer().getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if(event.getPlayer().getWorld().getPlayerCount() == 0) {
            event.getPlayer().getWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, DataHandler.doOfflineCycle);
        }
    }

    @EventHandler
    public void onNewDay(NewDayEvent event) {
        Bukkit.getLogger().info("Trying new day...");
        if(event.getWorld().getPlayerCount() <= 1) return;
        Bukkit.getLogger().info("New day success!");
        double size = event.getWorld().getWorldBorder().getSize();
        event.getWorld().getWorldBorder().setSize(size + DataHandler.expansionAmount, 2);
        for (Player p : event.getWorld().getPlayers()) {
            p.sendMessage(ChatColor.GOLD + DataHandler.expansionMessage);
        }
        Player player = (Player) event.getWorld().getPlayers().toArray()[0];
        try {
            Objective objective = player.getScoreboard().getObjective("Erweitert");
            if(objective != null) {
                Score score = objective.getScore("Blöcke");
                score.setScore(score.getScore() + DataHandler.expansionAmount);
            } else {
                Bukkit.getLogger().log(Level.WARNING, "Objective 'Erweitert' not found.");
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, e.toString());
        }
    }
}
