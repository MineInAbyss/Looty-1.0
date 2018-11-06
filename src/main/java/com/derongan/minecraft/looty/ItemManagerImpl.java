package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.world.SimpleChunk;

import java.util.Collection;
import java.util.Optional;

public class ItemManagerImpl implements ItemManager {
    private ItemPersiter itemPersiter;

    public ItemManagerImpl(ItemPersiter itemPersiter) {
        this.itemPersiter = itemPersiter;
    }

    @Override
    public void addItem(Item item) {
        itemPersiter.create(item);
    }

    @Override
    public void removeItem(Item item) {
        itemPersiter.delete(item);
    }

    @Override
    public Optional<Item> getItem(Location location) {
        return itemPersiter.getFromLocation(location);
    }

    @Override
    public Collection<Item> getItems(SimpleChunk chunk) {
        return itemPersiter.getFromChunk(chunk);
    }
}
