package com.derongan.minecraft.looty.item.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.derongan.minecraft.looty.item.components.ParticleComponent;
import com.derongan.minecraft.looty.item.components.internal.AttachedEntityComponent;
import com.derongan.minecraft.looty.item.components.internal.EntityTargetsComponent;
import com.derongan.minecraft.looty.item.components.internal.LocationTargetsComponent;
import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleSystem extends IteratingPeriodAwareSystem {
    private static final ComponentMapper<ParticleComponent> particleMapper = ComponentMapper.getFor(ParticleComponent.class);
    private static final ComponentMapper<LocationTargetsComponent> locationTargetsMapper = ComponentMapper.getFor(LocationTargetsComponent.class);
    private static final ComponentMapper<EntityTargetsComponent> entityTargetsMapper = ComponentMapper.getFor(EntityTargetsComponent.class);
    private static final ComponentMapper<AttachedEntityComponent> attachedEntityMapper = ComponentMapper.getFor(AttachedEntityComponent.class);


    public ParticleSystem(int priority) {
        super(Family.all(ParticleComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Particle particle = particleMapper.get(entity).particle;

        LocationTargetsComponent locationTargetsComponent = locationTargetsMapper.get(entity);
        if (locationTargetsComponent != null) {
            locationTargetsComponent.getTargetLocations().forEach(a -> {
                spawnParticleAtLocation(particle, a);
            });
        }

        EntityTargetsComponent entityTargetsComponent = entityTargetsMapper.get(entity);
        if (entityTargetsComponent != null) {
            entityTargetsComponent.getTargetEntities().forEach(a -> {
                spawnParticleAtLocation(particle, a.getLocation());
            });
        }

        //TODO we need to think about priority and choosing targets more
//        AttachedEntityComponent attachedEntityComponent = attachedEntityMapper.get(entity);
//        if (attachedEntityComponent != null) {
//            spawnParticleAtLocation(particle, attachedEntityComponent.entity.getLocation());
//        }
    }

    private void spawnParticleAtLocation(Particle particle, Location location) {
        location.getWorld().spawnParticle(particle, location, 0);
    }
}
