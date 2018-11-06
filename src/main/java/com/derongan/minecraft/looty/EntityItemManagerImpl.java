package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.world.ChunkUtils;
import com.derongan.minecraft.looty.world.SimpleChunk;
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
}
