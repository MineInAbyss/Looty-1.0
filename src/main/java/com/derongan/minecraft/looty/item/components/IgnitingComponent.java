package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

public class IgnitingComponent implements Component {
    public int strength;

    private IgnitingComponent(int strength) {
        this.strength = strength;
    }

    private IgnitingComponent() {
        this.strength = 5;
    }

    public static ComponentFactory create(){
        return IgnitingComponent::new;
    }
    public static ComponentFactory create(int strength){
        return ()->new IgnitingComponent(strength);
    }
}
