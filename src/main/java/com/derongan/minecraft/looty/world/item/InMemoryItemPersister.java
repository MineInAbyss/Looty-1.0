package com.derongan.minecraft.looty.world.item;

import com.derongan.minecraft.looty.world.chunk.ChunkUtils;
import com.derongan.minecraft.looty.world.chunk.SimpleLocation;
import com.derongan.minecraft.looty.world.chunk.SimpleChunk;

import java.util.*;

public class InMemoryItemPersister implements ItemPersiter {
    Map<SimpleChunk, Map<SimpleLocation, Item>> chunkMap = new HashMap<>();

    @Override
    public Collection<Item> getFromChunk(SimpleChunk simpleChunk) {
        return chunkMap.getOrDefault(simpleChunk, Collections.emptyMap()).values();
    }

    @Override
    public void create(Item item) {
        SimpleChunk simpleChunk = ChunkUtils.getChunkForLocation(item.getLocation());

        Map<SimpleLocation, Item> itemMap = this.chunkMap.computeIfAbsent(simpleChunk, (a)->new HashMap<SimpleLocation, Item>());

        if(itemMap.get(item.getLocation()) == null){
            itemMap.put(item.getLocation(), item);
        } else {
            throw new IllegalStateException("You cannot add an item at a location that already contains an item");
        }
    }

    @Override
    public void update(Item item) {
        delete(item);
        create(item);
    }

    @Override
    public void delete(Item item) {
        SimpleChunk simpleChunk = ChunkUtils.getChunkForLocation(item.getLocation());

        Map<SimpleLocation, Item> itemMap = this.chunkMap.computeIfAbsent(simpleChunk, (a)->new HashMap<SimpleLocation, Item>());

        if(itemMap.get(item.getLocation()) != null){
            itemMap.remove(item.getLocation());
        }
    }

    @Override
    public Optional<Item> getFromLocation(SimpleLocation location) {
        SimpleChunk simpleChunk = ChunkUtils.getChunkForLocation(location);

        if (chunkMap.containsKey(simpleChunk)) {
            return Optional.ofNullable(chunkMap.get(simpleChunk).get(location));
        }

        return Optional.empty();
    }
}
