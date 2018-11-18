package com.derongan.minecraft.looty.item.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.derongan.minecraft.looty.item.components.EntityTargetsComponent;
import com.derongan.minecraft.looty.item.components.LocationTargetsComponent;
import com.derongan.minecraft.looty.item.components.PeriodicComponent;

public class EntityRemovalSystem extends IteratingSystem {
    public static final ComponentMapper<PeriodicComponent> persistMapper = ComponentMapper.getFor(PeriodicComponent.class);
    public static final ComponentMapper<EntityTargetsComponent> entityTargetMapper = ComponentMapper.getFor(EntityTargetsComponent.class);
    public static final ComponentMapper<LocationTargetsComponent> locationTargetMapper = ComponentMapper.getFor(LocationTargetsComponent.class);

    public EntityRemovalSystem(int priority) {
        super(Family.exclude().get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (persistMapper.has(entity)) {
            int ttl = persistMapper.get(entity).lifespan;

            if (ttl == 0) {
                getEngine().removeEntity(entity);
            } else {
                persistMapper.get(entity).lifespan -= deltaTime;
                if (entityTargetMapper.has(entity)) {
                    entityTargetMapper.get(entity).clearTargetEntities();
                }
                if (locationTargetMapper.has(entity)) {
                    locationTargetMapper.get(entity).clearTargetLocations();
                }
            }
        } else {
            getEngine().removeEntity(entity);
        }
    }
}
