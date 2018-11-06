package com.derongan.minecraft.looty.world.chunk;

/**
 * Instead of using {@link org.bukkit.Chunk} we create
 * a chunk does not require a world instance but rather just a world name
 * Additionally it does not provide access to blocks
 */
public class SimpleChunk {
    private int x,z;
    private String worldName;

    public SimpleChunk(int x, int z, String worldName) {
        this.x = x;
        this.z = z;
        this.worldName = worldName;
    }

    public int getX() {
        return x;
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
        if (!(o instanceof SimpleChunk)) return false;

        SimpleChunk that = (SimpleChunk) o;

        if (getX() != that.getX()) return false;
        if (getZ() != that.getZ()) return false;
        return worldName.equals(that.worldName);
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getZ();
        result = 31 * result + worldName.hashCode();
        return result;
    }
}
