package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.item.handling.ItemPlayerEventListener;
import com.derongan.minecraft.looty.item.handling.ItemRegistrar;
import com.derongan.minecraft.looty.item.handling.ItemRegistrarImpl;
import com.derongan.minecraft.looty.world.chunk.ChunkListener;
import com.derongan.minecraft.looty.world.entity.EntityItemManager;
import com.derongan.minecraft.looty.world.entity.EntityItemManagerImpl;
import com.derongan.minecraft.looty.world.entity.creation.strategies.ArmorStandItemCreationStrategy;
import com.derongan.minecraft.looty.world.item.InMemoryItemPersister;
import com.derongan.minecraft.looty.world.item.ItemManager;
import com.derongan.minecraft.looty.world.item.ItemManagerImpl;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Looty extends JavaPlugin {
    private ItemManager itemManager;
    private EntityItemManager entityItemManager;
    private ItemRegistrar itemRegistrar;

    @Override
    public void onEnable() {
        // Plugin startup logic
        initializeItemManager();
        initializeEntityItemManager();

        initializeItemRegistrar();

        registerListeners();
    }

    private void initializeItemManager() {
        itemManager = new ItemManagerImpl(new InMemoryItemPersister());
    }

    private void initializeItemRegistrar() {
        itemRegistrar = new ItemRegistrarImpl();

        ItemCommandExecutor executor = new ItemCommandExecutor(itemRegistrar);
        this.getCommand("relic").setExecutor(executor);
        this.getCommand("relics").setExecutor(executor);
    }

    private void initializeEntityItemManager() {
        entityItemManager = new EntityItemManagerImpl(itemManager, new ArmorStandItemCreationStrategy());
        entityItemManager.start();
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new ChunkListener(entityItemManager), this);

        ItemPlayerEventListener listener = new ItemPlayerEventListener(itemRegistrar);

        pluginManager.registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
        entityItemManager.stop();
    }
}
