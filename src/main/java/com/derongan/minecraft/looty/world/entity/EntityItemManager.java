package com.derongan.minecraft.looty.world.entity;

import com.derongan.minecraft.looty.Lifecycle;
import org.bukkit.Chunk;

/**
 * Manager for real world entities
 */
public interface EntityItemManager extends Lifecycle {
    void loadEntities(Chunk chunk);
    void unloadEntities(Chunk chunk);
}
