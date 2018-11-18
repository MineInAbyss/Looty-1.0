package com.derongan.minecraft.looty.item.components;

import org.bukkit.entity.Entity;

public class ItemOwnerComponent implements InternalComponent {
    public Entity owner;

    public ItemOwnerComponent(Entity owner) {
        this.owner = owner;
    }
}
