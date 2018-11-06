package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.world.ChunkListener;
import com.derongan.minecraft.looty.world.LocationImpl;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class Looty extends JavaPlugin {

    private EntityItemManager entityItemManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        ItemManager itemManager = new ItemManagerImpl(new InMemoryItemPersister());
        entityItemManager = new EntityItemManagerImpl(itemManager, new ArmorStandItemCreationStrategy());
        getServer().getPluginManager().registerEvents(new ChunkListener(entityItemManager), this);

        itemManager.addItem(new Item() {
            @Override
            public Location getLocation() {
                return new LocationImpl(1000,200,1000, "world");
            }

            @Override
            public Material getItem() {
                return Material.DIAMOND_PICKAXE;
            }
        });

        getServer().getWorlds().forEach(a->{
            for (Chunk loadedChunk : a.getLoadedChunks()) {
                entityItemManager.loadEntities(loadedChunk);
            }
        });
    }

    @Override
    public void onDisable() {
        getServer().getWorlds().forEach(a->{
            for (Chunk loadedChunk : a.getLoadedChunks()) {
                entityItemManager.unloadEntities(loadedChunk);
            }
        });
    }
}
