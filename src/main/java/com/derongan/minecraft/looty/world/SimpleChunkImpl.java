package com.derongan.minecraft.looty.world;

public class SimpleChunkImpl implements SimpleChunk {
    private int x,z;
    private String worldName;

    public SimpleChunkImpl(int x, int z, String worldName) {
        this.x = x;
        this.z = z;
        this.worldName = worldName;
    }

    @Override
    public int getX() {
        return x;
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
        if (!(o instanceof SimpleChunkImpl)) return false;

        SimpleChunkImpl that = (SimpleChunkImpl) o;

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
