package com.derongan.minecraft.looty.world.entity;

import org.bukkit.Chunk;

/**
 * Manager for real world entities
 */
public interface EntityItemManager {
    void loadEntities(Chunk chunk);
    void unloadEntities(Chunk chunk);
}
