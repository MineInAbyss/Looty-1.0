package com.derongan.minecraft.looty.item.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.derongan.minecraft.looty.item.components.PeriodicComponent;
import com.google.common.collect.Streams;

public abstract class IteratingPeriodAwareSystem extends IteratingSystem {
    private static final ComponentMapper<PeriodicComponent> periodicMapper = ComponentMapper.getFor(PeriodicComponent.class);


    public IteratingPeriodAwareSystem(Family family) {
        super(family);
    }

    public IteratingPeriodAwareSystem(Family family, int priority) {
        super(family, priority);
    }


    @Override
    public void update(float deltaTime) {
        Streams.stream(getEntities()).filter(this::filterOffCycle).forEach(a -> processEntity(a, deltaTime));
    }


    private boolean filterOffCycle(Entity e) {
        if (periodicMapper.has(e)) {
            PeriodicComponent periodicComponent = periodicMapper.get(e);

            return periodicComponent.lifespan % periodicComponent.period == 0;
        }
        return true;
    }
}
