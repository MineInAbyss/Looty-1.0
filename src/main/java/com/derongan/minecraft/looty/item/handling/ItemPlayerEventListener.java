package com.derongan.minecraft.looty.item.handling;

import com.derongan.minecraft.looty.item.handling.items.ItemType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemPlayerEventListener implements Listener {
    private ItemRegistrar itemRegistrar;

    public ItemPlayerEventListener(ItemRegistrar itemRegistrar) {
        this.itemRegistrar = itemRegistrar;
    }

    @EventHandler
    public void onPlayerEvent(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
        }
    }

    @EventHandler
    public void onPlayerAnimationEvent(PlayerAnimationEvent event) {
        if(event.getAnimationType() == PlayerAnimationType.ARM_SWING){

        }
    }

    private void playerEventInternal(Event event, Player player) {
        ItemStack mainHand = getPlayerMainHand(player);
        ItemStack offHand = getPlayerOffHand(player);

        Optional<ItemType> mainHandItem = itemRegistrar.getItemType(mainHand);
//        Optional<ItemType> offHandItem = itemRegistrar.getItemType(offHand);

//        mainHandItem.ifPresent(a -> handleEvent(a, event, player));
//        offHandItem.ifPresent(a -> handleEvent(a, event, player));
    }

//    private Optional<Entity> getEntityLookedAt(Player player){
//        player.getWorld().getNearbyEntities()
//        return Optional.ofNullable(null);
//    }

    private Optional<Block> getBlockLookedAt(Player player){
        return Optional.ofNullable(player.getTargetBlock(null, 100));
    }

    private ItemStack getPlayerMainHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    private ItemStack getPlayerOffHand(Player player) {
        return player.getInventory().getItemInOffHand();
    }
}
