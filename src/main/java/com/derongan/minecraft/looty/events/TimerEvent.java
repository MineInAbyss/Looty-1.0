package com.derongan.minecraft.looty.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TimerEvent extends Event {
    private long ticks;

    public TimerEvent(long ticks) {
        this.ticks = ticks;
    }

    public long tickCount() {
        return ticks;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
