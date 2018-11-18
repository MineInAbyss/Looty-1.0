package com.derongan.minecraft.looty.item.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.derongan.minecraft.looty.item.components.DamageComponent;
import com.derongan.minecraft.looty.item.components.EntityTargetsComponent;
import org.bukkit.entity.Damageable;


public class DamageSystem extends IteratingPeriodAwareSystem {
    private ImmutableArray<Entity> entities;

    public static final ComponentMapper<DamageComponent> damageMapper = ComponentMapper.getFor(DamageComponent.class);
    public static final ComponentMapper<EntityTargetsComponent> targetMapper = ComponentMapper.getFor(EntityTargetsComponent.class);

    public DamageSystem(int priority) {
        super(Family.all(DamageComponent.class, EntityTargetsComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        float damage = damageMapper.get(entity).amount;
        targetMapper.get(entity)
                .getTargetEntities()
                .stream()
                .filter(a -> a instanceof Damageable)
                .forEach(a -> ((Damageable) a).damage(damage));
    }
}
