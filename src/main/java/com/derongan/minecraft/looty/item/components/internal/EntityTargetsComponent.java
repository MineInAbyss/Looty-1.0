package com.derongan.minecraft.looty.item.components.internal;

import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Component that contains entities that will be affected by this
 */
public class EntityTargetsComponent implements InternalComponent {
    private Set<Entity> entities;

    public EntityTargetsComponent() {
        entities = new HashSet<>();
    }

    public void addTargetEntity(Entity e) {
        entities.add(e);
    }

    public Set<Entity> getTargetEntities() {
        return entities;
    }

    public void clearTargetEntities(){
        entities.clear();
    }
}
