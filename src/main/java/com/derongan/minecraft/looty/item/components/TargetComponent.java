package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;
import com.derongan.minecraft.looty.item.ActionTarget;
import com.google.common.collect.Sets;

import java.util.Set;

public class TargetComponent implements Component {
    private Set<ActionTarget> target;

    private TargetComponent(ActionTarget... targets) {
        this.target = Sets.newHashSet(targets);
    }

    public Set<ActionTarget> getTarget() {
        return target;
    }

    public static ComponentFactory create(ActionTarget ... targets){
        return ()->new TargetComponent(targets);
    }
}
