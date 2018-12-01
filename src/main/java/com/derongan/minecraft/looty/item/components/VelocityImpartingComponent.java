package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

public class VelocityImpartingComponent implements Component {
    public double strength;
    public From from;

    //TODO split into different components possibly
    public enum From{
        TARGET,
        OWNER
    }

    private VelocityImpartingComponent(double strength, From from) {
        this.strength = strength;
        this.from = from;
    }

    public static ComponentFactory create(double strength, From from) {
        return () -> new VelocityImpartingComponent(strength, from);
    }

}
