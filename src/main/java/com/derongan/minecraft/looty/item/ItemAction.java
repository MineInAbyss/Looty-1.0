package com.derongan.minecraft.looty.item;

import com.derongan.minecraft.looty.item.components.ComponentFactory;
import com.derongan.minecraft.looty.item.handling.ActionType;

import java.util.Collection;

public class ItemAction {
    private Collection<ComponentFactory> componentFactories;
    private ActionType actionType;
    private Collection<ItemActionTarget> targets;

    ItemAction(Collection<ComponentFactory> componentFactories, ActionType actionType, Collection<ItemActionTarget> targets) {
        this.componentFactories = componentFactories;
        this.actionType = actionType;
        this.targets = targets;
    }

    public Collection<ComponentFactory> getComponentFactories() {
        return componentFactories;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Collection<ItemActionTarget> getTargets() {
        return targets;
    }
}
