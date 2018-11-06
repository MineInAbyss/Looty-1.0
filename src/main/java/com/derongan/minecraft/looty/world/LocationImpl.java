package com.derongan.minecraft.looty.world;

import com.derongan.minecraft.looty.Location;

public class LocationImpl implements Location {
    private int x,y,z;
    private String worldName;

    public LocationImpl(int x, int y, int z, String worldName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldName = worldName;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public String getWorld() {
        return worldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationImpl)) return false;

        LocationImpl location = (LocationImpl) o;

        if (getX() != location.getX()) return false;
        if (getY() != location.getY()) return false;
        if (getZ() != location.getZ()) return false;
        return worldName.equals(location.worldName);
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        result = 31 * result + getZ();
        result = 31 * result + worldName.hashCode();
        return result;
    }
}
