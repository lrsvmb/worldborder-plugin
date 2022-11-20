package com.lrsvmb;

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

public class EventListener implements Listener {

    private boolean changed = false;

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
        int score = event.getPlayer().getScoreboard().getObjective("Erweitert").getScore("Blöcke").getScore();
        event.getPlayer().getScoreboard().getObjective("Erweitert").getScore("Blöcke").setScore(score - 1);
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
        if(event.getWorld().getPlayerCount() == 0) return;
        double size = event.getWorld().getWorldBorder().getSize();
        event.getWorld().getWorldBorder().setSize(size + 2, 2);
        Player player = null;
        for (Player p : event.getWorld().getPlayers()) {
            p.sendMessage(ChatColor.GOLD + DataHandler.expansionMessage);
            player = p;
        }
        int score = player.getScoreboard().getObjective("Erweitert").getScore("Blöcke").getScore();
        player.getScoreboard().getObjective("Erweitert").getScore("Blöcke").setScore(score + 2);
    }
}
