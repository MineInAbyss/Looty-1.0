package com.derongan.minecraft.looty.item.behaviour;

import org.bukkit.event.Event;

public interface TypedItemBehaviour<T extends Event> extends EventItemBehaviour<T> {
    Class<T> getType();
}
