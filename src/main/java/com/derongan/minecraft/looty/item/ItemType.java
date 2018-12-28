package com.derongan.minecraft.looty.item;

import com.derongan.minecraft.looty.item.handling.ActionTrigger;
import com.derongan.minecraft.looty.item.handling.ItemRarity;
import org.bukkit.Material;

import java.util.Collection;
import java.util.List;

public interface ItemType {
    /**
     * Get the name of this item
     */
    String getName();

    /**
     * Get the lore to display for this item
     */
    List<String> getLore();

    /**
     * Get the material this item is assigned to
     * TODO in 1.14 we can replace this with model nbt data!
     */
    Material getMaterial();

    /**
     * Get the durability this item is assigned to
     * TODO in 1.14 we can replace this with model nbt data!
     */
    short getDurability();

    /**
     * Gets the rarity of this item
     */
    ItemRarity getRarity();

    /**
     * Get the actions for this event type
     */
    Collection<ItemAction> getActions(ActionTrigger actionTrigger);


    /**
     * Get a specific variable value
     */
}
