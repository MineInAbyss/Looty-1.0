package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

public class SelfTargetingComponent implements Component {
    private SelfTargetingComponent() {
    }

    public static ComponentFactory create() {
        return SelfTargetingComponent::new;
    }
}
