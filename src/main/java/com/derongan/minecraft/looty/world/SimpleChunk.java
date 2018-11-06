package com.derongan.minecraft.looty.world;

/**
 * Instead of using {@link org.bukkit.Chunk} we create
 * a chunk does not require a world instance but rather just a world name
 * Additionally it does not provide access to blocks
 */
public interface SimpleChunk {
    int getX();
    int getZ();

    String getWorld();
}
