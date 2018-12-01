package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.item.ActionBuilder;
import com.derongan.minecraft.looty.item.ActionTarget;
import com.derongan.minecraft.looty.item.ItemAction;
import com.derongan.minecraft.looty.item.ItemActionTarget;
import com.derongan.minecraft.looty.item.components.*;
import com.derongan.minecraft.looty.item.handling.ActionTrigger;
import com.derongan.minecraft.looty.item.handling.ItemRarity;
import com.derongan.minecraft.looty.item.handling.ItemRegistrar;
import com.derongan.minecraft.looty.item.handling.items.ItemType;
import com.derongan.minecraft.looty.item.handling.items.ItemTypeBuilder;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.util.Arrays;

public class TestItems {
    void registerAllTests(ItemRegistrar registrar) {
        registrar.registerItem(createSmoker());
        registrar.registerItem(createMessager());
        registrar.registerItem(createPushStick());
        registrar.registerItem(createIncinerator());
        registrar.registerItem(createSucker());
        registrar.registerItem(createPucker());
        registrar.registerItem(createBlazeReap());
        registrar.registerItem(createPushJumper());
        registrar.registerItem(createDogFighter());
    }

    public static ItemType createBlazeReap() {
        ItemAction damageAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.BLOCK, ItemActionTarget.ENTITY)
                .add(TargetComponent.create(ActionTarget.ENTITY))
                .add(SelfTargetingComponent.create())
                .add(AreaComponent.create(3))
                .add(DamageComponent.create(5))
                .build();

