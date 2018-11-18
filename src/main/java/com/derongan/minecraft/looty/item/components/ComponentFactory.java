package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;

@FunctionalInterface
public interface ComponentFactory {
    Component createComponent();
}
