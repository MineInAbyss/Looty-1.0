package com.derongan.minecraft.looty.item.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.derongan.minecraft.looty.item.components.LocationTargetsComponent;
import com.derongan.minecraft.looty.item.components.ProjectileComponent;

public class ProjectileSystem extends IteratingPeriodAwareSystem {
    private static final ComponentMapper<LocationTargetsComponent> locationTargetsMapper = ComponentMapper.getFor(LocationTargetsComponent.class);

    public ProjectileSystem(int priority) {
        super(Family.all(ProjectileComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
