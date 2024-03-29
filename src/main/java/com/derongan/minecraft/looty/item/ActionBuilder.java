package com.derongan.minecraft.looty.item;

import com.derongan.minecraft.looty.item.components.ComponentFactory;
import com.derongan.minecraft.looty.item.handling.ActionType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ActionBuilder {
    private final Collection<ComponentFactory> factories;
    private ActionType actionType;
    private Set<ItemActionTarget> actionTargets;

    private ActionBuilder() {
        factories = new HashSet<>();
        actionTargets = new HashSet<>();
    }

    public static ActionBuilder create() {
        return new ActionBuilder();
    }

    public ItemAction build() {
        return new ItemAction(factories, actionType, actionTargets);
    }

    public ActionBuilder setActionType(ActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public ActionBuilder setTargets(ItemActionTarget... actionTargets) {
        this.actionTargets.addAll(Arrays.asList(actionTargets));
        return this;
    }

    public ActionBuilder add(ComponentFactory componentFactory) {
        factories.add(componentFactory);
        return this;
    }

}
