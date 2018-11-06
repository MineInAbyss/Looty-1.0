package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.world.SimpleChunk;

import java.util.Collection;
import java.util.Optional;

/**
 * Manager that handles items. This manager does not deal with the
 * physical minecraft world
 */
public interface ItemManager {
    void addItem(Item item);
    void removeItem(Item item);
    Optional<Item> getItem(Location location);

    Collection<Item> getItems(SimpleChunk chunk);
}
