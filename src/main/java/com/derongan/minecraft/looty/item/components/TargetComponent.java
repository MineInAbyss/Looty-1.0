package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;
import com.google.common.collect.Sets;

import java.util.Set;

public class TargetComponent implements Component {
    private Set<ActionTarget> target;

    private TargetComponent(Set<ActionTarget> targets) {
        this.target = targets;
    }

    public Set<ActionTarget> getTargets() {
        return target;
    }

    public static ComponentFactory create(ActionTarget ... targets){
        return ()->new TargetComponent(Sets.newHashSet(targets));
    }
}
