package com.derongan.minecraft.looty.item.items;

import com.derongan.minecraft.looty.item.ItemRarity;
import com.derongan.minecraft.looty.item.behaviour.ItemBehaviour;
import org.bukkit.Material;
import org.bukkit.event.Event;

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
     * Gets the behaviours assigned to this item
     */
    Collection<ItemBehaviour> getBehaviours(Class<? extends Event> type);

    /**
     * Gets the rarity of this item
     */
    ItemRarity getRarity();
}
