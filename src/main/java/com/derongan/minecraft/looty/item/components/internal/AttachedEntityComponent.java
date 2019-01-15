package com.derongan.minecraft.looty.item.components.internal;

import org.bukkit.entity.Entity;

/**
 * Internal use. Holds a bukkit entity that represents a projectile/?
 */
public class AttachedEntityComponent implements InternalComponent {
    public Entity entity;

    public AttachedEntityComponent(Entity entity) {
        this.entity = entity;
    }
}
