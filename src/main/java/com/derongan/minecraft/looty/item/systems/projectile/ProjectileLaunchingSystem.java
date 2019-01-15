package com.derongan.minecraft.looty.item.systems.projectile;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.derongan.minecraft.looty.item.components.ProjectileComponent;
import com.derongan.minecraft.looty.item.components.internal.AttachedEntityComponent;
import com.derongan.minecraft.looty.item.components.internal.ItemOwnerComponent;
import com.derongan.minecraft.looty.item.components.internal.LocationTargetsComponent;
import com.derongan.minecraft.looty.item.systems.IteratingPeriodAwareSystem;
import org.bukkit.Location;

public class ProjectileLaunchingSystem extends IteratingPeriodAwareSystem {
    private static final ComponentMapper<LocationTargetsComponent> locationTargetsMapper = ComponentMapper.getFor(LocationTargetsComponent.class);
    private static final ComponentMapper<ProjectileComponent> projectileMapper = ComponentMapper.getFor(ProjectileComponent.class);
    private static final ComponentMapper<AttachedEntityComponent> attachedEntityMapper = ComponentMapper.getFor(AttachedEntityComponent.class);
    private static final ComponentMapper<ItemOwnerComponent> itemOwnerMapper = ComponentMapper.getFor(ItemOwnerComponent.class);

    public ProjectileLaunchingSystem(int priority) {
        //TODO owner isnt required?
        super(Family.all(ProjectileComponent.class, ItemOwnerComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
//        projectileMapper.get(entity);
         if(!attachedEntityMapper.has(entity)){
             org.bukkit.entity.Entity owner = itemOwnerMapper.get(entity).owner;
             Location origin = owner.getLocation();

             ProjectileComponent pc = projectileMapper.get(entity);

             //TODO codify offset
            org.bukkit.entity.Entity attached = origin.getWorld().spawnArrow(origin.clone().add(0,1,0), origin.getDirection(), pc.getSpeed(), pc.getSpread());

            attached.setGravity(false);
            entity.add(new AttachedEntityComponent(attached));
         }
    }
}
