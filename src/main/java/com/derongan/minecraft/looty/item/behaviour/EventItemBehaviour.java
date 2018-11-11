package com.derongan.minecraft.looty.item.behaviour;

import org.bukkit.event.Event;

/**
 * Behaviour to be fired based on an event
 */
public interface EventItemBehaviour<T extends Event> extends ItemBehaviour {
    /**
     * Gets the event this behaviour is activated by
     * TODO can we simplify this for items that fire at multiple events?
     */
    Class<T> getEvent();

    void onEvent(T event);
}
