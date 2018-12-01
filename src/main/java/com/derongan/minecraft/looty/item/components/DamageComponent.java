package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

public class DamageComponent implements Component {
    public double amount;

    private DamageComponent(double amount) {
        this.amount = amount;
    }

    public static ComponentFactory create(double damage){
        return ()->new DamageComponent(damage);
    }
}
