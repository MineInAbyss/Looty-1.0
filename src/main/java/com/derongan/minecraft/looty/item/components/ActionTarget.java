package com.derongan.minecraft.looty.item.components;

public enum ActionTarget {
    ENTITY, // Affect entities
    OWNER,  // Affect the owner of the original item
    BLOCK,  // Affect blocks
    LOCATION,   // Affect locations
    WORLD,  // Affect the world
    SELF,   // Affect the causing item
    ATTACHMENT
}
