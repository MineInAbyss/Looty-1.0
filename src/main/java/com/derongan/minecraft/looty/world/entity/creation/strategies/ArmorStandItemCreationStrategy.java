package com.derongan.minecraft.looty.world.entity.creation.strategies;

import com.derongan.minecraft.looty.world.chunk.SimpleLocation;
import com.derongan.minecraft.looty.world.entity.creation.EntityItemCreationStrategy;
import com.derongan.minecraft.looty.world.item.Item;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class ArmorStandItemCreationStrategy implements EntityItemCreationStrategy {
    @Override
    public Entity createEntity(Item item) {
        SimpleLocation location = item.getLocation();

        World world = Bukkit.getWorld(location.getWorld());


        return world.spawn(new org.bukkit.Location(world, location.getX(), location.getY(), location.getZ()), ArmorStand.class, a->{
            a.setArms(true);
            a.setGravity(false);
            a.setItemInHand(new ItemStack(item.getItem()));
        });
    }
}
