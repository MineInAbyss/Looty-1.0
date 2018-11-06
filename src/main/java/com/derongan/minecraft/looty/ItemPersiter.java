package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.world.SimpleChunk;

import java.util.Collection;
import java.util.Optional;

/**
 * Basically a DAO
 */
public interface ItemPersiter {
    void create(Item item);
    void update(Item item);
    void delete(Item item);
    Optional<Item> getFromLocation(Location location);
    Collection<Item> getFromChunk(SimpleChunk simpleChunk);
}
