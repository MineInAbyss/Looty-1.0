package com.derongan.minecraft.looty.item.components;

import com.badlogic.ashley.core.Component;
import org.bukkit.Location;


/**
 * Component that stores the location the action was directed at
 */
public class ActionTargetComponent implements Component {
    public Location location;

    public ActionTargetComponent(Location location) {
        this.location = location;
    }
}
