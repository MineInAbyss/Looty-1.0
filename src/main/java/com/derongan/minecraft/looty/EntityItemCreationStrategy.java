package com.derongan.minecraft.looty;

import org.bukkit.entity.Entity;

/**
 * Strategy for turning an {@link Item} into an entity (for example an armor stand holding a minecraft item)
 */
public interface EntityItemCreationStrategy {
    Entity createEntity(Item item);
}
