package com.derongan.minecraft.looty.item;

import com.derongan.minecraft.looty.item.items.ItemType;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Optional;

/**
 * Handles registration of item types.
 */
public interface ItemRegistrar {
    void registerItem(ItemType itemType);
    void unregisterItem(ItemType itemType);

    Collection<ItemType> getAllTypes();

    Optional<ItemType> getItemType(ItemStack itemStack);
}
