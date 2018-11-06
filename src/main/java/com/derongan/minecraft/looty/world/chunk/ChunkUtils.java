package com.derongan.minecraft.looty.world.chunk;

import org.bukkit.Chunk;

public class ChunkUtils {
    //TODO
    public static SimpleChunk getChunkForLocation(SimpleLocation location){
        return new SimpleChunk(location.getX()/16, location.getZ()/16, location.getWorld());
    }

    public static SimpleChunk toSimpleChunk(Chunk chunk) {
        return new SimpleChunk(chunk.getX(), chunk.getZ(), chunk.getWorld().getName());
    }
}
