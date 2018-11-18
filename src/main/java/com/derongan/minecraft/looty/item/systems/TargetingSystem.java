package com.derongan.minecraft.looty.item.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.derongan.minecraft.looty.item.ActionTarget;
import com.derongan.minecraft.looty.item.components.*;
import org.bukkit.Location;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * System that populates the targets component for entities
 */
public class TargetingSystem extends IteratingPeriodAwareSystem {
    private static final ComponentMapper<AreaComponent> areaMapper = ComponentMapper.getFor(AreaComponent.class);
    private static final ComponentMapper<TargetComponent> targetMapper = ComponentMapper.getFor(TargetComponent.class);
    private static final ComponentMapper<EntityTargetsComponent> entityTargetsMapper = ComponentMapper.getFor(EntityTargetsComponent.class);
    private static final ComponentMapper<ItemOwnerComponent> itemOwnerMapper = ComponentMapper.getFor(ItemOwnerComponent.class);
    private static final ComponentMapper<ActionTargetComponent> actionTargetMapper = ComponentMapper.getFor(ActionTargetComponent.class);
    private static final ComponentMapper<SelfTargetingComponent> selfTargetingMapper = ComponentMapper.getFor(SelfTargetingComponent.class);
    private static final ComponentMapper<LocationTargetsComponent> locationTargetsMapper = ComponentMapper.getFor(LocationTargetsComponent.class);


    public TargetingSystem(int priority) {
        super(Family.exclude().get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Collection<ActionTarget> targetTypes;

        if (targetMapper.has(entity)) {
            targetTypes = targetMapper.get(entity).getTarget();
        } else {
            //TODO handle this better. The default for all should be LOCATION prob
            targetTypes = Collections.singleton(ActionTarget.LOCATION);
        }

        targetTypes.forEach(a -> injectTargets(entity, a));
    }

    private EntityTargetsComponent getOrAddEntitiesTargetComponent(Entity entity) {
        EntityTargetsComponent targets;

        if (entityTargetsMapper.has(entity))
            targets = entityTargetsMapper.get(entity);
        else {
            targets = new EntityTargetsComponent();
            entity.add(targets);
        }

        return targets;
    }

    private LocationTargetsComponent getOrAddLocationTargetsComponent(Entity entity) {
        LocationTargetsComponent targets;

        if (locationTargetsMapper.has(entity))
            targets = locationTargetsMapper.get(entity);
        else {
            targets = new LocationTargetsComponent();
            entity.add(targets);
        }

        return targets;
    }

    private void injectTargets(Entity entity, ActionTarget actionTarget) {
        switch (actionTarget) {
            case OWNER:
                injectOwner(entity);
                break;
            case ENTITY:
                injectEntities(entity);
                break;
            case BLOCK:
                injectBlocks(entity);
                break;
            default:
                injectDefaultLocation(entity);
                break;
        }
    }

    private void injectBlocks(Entity entity) {
        LocationTargetsComponent targets = getOrAddLocationTargetsComponent(entity);
        Location targetLocation = actionTargetMapper.get(entity).location;

        double radius;

        if (areaMapper.has(entity)) {
            radius = areaMapper.get(entity).radius;
        } else {
            radius = .5;
        }

        for (int x = (int) Math.floor(-radius); x < radius; x++) {
            for (int y = (int) Math.floor(-radius); y < radius; y++) {
                for (int z = (int) Math.floor(-radius); z < radius; z++) {
                    int distanceSquared = (x * x + y * y + z * z);

                    if (distanceSquared < radius * radius) {
                        targets.addTargetLocation(targetLocation.clone().add(x, y, z));
                    }
                }
            }
        }
    }

    private void injectDefaultLocation(Entity entity) {
        //fuck
    }

    private void injectEntities(Entity entity) {
        EntityTargetsComponent targets = getOrAddEntitiesTargetComponent(entity);

        Location targetLocation = actionTargetMapper.get(entity).location;
        float radius;

        //TODO this may be too restrictive
        int limit = 100;

        //TODO right now this is hacky and just
        if (areaMapper.has(entity)) {
            radius = areaMapper.get(entity).radius;
        } else {
            radius = .5f;
            limit = 1;
        }

        targetLocation.getWorld()
                .getNearbyEntities(targetLocation, radius, radius, radius, a -> playerFilter(entity, a))
                .stream()
                .sorted(Comparator.comparingDouble(a -> targetLocation.toVector().distance(a.getLocation().toVector())))
                .limit(limit)
                .forEach(targets::addTargetEntity);
    }

    private boolean playerFilter(Entity entity, org.bukkit.entity.Entity a) {
        return isSelfTargeting(entity) || a != itemOwnerMapper.get(entity).owner;
    }

    private boolean isSelfTargeting(Entity entity) {
        return selfTargetingMapper.has(entity);
    }

    private void injectOwner(Entity entity) {
        EntityTargetsComponent targets = getOrAddEntitiesTargetComponent(entity);
        targets.addTargetEntity(itemOwnerMapper.get(entity).owner);
    }
}
