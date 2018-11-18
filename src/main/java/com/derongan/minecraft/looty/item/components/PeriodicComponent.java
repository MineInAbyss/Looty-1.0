package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

//TODO persistent vs periodic vs aging and so on...
public class PeriodicComponent implements Component {
    public int lifespan;
    public int period;

    private PeriodicComponent(int lifespan, int period) {
        this.lifespan = lifespan;
        this.period = period;
    }

    public static ComponentFactory create(int lifespan, int period){
        return ()->new PeriodicComponent(lifespan, period);
    }
}
