package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

public class MessageComponent implements Component {
    public String message;

    private MessageComponent(String message) {
        this.message = message;
    }

    public static ComponentFactory create(String message){
        return ()->new MessageComponent(message);
    }
}
