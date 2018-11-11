package com.derongan.minecraft.looty;

/**
 * Interface for services that have startup/shutdown concerns
 */
public interface Lifecycle {
    void start();
    void stop();
}
