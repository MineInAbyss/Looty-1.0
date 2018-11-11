package com.derongan.minecraft.looty.world.entity.creation;

import com.derongan.minecraft.looty.world.item.Item;
import org.bukkit.entity.Entity;

/**
 * Strategy for turning an {@link Item} into an entity (for example an armor stand holding a minecraft item)
 */
public interface EntityItemCreationStrategy {
    Entity createEntity(Item item);
}
