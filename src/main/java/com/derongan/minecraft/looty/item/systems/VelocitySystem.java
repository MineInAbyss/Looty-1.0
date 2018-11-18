package com.derongan.minecraft.looty.item.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.derongan.minecraft.looty.item.components.ActionTargetComponent;
import com.derongan.minecraft.looty.item.components.EntityTargetsComponent;
import com.derongan.minecraft.looty.item.components.ItemOwnerComponent;
import com.derongan.minecraft.looty.item.components.VelocityImpartingComponent;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class VelocitySystem extends IteratingPeriodAwareSystem {
    private static final ComponentMapper<EntityTargetsComponent> targetMapper = ComponentMapper.getFor(EntityTargetsComponent.class);
    private static final ComponentMapper<VelocityImpartingComponent> velocityMapper = ComponentMapper.getFor(VelocityImpartingComponent.class);
    private static final ComponentMapper<ItemOwnerComponent> ownerMapper = ComponentMapper.getFor(ItemOwnerComponent.class);
    private static final ComponentMapper<ActionTargetComponent> actionTargetMapper = ComponentMapper.getFor(ActionTargetComponent.class);

    public VelocitySystem(int priority) {
        super(Family.all(VelocityImpartingComponent.class, EntityTargetsComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final double velocity = velocityMapper.get(entity).strength;
        VelocityImpartingComponent.From from = velocityMapper.get(entity).from;

        Vector causeLocation;

        switch (from) {
            case TARGET:
                causeLocation = actionTargetMapper.get(entity).location.toVector();
                break;
            default:
                causeLocation = ownerMapper.get(entity).owner.getLocation().toVector();
        }


        targetMapper.get(entity).getTargetEntities().forEach(a -> {
            try {
                double finalVelocity = velocity;
                Vector aVector = a.getLocation().toVector();
                if (a instanceof Player) {
                    finalVelocity *= .5;
                    aVector = ((Player) a).getEyeLocation().toVector();
                }

                Vector forceVector = aVector.subtract(causeLocation).normalize().multiply(finalVelocity);

                forceVector.checkFinite();
                a.setVelocity(a.getVelocity().add(forceVector));
            } catch (IllegalArgumentException e) {
                //NOOP
            }
        });
    }
}
