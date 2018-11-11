package com.derongan.minecraft.looty.item;

import com.derongan.minecraft.annotations.event.ExpandEventHandler;
import com.derongan.minecraft.looty.item.behaviour.EventItemBehaviour;
import com.derongan.minecraft.looty.item.items.ItemType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemPlayerEventListener implements Listener {
    private ItemRegistrar itemRegistrar;

    public ItemPlayerEventListener(ItemRegistrar itemRegistrar) {
        this.itemRegistrar = itemRegistrar;
    }

    @ExpandEventHandler(exclude = {PlayerInteractAtEntityEvent.class})
    public void onPlayerEvent(PlayerEvent event) {
        Player player = event.getPlayer();
        playerEventInternal(event, player);
    }

    @EventHandler
    public void onPlayerHitEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            playerEventInternal(event, (Player) event.getDamager());
        }
    }

    static interface B {}
    static interface A extends B{}

    static interface C extends B{}
    static class D implements A,C{}

    private void playerEventInternal(Event event, Player player) {
        ItemStack mainHand = getPlayerMainHand(player);
        ItemStack offHand = getPlayerOffHand(player);

        Optional<ItemType> mainHandItem = itemRegistrar.getItemType(mainHand);
        Optional<ItemType> offHandItem = itemRegistrar.getItemType(offHand);

        mainHandItem.ifPresent(a -> handleEvent(a, event));
        offHandItem.ifPresent(a -> handleEvent(a, event));
    }

    @SuppressWarnings("unchecked")
    private void handleEvent(ItemType itemType, Event event) {
        itemType.getBehaviours()
                .stream()
                .filter(a -> a instanceof EventItemBehaviour)
                .map(a -> (EventItemBehaviour) a)
                .filter(a -> a.getEvent().equals(event.getClass()))
                .forEach(a -> a.onEvent(event));
    }


    private ItemStack getPlayerMainHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    private ItemStack getPlayerOffHand(Player player) {
        return player.getInventory().getItemInOffHand();
    }
}
