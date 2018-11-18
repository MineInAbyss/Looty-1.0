package com.derongan.minecraft.looty.item.components;

import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.Set;

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
