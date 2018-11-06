package com.derongan.minecraft.looty;

import org.bukkit.Chunk;

/**
 * Manager for real world entities
 */
public interface EntityItemManager {
    void loadEntities(Chunk chunk);
    void unloadEntities(Chunk chunk);
}
