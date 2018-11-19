package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

public class BeamComponent implements Component {
    public int length;

    private BeamComponent(int length) {
        this.length = length;
    }

    public static ComponentFactory create(int length){
        return ()->new BeamComponent(length);
    }
}
