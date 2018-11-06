package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.world.chunk.ChunkListener;
import com.derongan.minecraft.looty.world.chunk.SimpleLocation;
import com.derongan.minecraft.looty.world.entity.ArmorStandItemCreationStrategy;
import com.derongan.minecraft.looty.world.entity.EntityItemManager;
import com.derongan.minecraft.looty.world.entity.EntityItemManagerImpl;
import com.derongan.minecraft.looty.world.item.InMemoryItemPersister;
import com.derongan.minecraft.looty.world.item.Item;
import com.derongan.minecraft.looty.world.item.ItemManager;
import com.derongan.minecraft.looty.world.item.ItemManagerImpl;
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
            public SimpleLocation getLocation() {
                return new SimpleLocation(1000,200,1000, "world");
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
