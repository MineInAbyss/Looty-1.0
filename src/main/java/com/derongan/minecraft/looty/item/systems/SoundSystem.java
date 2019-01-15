package com.derongan.minecraft.looty.item.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.derongan.minecraft.looty.item.components.SoundComponent;
import com.derongan.minecraft.looty.item.components.internal.EntityTargetsComponent;
import com.derongan.minecraft.looty.item.components.internal.LocationTargetsComponent;
import org.bukkit.Location;
import org.bukkit.Sound;

public class SoundSystem extends IteratingPeriodAwareSystem {
    private static final ComponentMapper<SoundComponent> soundMapper = ComponentMapper.getFor(SoundComponent.class);
    private static final ComponentMapper<LocationTargetsComponent> locationTargetsMapper = ComponentMapper.getFor(LocationTargetsComponent.class);
    private static final ComponentMapper<EntityTargetsComponent> entityTargetsMapper = ComponentMapper.getFor(EntityTargetsComponent.class);

    public SoundSystem(int priority) {
        super(Family.all(SoundComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Sound sound = soundMapper.get(entity).sound;

        LocationTargetsComponent locationTargetsComponent = locationTargetsMapper.get(entity);
        if (locationTargetsComponent != null) {
            locationTargetsComponent.getTargetLocations().forEach(a -> {
                playSoundAtLocation(sound, a);
            });
        }

        EntityTargetsComponent entityTargetsComponent = entityTargetsMapper.get(entity);
        if (entityTargetsComponent != null) {
            entityTargetsComponent.getTargetEntities().forEach(a -> {
                playSoundAtLocation(sound, a.getLocation());
            });
        }
    }

    private void playSoundAtLocation(Sound sound, Location location) {
        location.getWorld().playSound(location, sound, 1, 1);
    }
}
