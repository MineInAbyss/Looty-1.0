package com.derongan.minecraft.looty.item;

import com.derongan.minecraft.looty.item.components.ComponentFactory;

import java.util.Collection;
import java.util.Set;

public class ItemAction {
    private Collection<ComponentFactory> componentFactories;
    private Set<ItemActionTarget> targets;

    ItemAction(Collection<ComponentFactory> componentFactories, Set<ItemActionTarget> targets) {
        this.componentFactories = componentFactories;
        this.targets = targets;
    }

    public Collection<ComponentFactory> getComponentFactories() {
        return componentFactories;
    }

    public Set<ItemActionTarget> getTargets() {
        return targets;
    }
}
