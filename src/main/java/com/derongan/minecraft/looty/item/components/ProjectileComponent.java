package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

public class ProjectileComponent implements Component {
    private int speed;
    private int spread;

    public ProjectileComponent(int speed, int spread) {
        this.speed = speed;
        this.spread = spread;
    }

//    public static ComponentFactory create() {
//        return ()->new ProjectileComponent(1,0);
//    }

    public static ComponentFactory create(int speed, int spread) {
        return ()->new ProjectileComponent(speed, spread);
    }

    public int getSpeed() {
        return speed;
    }

    public int getSpread() {
        return spread;
    }
}
