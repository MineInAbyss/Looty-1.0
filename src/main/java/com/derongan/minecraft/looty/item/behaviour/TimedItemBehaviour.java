package com.derongan.minecraft.looty.item.behaviour;

/**
 * Behaviour to be fired based on a timer
 */
public interface TimedItemBehaviour {
    void onTimerEvent(int numTicksPassed);
}
