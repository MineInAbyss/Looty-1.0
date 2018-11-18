package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;
import org.bukkit.Sound;

public class SoundComponent implements Component {
    public Sound sound;

    private SoundComponent(Sound sound) {
        this.sound = sound;
    }

    public static ComponentFactory create(Sound sound){
        return ()->new SoundComponent(sound);
    }
}
