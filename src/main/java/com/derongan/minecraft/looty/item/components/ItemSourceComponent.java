package com.derongan.minecraft.looty.item.components;

import org.bukkit.inventory.ItemStack;

/**
 * Component that holds the source item stack
 */
public class ItemSourceComponent implements InternalComponent {
    private ItemStack source;

    public ItemSourceComponent(ItemStack source) {
        this.source = source;
    }
}
