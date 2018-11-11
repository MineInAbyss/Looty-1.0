package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.item.ItemPlayerEventListener;
import com.derongan.minecraft.looty.item.ItemRegistrar;
import com.derongan.minecraft.looty.item.ItemRegistrarImpl;
import com.derongan.minecraft.looty.item.behaviour.EventItemBehaviour;
import com.derongan.minecraft.looty.item.behaviour.ItemBehaviour;
import com.derongan.minecraft.looty.item.items.ItemType;
import com.derongan.minecraft.looty.item.items.ItemTypeBuilder;
import com.derongan.minecraft.looty.world.chunk.ChunkListener;
import com.derongan.minecraft.looty.world.entity.EntityItemManager;
import com.derongan.minecraft.looty.world.entity.EntityItemManagerImpl;
import com.derongan.minecraft.looty.world.entity.creation.strategies.ArmorStandItemCreationStrategy;
import com.derongan.minecraft.looty.world.item.InMemoryItemPersister;
import com.derongan.minecraft.looty.world.item.ItemManager;
import com.derongan.minecraft.looty.world.item.ItemManagerImpl;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
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

    private void initializeItemManager(){
        itemManager = new ItemManagerImpl(new InMemoryItemPersister());
    }

    private void initializeItemRegistrar(){
        itemRegistrar = new ItemRegistrarImpl();

        ItemBehaviour itemBehaviour = new TalkingBehaviour();

        ItemType itemType = new ItemTypeBuilder().setDurability(5).setMaterial(Material.DIAMOND_AXE).setName("Test Axe").addBehaviour(itemBehaviour).build();

        itemRegistrar.registerItem(itemType);
    }

    private void initializeEntityItemManager(){
        entityItemManager = new EntityItemManagerImpl(itemManager, new ArmorStandItemCreationStrategy());
        entityItemManager.start();
    }

    private void registerListeners(){
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new ChunkListener(entityItemManager), this);
        pluginManager.registerEvents(new ItemPlayerEventListener(itemRegistrar), this);
    }

    @Override
    public void onDisable() {
        entityItemManager.stop();
    }

    private static class TalkingBehaviour implements EventItemBehaviour<PlayerAnimationEvent> {
        @Override
        public Class<PlayerAnimationEvent> getEvent() {
            return PlayerAnimationEvent.class;
        }

        @Override
        public void onEvent(PlayerAnimationEvent event) {
            if(event.getAnimationType() == PlayerAnimationType.ARM_SWING) {
                Bukkit.broadcastMessage("The Item has been SWUNGD");
            }
        }
    }
}
