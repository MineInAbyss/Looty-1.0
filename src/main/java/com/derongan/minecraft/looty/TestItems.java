package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.item.ActionBuilder;
import com.derongan.minecraft.looty.item.ActionTarget;
import com.derongan.minecraft.looty.item.ItemAction;
import com.derongan.minecraft.looty.item.ItemActionTarget;
import com.derongan.minecraft.looty.item.components.*;
import com.derongan.minecraft.looty.item.handling.ActionType;
import com.derongan.minecraft.looty.item.handling.ItemRarity;
import com.derongan.minecraft.looty.item.handling.ItemRegistrar;
import com.derongan.minecraft.looty.item.handling.items.ItemType;
import com.derongan.minecraft.looty.item.handling.items.ItemTypeBuilder;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.util.Arrays;

class TestItems {
    void registerAllTests(ItemRegistrar registrar) {
        registrar.registerItem(createSmoker());
        registrar.registerItem(createMessager());
        registrar.registerItem(createPushStick());
    }

    private ItemType createPushStick() {
        ItemAction pushAction = ActionBuilder.create()
                .setActionType(ActionType.LEFT)
                .setTargets(ItemActionTarget.ENTITY)
                .add(TargetComponent.create(ActionTarget.ENTITY))
                .add(VelocityImpartingComponent.create(2))
                .build();

        ItemAction jumpAction = ActionBuilder.create()
                .setActionType(ActionType.LEFT)
                .setTargets(ItemActionTarget.BLOCK)
                .add(TargetComponent.create(ActionTarget.OWNER))
                .add(VelocityImpartingComponent.create(2, VelocityImpartingComponent.From.TARGET))
                .build();

        return new ItemTypeBuilder()
                .setName("Push Stick")
                .setMaterial(Material.WOODEN_SHOVEL)
                .setDurability(5)
                .setRarity(ItemRarity.FOURTH_GRADE)
                .setLore(Arrays.asList("Poosh"))
                .addEntityAction(pushAction)
                .addEntityAction(jumpAction)
                .build();
    }

    private ItemType createMessager() {
        ItemAction messageAction = ActionBuilder.create()
                .setActionType(ActionType.LEFT)
                .setTargets(ItemActionTarget.ALL)
                .add(MessageComponent.create("Hello world"))
                .build();

        return new ItemTypeBuilder()
                .setName("Messager")
                .setMaterial(Material.CLOCK)
                .setDurability(5)
                .setRarity(ItemRarity.THIRD_GRADE)
                .setLore(Arrays.asList("Hello", "You"))
                .addEntityAction(messageAction)
                .build();
    }


    private ItemType createSmoker() {
        ItemAction smokerEffects = ActionBuilder.create()
                .setActionType(ActionType.LEFT)
                .setTargets(ItemActionTarget.ALL)
                .add(AreaComponent.create(3))
                .add(ParticleComponent.create(Particle.SMOKE_LARGE))
                .add(PeriodicComponent.create(40, 20))
                .add(TargetComponent.create(ActionTarget.BLOCK))
                .add(SoundComponent.create(Sound.BLOCK_FIRE_AMBIENT))
                .build();

        ItemAction smokerDamage = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .setActionType(ActionType.LEFT)
                .add(AreaComponent.create(3))
                .add(SelfTargetingComponent.create())
                .add(IgnitingComponent.create())
                .add(TargetComponent.create(ActionTarget.ENTITY))
                .add(PeriodicComponent.create(40, 1))
                .build();


        return new ItemTypeBuilder()
                .setName("Smoker")
                .setLore(Arrays.asList("Whatever", "cool"))
                .setDurability(5)
                .setMaterial(Material.STICK)
                .setRarity(ItemRarity.FIRST_GRADE)
                .addEntityAction(smokerEffects)
                .addEntityAction(smokerDamage)
                .build();
    }
}
