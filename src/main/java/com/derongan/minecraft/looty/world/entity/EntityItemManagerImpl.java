package com.derongan.minecraft.looty.world.entity;

import com.derongan.minecraft.looty.world.chunk.ChunkUtils;
import com.derongan.minecraft.looty.world.chunk.SimpleChunk;
import com.derongan.minecraft.looty.world.entity.creation.EntityItemCreationStrategy;
import com.derongan.minecraft.looty.world.item.Item;
import com.derongan.minecraft.looty.world.item.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityItemManagerImpl implements EntityItemManager {
    private EntityItemCreationStrategy entityItemCreationStrategy;
    private ItemManager itemManager;

    //TODO think about this
    private Map<SimpleChunk, Collection<Entity>> loadedEntities = new HashMap<>();

    public EntityItemManagerImpl(ItemManager itemManager, EntityItemCreationStrategy entityItemCreationStrategy) {
        this.itemManager = itemManager;
        this.entityItemCreationStrategy = entityItemCreationStrategy;
    }

    @Override
    public void loadEntities(Chunk chunk) {
        Collection<Item> items = itemManager.getItems(ChunkUtils.toSimpleChunk(chunk));

        loadedEntities.put(ChunkUtils.toSimpleChunk(chunk),
                items.stream().map(a -> entityItemCreationStrategy.createEntity(a)).collect(Collectors.toList())
        );
    }

    @Override
    public void unloadEntities(Chunk chunk) {
        loadedEntities.getOrDefault(ChunkUtils.toSimpleChunk(chunk), Collections.emptyList()).forEach(Entity::remove);
    }

    @Override
    public void start() {
        Bukkit.getServer().getWorlds().forEach(a->{
            for (Chunk loadedChunk : a.getLoadedChunks()) {
                loadEntities(loadedChunk);
            }
        });
    }

    @Override
    public void stop() {
        Bukkit.getServer().getWorlds().forEach(a->{
            for (Chunk loadedChunk : a.getLoadedChunks()) {
                unloadEntities(loadedChunk);
            }
        });

        //Verification
        loadedEntities.values().forEach(a->{
            if(!a.isEmpty()){
                throw new IllegalStateException("Entities should only exist for loaded chunks");
            }
        });
    }
}
