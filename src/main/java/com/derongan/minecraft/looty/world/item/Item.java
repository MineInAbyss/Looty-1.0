package com.derongan.minecraft.looty.world.item;

import com.derongan.minecraft.looty.world.chunk.SimpleLocation;
import org.bukkit.Material;

public interface Item {
    SimpleLocation getLocation();

    Material getItem();
}
