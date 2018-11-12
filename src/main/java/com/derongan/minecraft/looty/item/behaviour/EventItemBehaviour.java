package com.derongan.minecraft.looty.item.behaviour;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * Behaviour to be fired based on an event
 */
@FunctionalInterface
public interface EventItemBehaviour<T extends Event> extends ItemBehaviour {
    void onEvent(T event, Player player);
}
