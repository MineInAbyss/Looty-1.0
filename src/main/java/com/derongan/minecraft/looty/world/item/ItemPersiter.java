package com.derongan.minecraft.looty.world.item;

import com.derongan.minecraft.looty.world.chunk.SimpleLocation;
import com.derongan.minecraft.looty.world.chunk.SimpleChunk;

import java.util.Collection;
import java.util.Optional;

/**
 * Basically a DAO
 */
public interface ItemPersiter {
    void create(Item item);
    void update(Item item);
    void delete(Item item);
    Optional<Item> getFromLocation(SimpleLocation location);
    Collection<Item> getFromChunk(SimpleChunk simpleChunk);
}
