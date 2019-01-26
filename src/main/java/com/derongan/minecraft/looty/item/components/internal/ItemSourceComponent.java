package com.derongan.minecraft.looty.item.components.internal;

import com.derongan.minecraft.looty.item.ItemType;

/**
 * Component that holds the source item stack
 */
public class ItemSourceComponent implements InternalComponent {
    private ItemType source;

    public ItemSourceComponent(ItemType source) {
        this.source = source;
    }

    public ItemType getSource() {
        return source;
    }
}
