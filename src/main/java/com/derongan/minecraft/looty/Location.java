package com.derongan.minecraft.looty;

/**
 * Instead of using {@link org.bukkit.Location} we create
 * a location that is bound to integer values. Additionally
 * it does not require a world instance but rather just a world name
 */
public interface Location {
    int getX();
    int getY();
    int getZ();

    String getWorld();
}
