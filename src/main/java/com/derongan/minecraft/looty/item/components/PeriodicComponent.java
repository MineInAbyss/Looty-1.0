package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

//TODO persistent vs periodic vs aging and so on...
public class PeriodicComponent implements Component {
    public int period;
    public int lifespan;

    private PeriodicComponent(int lifespan, int period) {
        this.period = period;
        this.lifespan = lifespan;
    }

    public static ComponentFactory create(int period, int lifespan){
        return ()->new PeriodicComponent(lifespan, period);
    }
}
