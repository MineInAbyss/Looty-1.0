package com.derongan.minecraft.looty.world.item;

import com.derongan.minecraft.looty.world.chunk.SimpleLocation;
import com.derongan.minecraft.looty.world.chunk.SimpleChunk;

import java.util.Collection;
import java.util.Optional;

/**
 * Manager that handles items. This manager does not deal with the
 * physical minecraft world
 */
public interface ItemManager {
    void addItem(Item item);
    void removeItem(Item item);
    Optional<Item> getItem(SimpleLocation location);

    Collection<Item> getItems(SimpleChunk chunk);
}
