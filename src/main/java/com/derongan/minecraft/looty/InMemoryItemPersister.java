package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.world.SimpleChunk;
import com.derongan.minecraft.looty.world.ChunkUtils;

import java.util.*;

public class InMemoryItemPersister implements ItemPersiter {
    Map<SimpleChunk, Map<Location, Item>> chunkMap = new HashMap<>();

    @Override
    public Collection<Item> getFromChunk(SimpleChunk simpleChunk) {
        return chunkMap.getOrDefault(simpleChunk, Collections.emptyMap()).values();
    }

    @Override
    public void create(Item item) {
        SimpleChunk simpleChunk = ChunkUtils.getChunkForLocation(item.getLocation());

        Map<Location, Item> itemMap = this.chunkMap.computeIfAbsent(simpleChunk, (a)->new HashMap<Location, Item>());

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

        Map<Location, Item> itemMap = this.chunkMap.computeIfAbsent(simpleChunk, (a)->new HashMap<Location, Item>());

        if(itemMap.get(item.getLocation()) != null){
            itemMap.remove(item.getLocation());
        }
    }

    @Override
    public Optional<Item> getFromLocation(Location location) {
        SimpleChunk simpleChunk = ChunkUtils.getChunkForLocation(location);

        if (chunkMap.containsKey(simpleChunk)) {
            return Optional.ofNullable(chunkMap.get(simpleChunk).get(location));
        }

        return Optional.empty();
    }
}
