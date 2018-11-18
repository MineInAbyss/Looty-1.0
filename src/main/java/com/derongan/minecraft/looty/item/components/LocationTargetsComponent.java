package com.derongan.minecraft.looty.item.components;

import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class LocationTargetsComponent implements InternalComponent {
    private Set<Location> locations;

    public LocationTargetsComponent() {
        locations = new HashSet<>();
    }

    public void addTargetLocation(Location e) {
        locations.add(e);
    }

    public Set<Location> getTargetLocations() {
        return locations;
    }

    public void clearTargetLocations() {
        locations.clear();
    }
}
