package com.derongan.minecraft.looty.world.chunk;

import com.derongan.minecraft.looty.world.entity.EntityItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkListener implements Listener {
    private EntityItemManager entityItemManager;

    public ChunkListener(EntityItemManager entityItemManager) {
        this.entityItemManager = entityItemManager;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent chunkLoadEvent){
        entityItemManager.loadEntities(chunkLoadEvent.getChunk());
    }

    @EventHandler
    private void onChunkUnload(ChunkUnloadEvent chunkUnloadEvent){
        entityItemManager.unloadEntities(chunkUnloadEvent.getChunk());
    }
}
