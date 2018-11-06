package com.derongan.minecraft.looty.world;

import com.derongan.minecraft.looty.Location;
import org.bukkit.Chunk;

public class ChunkUtils {
    //TODO
    public static SimpleChunk getChunkForLocation(Location location){
        return new SimpleChunkImpl(location.getX()/16, location.getZ()/16, location.getWorld());
    }

    public static SimpleChunk toSimpleChunk(Chunk chunk) {
        return new SimpleChunkImpl(chunk.getX(), chunk.getZ(), chunk.getWorld().getName());
    }
}
