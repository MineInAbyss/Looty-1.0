package com.derongan.minecraft.looty.item.handling;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.derongan.minecraft.looty.Looty;
import com.derongan.minecraft.looty.item.ItemAction;
import com.derongan.minecraft.looty.item.ItemActionTarget;
import com.derongan.minecraft.looty.item.components.internal.ActionTargetComponent;
import com.derongan.minecraft.looty.item.components.internal.ItemOwnerComponent;
import com.derongan.minecraft.looty.item.components.internal.ItemSourceComponent;
import org.bukkit.FluidCollisionMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

import java.util.Optional;

public class ItemPlayerEventListener implements Listener {
    private ItemRegistrar itemRegistrar;
    private ProjectileRegistrar projectileRegistrar;

    public ItemPlayerEventListener(ItemRegistrar itemRegistrar, ProjectileRegistrar projectileRegistrar) {
        this.itemRegistrar = itemRegistrar;
        this.projectileRegistrar = projectileRegistrar;
    }

    @EventHandler
    public void onPlayerEvent(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getHand() == EquipmentSlot.HAND) {
            doActions(event, event.getPlayer(), ActionTrigger.RIGHT);
        }
    }

    @EventHandler
    public void onPlayerAnimationEvent(PlayerAnimationEvent event) {
        if (event.getAnimationType() == PlayerAnimationType.ARM_SWING) {
            doActions(event, event.getPlayer(), ActionTrigger.LEFT);
        }
    }

    /**
     * Cancel projectile damage
     * @param entityDamageByEntityEvent
     */
    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent entityDamageByEntityEvent){
        if(projectileRegistrar.getItemType(entityDamageByEntityEvent.getDamager()).isPresent()){
            entityDamageByEntityEvent.setDamage(0);
        }
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent hitEvent) {
        Projectile proj = hitEvent.getEntity();

        projectileRegistrar.getItemType(proj).ifPresent(itemTypeAndOrigin -> {
            Engine engine = Looty.getEngine();


            itemTypeAndOrigin.itemType.getActions(ActionTrigger.PROJECTILE_HIT)
                    .stream()
                    .filter(c -> handlesTarget(c, hitEvent))
                    .forEach(b -> {
                        Entity entity = new Entity();
                        b.getComponentFactories().forEach(c -> {
                            entity.add(c.createComponent());
                        });

                        //TODO reuse origin?
                        entity.add(new ItemOwnerComponent(itemTypeAndOrigin.origin));
                        entity.add(new ActionTargetComponent(proj.getLocation()));

                        engine.addEntity(entity);
                    });
        });
    }


    private void doActions(Cancellable event, Player player, ActionTrigger type) {
        RayTraceResult rayTraceResult = player
                .getWorld()
                .rayTrace(player.getEyeLocation(), player.getEyeLocation().getDirection(), 5, FluidCollisionMode.NEVER, true, .1, (entity -> entity != player));

        ItemOwnerComponent owner = new ItemOwnerComponent(player);

        itemRegistrar.getItemType(getPlayerMainHand(player)).ifPresent(a -> {
            Engine engine = Looty.getEngine();

            ActionTargetComponent target;

            if (rayTraceResult != null) {
                target = new ActionTargetComponent(rayTraceResult.getHitPosition().toLocation(player.getWorld()));
            } else {
                target = new ActionTargetComponent(player.getTargetBlock(null, 5).getLocation());
            }

            ItemSourceComponent itemSourceComponent = new ItemSourceComponent(a);

            ActionTargetComponent finalTarget = target;
            a.getActions(type)
                    .stream()
                    .filter(c -> handlesTarget(c, rayTraceResult))
                    .forEach(b -> {
                        Entity entity = new Entity();
                        b.getComponentFactories().forEach(c -> {
                            entity.add(c.createComponent());
                        });
                        entity.add(owner);
                        entity.add(finalTarget);
                        entity.add(itemSourceComponent);
                        engine.addEntity(entity);
                    });

            event.setCancelled(true);
        });
    }

    private boolean handlesTarget(ItemAction action, RayTraceResult rayTraceResult) {
        if (rayTraceResult == null) {
            return action.getTargets().contains(ItemActionTarget.ALL) || action.getTargets().contains(ItemActionTarget.LOCATION);
        } else if (rayTraceResult.getHitEntity() != null) {
            return action.getTargets().contains(ItemActionTarget.ALL) || action.getTargets().contains(ItemActionTarget.ENTITY);
        } else {
            return action.getTargets().contains(ItemActionTarget.ALL) || action.getTargets().contains(ItemActionTarget.LOCATION) || action.getTargets().contains(ItemActionTarget.BLOCK);
        }
    }

    private boolean handlesTarget(ItemAction action, ProjectileHitEvent hitEvent){
        if(action.getTargets().contains(ItemActionTarget.ALL)){
            return true;
        }

        if(hitEvent.getHitBlock() != null){
            return action.getTargets().contains(ItemActionTarget.BLOCK) || action.getTargets().contains(ItemActionTarget.LOCATION);
        } else if (hitEvent.getHitEntity() != null){
            return action.getTargets().contains(ItemActionTarget.ENTITY);
        } else {
            return action.getTargets().contains(ItemActionTarget.LOCATION);
        }
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            itemRegistrar.getItemType(getPlayerMainHand((Player) event.getDamager())).ifPresent(a -> {
                event.setCancelled(true);
            });
        }
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(BlockBreakEvent event) {
        itemRegistrar.getItemType(getPlayerMainHand(event.getPlayer())).ifPresent(a -> {
            event.setCancelled(true);
        });
    }

    private Optional<Block> getBlockLookedAt(Player player) {
        return Optional.ofNullable(player.getTargetBlock(null, 5));
    }

    private ItemStack getPlayerMainHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    private ItemStack getPlayerOffHand(Player player) {
        return player.getInventory().getItemInOffHand();
    }
}
