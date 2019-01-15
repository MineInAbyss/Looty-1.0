package com.derongan.minecraft.looty;

import com.badlogic.ashley.core.Engine;
import com.derongan.minecraft.looty.item.ItemType;
import com.derongan.minecraft.looty.item.handling.ItemPlayerEventListener;
import com.derongan.minecraft.looty.item.handling.ItemRegistrar;
import com.derongan.minecraft.looty.item.handling.ItemRegistrarImpl;
import com.derongan.minecraft.looty.item.systems.*;
import com.derongan.minecraft.looty.item.systems.projectile.ProjectileLaunchingSystem;
import com.derongan.minecraft.looty.world.chunk.ChunkListener;
import com.derongan.minecraft.looty.world.entity.EntityItemManager;
import com.derongan.minecraft.looty.world.entity.EntityItemManagerImpl;
import com.derongan.minecraft.looty.world.entity.creation.strategies.ArmorStandItemCreationStrategy;
import com.derongan.minecraft.looty.world.item.InMemoryItemPersister;
import com.derongan.minecraft.looty.world.item.ItemManager;
import com.derongan.minecraft.looty.world.item.ItemManagerImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public final class Looty extends JavaPlugin {
    private ItemManager itemManager;
    private EntityItemManager entityItemManager;
    private ItemRegistrar itemRegistrar;
    private static Engine engine = new Engine();

    @Override
    public void onEnable() {
        // Plugin startup logic
        initializeItemManager();
        initializeEntityItemManager();

        initializeItemRegistrar();

        registerListeners();


//        new TestItems().registerAllTests(itemRegistrar);

        loadConfigItems();

        //Set up engine
        engine.addSystem(new TargetingSystem(0));
        engine.addSystem(new ProjectileLaunchingSystem(1));
        engine.addSystem(new ParticleSystem(1));
        engine.addSystem(new SoundSystem(2));
        engine.addSystem(new DamageSystem(3));
        engine.addSystem(new MessageSystem(4));
        engine.addSystem(new IgnitingSystem(5));
        engine.addSystem(new VelocitySystem(6));
        engine.addSystem(new EntityRemovalSystem(7));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            engine.update(1);
        }, 0, 1);
    }

    private void loadConfigItems(){
        ItemConstructor constructor = new ItemConstructor();
        for (File file : getDataFolder().listFiles()) {
            try {
                Map<String, Object> map = new Yaml().load(new FileInputStream(file));
                ItemType type = constructor.constructItemTypeFromMap(map);

                itemRegistrar.registerItem(type);
            } catch (IllegalAccessException | FileNotFoundException | InvocationTargetException | ClassNotFoundException e) {
                getLogger().warning(String.format("Failed to load %s!", file.getName()));
                getLogger().warning(e.toString());
            }
        }
    }

    public static Engine getEngine() {
        return engine;
    }

    private void initializeItemManager() {
        itemManager = new ItemManagerImpl(new InMemoryItemPersister());
    }

    private void initializeItemRegistrar() {
        itemRegistrar = new ItemRegistrarImpl();

        ItemCommandExecutor executor = new ItemCommandExecutor(itemRegistrar);
        this.getCommand("looty").setExecutor(executor);
        this.getCommand("looties").setExecutor(executor);
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
