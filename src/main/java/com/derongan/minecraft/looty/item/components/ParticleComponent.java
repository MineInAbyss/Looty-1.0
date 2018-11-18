package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;
import org.bukkit.Particle;

public class ParticleComponent implements Component {
    public Particle particle;

    private ParticleComponent(Particle particle) {
        this.particle = particle;
    }

    public static ComponentFactory create(Particle particle){
        return ()->new ParticleComponent(particle);
    }
}
