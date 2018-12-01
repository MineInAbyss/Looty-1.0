package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

public class AreaComponent implements Component {
    public double radius;

    private AreaComponent(double radius) {
        this.radius = radius;
    }

    public static ComponentFactory create(double radius){
        return ()->new AreaComponent(radius);
    }
}
