package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

public class DamageComponent implements Component {
    public float amount;

    private DamageComponent(float amount) {
        this.amount = amount;
    }

    public static ComponentFactory create(float damage){
        return ()->new DamageComponent(damage);
    }
}
