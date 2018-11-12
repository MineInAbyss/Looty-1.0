package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.events.TimerEvent;
import com.derongan.minecraft.looty.item.ItemPlayerEventListener;
import com.derongan.minecraft.looty.item.ItemRarity;
import com.derongan.minecraft.looty.item.ItemRegistrar;
import com.derongan.minecraft.looty.item.ItemRegistrarImpl;
import com.derongan.minecraft.looty.item.behaviour.BehaviourFilterFactory;
import com.derongan.minecraft.looty.item.behaviour.ParticleBehaviourFactory;
import com.derongan.minecraft.looty.item.items.ItemType;
import com.derongan.minecraft.looty.item.items.ItemTypeBuilder;
import com.derongan.minecraft.looty.world.chunk.ChunkListener;
import com.derongan.minecraft.looty.world.entity.EntityItemManager;
import com.derongan.minecraft.looty.world.entity.EntityItemManagerImpl;
import com.derongan.minecraft.looty.world.entity.creation.strategies.ArmorStandItemCreationStrategy;
import com.derongan.minecraft.looty.world.item.InMemoryItemPersister;
import com.derongan.minecraft.looty.world.item.ItemManager;
import com.derongan.minecraft.looty.world.item.ItemManagerImpl;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

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

        ItemType itemType = new ItemTypeBuilder()
                .setDurability(5)
                .setMaterial(Material.DIAMOND_PICKAXE)
                .setName("Blaze Reap")
                .setRarity(ItemRarity.FIRST_GRADE)
                .setLore(Arrays.asList("An abnormally large pickaxe", "that contains Everlasting Gunpowder."))
                .addBehaviour(BehaviourFilterFactory.onPlayerHitSolid(this::doBlaze))
                .addBehaviour(ParticleBehaviourFactory.addParticleToRightHand(Particle.SMOKE_NORMAL, 2))
                .build();

        itemRegistrar.registerItem(itemType);

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


        AtomicLong ticks = new AtomicLong();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            listener.onTimerEvent(new TimerEvent(ticks.get()));
            ticks.addAndGet(2);
        }, 0, 1);

        pluginManager.registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
        entityItemManager.stop();
    }

    private void doBlaze(Player player, Location target) {
        if(!player.getEyeLocation().getBlock().isLiquid()) {
            player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, target, 3);

            player.getWorld().playSound(target, Sound.ENTITY_GENERIC_EXPLODE, 2f, .7f);

            player.getWorld().getNearbyEntities(target, 2, 2, 2).forEach(b -> {
                if (b instanceof LivingEntity)
                    ((LivingEntity) b).damage(10);
            });
        }
    }


}
