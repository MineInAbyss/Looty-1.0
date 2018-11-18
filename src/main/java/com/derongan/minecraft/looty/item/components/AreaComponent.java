package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

public class AreaComponent implements Component {
    public float radius;

    private AreaComponent(float radius) {
        this.radius = radius;
    }

    public static ComponentFactory create(float radius){
        return ()->new AreaComponent(radius);
    }
}