        ItemAction effectAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.BLOCK, ItemActionTarget.ENTITY)
                .add(TargetComponent.create(ActionTarget.BLOCK))
                .add(ParticleComponent.create(Particle.EXPLOSION_HUGE))
                .add(SoundComponent.create(Sound.ENTITY_GENERIC_EXPLODE))
                .build();

        return new ItemTypeBuilder()
                .setName("Blaze Reap")
                .setMaterial(Material.DIAMOND_PICKAXE)
                .setDurability(5)
                .setRarity(ItemRarity.SPECIAL_GRADE)
                .setLore(Arrays.asList("Big Pickaxe OK"))
                .addEntityAction(damageAction, ActionTrigger.LEFT)
                .addEntityAction(effectAction, ActionTrigger.LEFT)
                .build();
    }

    private ItemType createPucker() {
        ItemAction damageAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.ENTITY))
                .add(BeamComponent.create(100))
                .add(AreaComponent.create(2))
                .add(IgnitingComponent.create(50))
                .build();

        ItemAction suckAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.ENTITY))
                .add(BeamComponent.create(100))
                .add(AreaComponent.create(5))
                .add(VelocityImpartingComponent.create(5, VelocityImpartingComponent.From.OWNER))
                .build();

        ItemAction effectAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.BLOCK))
                .add(BeamComponent.create(100))
                .add(AreaComponent.create(2))
                .add(ParticleComponent.create(Particle.FLAME))
                .add(SoundComponent.create(Sound.AMBIENT_UNDERWATER_ENTER))
                .build();

        return new ItemTypeBuilder()
                .setName("Pucker")
                .setMaterial(Material.TORCH)
                .setDurability(7)
                .setRarity(ItemRarity.SPECIAL_GRADE)
                .setLore(Arrays.asList("Zoop"))
                .addEntityAction(damageAction, ActionTrigger.RIGHT)
                .addEntityAction(suckAction, ActionTrigger.RIGHT)
                .addEntityAction(effectAction, ActionTrigger.RIGHT)
                .build();
    }

    public static ItemType createDogFighter() {
        ItemAction damageAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.ENTITY))
                .add(BeamComponent.create(100))
                .add(AreaComponent.create(3))
                .add(DamageComponent.create(5))
                .build();

        ItemAction suckAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.ENTITY))
                .add(BeamComponent.create(100))
                .add(AreaComponent.create(5))
                .add(VelocityImpartingComponent.create(.2, VelocityImpartingComponent.From.OWNER))
                .build();

        ItemAction effectAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.BLOCK))
                .add(BeamComponent.create(100))
                .add(AreaComponent.create(2))
                .add(ParticleComponent.create(Particle.FLAME))
                .add(SoundComponent.create(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH))
                .build();

        ItemAction otherJump = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.OWNER))
                .add(VelocityImpartingComponent.create(-3, VelocityImpartingComponent.From.TARGET))
                .add(ParticleComponent.create(Particle.VILLAGER_ANGRY))
                .add(SoundComponent.create(Sound.ENTITY_GENERIC_EXPLODE))
                .build();

        return new ItemTypeBuilder()
                .setName("Dogfighter")
                .setMaterial(Material.STONE_SWORD)
                .setDurability(5)
                .setRarity(ItemRarity.TOOL)
                .setLore(Arrays.asList("For Pew Pewing"))
                .addEntityAction(damageAction, ActionTrigger.LEFT)
                .addEntityAction(suckAction, ActionTrigger.LEFT)
                .addEntityAction(effectAction, ActionTrigger.LEFT)
                .addEntityAction(otherJump, ActionTrigger.RIGHT)
                .build();
    }

    private ItemType createSucker() {
        ItemAction damageAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.ENTITY))
                .add(BeamComponent.create(100))
                .add(AreaComponent.create(2))
                .add(IgnitingComponent.create(50))
                .build();

        ItemAction suckAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.ENTITY))
                .add(BeamComponent.create(100))
                .add(AreaComponent.create(5))
                .add(VelocityImpartingComponent.create(-5, VelocityImpartingComponent.From.OWNER))
                .build();

        ItemAction effectAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.BLOCK))
                .add(BeamComponent.create(100))
                .add(AreaComponent.create(2))
                .add(ParticleComponent.create(Particle.SMOKE_NORMAL))
                .add(SoundComponent.create(Sound.AMBIENT_CAVE))
                .build();

        return new ItemTypeBuilder()
                .setName("Sucker")
                .setMaterial(Material.TORCH)
                .setDurability(5)
                .setRarity(ItemRarity.SPECIAL_GRADE)
                .setLore(Arrays.asList("Sloop"))
                .addEntityAction(damageAction, ActionTrigger.RIGHT)
                .addEntityAction(suckAction, ActionTrigger.RIGHT)
                .addEntityAction(effectAction, ActionTrigger.RIGHT)
                .build();
    }

    private ItemType createIncinerator() {
        ItemAction damageAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.ENTITY))
                .add(BeamComponent.create(100))
                .add(AreaComponent.create(3))
                .add(DamageComponent.create(100))
                .build();

        ItemAction effectAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.BLOCK))
                .add(BeamComponent.create(100))
                .add(AreaComponent.create(3))
                .add(ParticleComponent.create(Particle.EXPLOSION_LARGE))
                .add(SoundComponent.create(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE))
                .build();

        return new ItemTypeBuilder()
                .setName("Incinerator")
                .setMaterial(Material.FLINT_AND_STEEL)
                .setDurability(5)
                .setRarity(ItemRarity.SPECIAL_GRADE)
                .setLore(Arrays.asList("Kabooom ur dead ha"))
                .addEntityAction(damageAction, ActionTrigger.RIGHT)
                .addEntityAction(effectAction, ActionTrigger.RIGHT)
                .build();
    }

    private ItemType createPushStick() {
        ItemAction pushAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ENTITY)
                .add(TargetComponent.create(ActionTarget.ENTITY))
                .add(VelocityImpartingComponent.create(2, VelocityImpartingComponent.From.OWNER))
                .build();

        ItemAction jumpAction = ActionBuilder.create()
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
                .addEntityAction(pushAction, ActionTrigger.LEFT)
                .addEntityAction(jumpAction, ActionTrigger.LEFT)
                .build();
    }

    private ItemType createPushJumper() {
        ItemAction jumpAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.OWNER))
                .add(VelocityImpartingComponent.create(4, VelocityImpartingComponent.From.TARGET))
                .build();

        ItemAction otherJump = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(TargetComponent.create(ActionTarget.OWNER))
                .add(VelocityImpartingComponent.create(-4, VelocityImpartingComponent.From.TARGET))
                .build();

        return new ItemTypeBuilder()
                .setName("Push Jumper")
                .setMaterial(Material.IRON_SHOVEL)
                .setDurability(5)
                .setRarity(ItemRarity.FOURTH_GRADE)
                .setLore(Arrays.asList("POOSH MORE"))
                .addEntityAction(jumpAction, ActionTrigger.LEFT)
                .addEntityAction(otherJump, ActionTrigger.RIGHT)
                .build();
    }

    private ItemType createMessager() {
        ItemAction messageAction = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(MessageComponent.create("Hello world"))
                .build();

        return new ItemTypeBuilder()
                .setName("Messager")
                .setMaterial(Material.CLOCK)
                .setDurability(5)
                .setRarity(ItemRarity.THIRD_GRADE)
                .setLore(Arrays.asList("Hello", "You"))
                .addEntityAction(messageAction, ActionTrigger.LEFT)
                .build();
    }


    private ItemType createSmoker() {
        ItemAction smokerEffects = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
                .add(AreaComponent.create(3))
                .add(ParticleComponent.create(Particle.SMOKE_LARGE))
                .add(PeriodicComponent.create(40, 20))
                .add(TargetComponent.create(ActionTarget.BLOCK))
                .add(SoundComponent.create(Sound.BLOCK_FIRE_AMBIENT))
                .build();

        ItemAction smokerDamage = ActionBuilder.create()
                .setTargets(ItemActionTarget.ALL)
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
                .addEntityAction(smokerEffects, ActionTrigger.LEFT)
                .addEntityAction(smokerDamage, ActionTrigger.LEFT)
                .build();
    }
}
