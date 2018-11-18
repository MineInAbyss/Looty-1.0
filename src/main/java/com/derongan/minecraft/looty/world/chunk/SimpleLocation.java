package com.derongan.minecraft.looty.world.chunk;

/**
 * Instead of using {@link org.bukkit.Location} we create
 * a ownerMapper that is bound to integer values. Additionally
 * it does not require a world instance but rather just a world name
 */
public class SimpleLocation {
    private int x,y,z;
    private String worldName;

    public SimpleLocation(int x, int y, int z, String worldName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldName = worldName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getWorld() {
        return worldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleLocation)) return false;

        SimpleLocation location = (SimpleLocation) o;

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
