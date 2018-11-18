package com.derongan.minecraft.looty.item.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.derongan.minecraft.looty.item.components.EntityTargetsComponent;
import com.derongan.minecraft.looty.item.components.IgnitingComponent;
import com.derongan.minecraft.looty.item.components.LocationTargetsComponent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

public class IgnitingSystem extends IteratingPeriodAwareSystem {
    private static final ComponentMapper<IgnitingComponent> ignitingMapper = ComponentMapper.getFor(IgnitingComponent.class);
    private static final ComponentMapper<LocationTargetsComponent> locationTargetsMapper = ComponentMapper.getFor(LocationTargetsComponent.class);
    private static final ComponentMapper<EntityTargetsComponent> entityTargetsMapper = ComponentMapper.getFor(EntityTargetsComponent.class);

    public IgnitingSystem(int priority) {
        super(Family.all(IgnitingComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        int strength = ignitingMapper.get(entity).strength;

        LocationTargetsComponent locationTargetsComponent = locationTargetsMapper.get(entity);
        if (locationTargetsComponent != null) {
            locationTargetsComponent.getTargetLocations().forEach(a -> {
                Block block = a.getBlock();
                Block toIgnite = block.getRelative(BlockFace.UP);

                if (toIgnite.isEmpty()) {
                    BlockData data = Bukkit.getServer().createBlockData("minecraft:fire[age=10,east=false,north=false,south=false,up=false,west=false]");

                    toIgnite.setBlockData(data);
                }
            });
        }

        EntityTargetsComponent entityTargetsComponent = entityTargetsMapper.get(entity);
        if (entityTargetsComponent != null) {
            entityTargetsComponent.getTargetEntities().forEach(a -> {
                a.setFireTicks(Math.max(a.getFireTicks(),0) + strength);
            });
        }
    }
}
