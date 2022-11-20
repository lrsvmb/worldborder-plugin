package com.lrsvmb;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class NewDayEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final World world;

    public NewDayEvent(World w) {
        world = w;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public World getWorld() {
        return world;
    }
}
