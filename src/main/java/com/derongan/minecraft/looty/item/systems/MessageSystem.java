package com.derongan.minecraft.looty.item.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.derongan.minecraft.looty.item.components.MessageComponent;
import com.derongan.minecraft.looty.item.components.TargetComponent;
import com.derongan.minecraft.looty.item.components.internal.EntityTargetsComponent;
import com.derongan.minecraft.looty.item.components.internal.ItemOwnerComponent;

public class MessageSystem extends IteratingPeriodAwareSystem {
    private static final ComponentMapper<MessageComponent> messageMapper = ComponentMapper.getFor(MessageComponent.class);
    private static final ComponentMapper<TargetComponent> targetMapper = ComponentMapper.getFor(TargetComponent.class);
    private static final ComponentMapper<EntityTargetsComponent> entityTargetsMapper = ComponentMapper.getFor(EntityTargetsComponent.class);
    private static final ComponentMapper<ItemOwnerComponent> ownerMapper = ComponentMapper.getFor(ItemOwnerComponent.class);

    public MessageSystem(int priority) {
        super(Family.all(MessageComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        String message = messageMapper.get(entity).message;

        if(targetMapper.has(entity)){
            //dosomething
        } else{
            ownerMapper.get(entity).owner.sendMessage(message);
        }

//        EntityTargetsComponent entityTargetsComponent = entityTargetsMapper.get(entity);
//        if (entityTargetsComponent != null) {
//            entityTargetsComponent.getTargetEntities().stream().filter(a->a instanceof Player).forEach(a -> {
//                a.sendMessage(message);
//            });
//        }
    }
}
