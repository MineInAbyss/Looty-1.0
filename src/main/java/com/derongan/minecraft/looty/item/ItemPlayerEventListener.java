package com.derongan.minecraft.looty.item;

import com.derongan.minecraft.annotations.event.ExpandEventHandler;
import com.derongan.minecraft.looty.events.TimerEvent;
import com.derongan.minecraft.looty.item.behaviour.EventItemBehaviour;
import com.derongan.minecraft.looty.item.items.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemPlayerEventListener implements Listener {
    private ItemRegistrar itemRegistrar;

    public ItemPlayerEventListener(ItemRegistrar itemRegistrar) {
        this.itemRegistrar = itemRegistrar;
    }

    @ExpandEventHandler(exclude = {PlayerInteractAtEntityEvent.class, PlayerStatisticIncrementEvent.class})
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

    public void onTimerEvent(TimerEvent event){
        Bukkit.getOnlinePlayers().forEach(a->playerEventInternal(event, a));
    }

    private void playerEventInternal(Event event, Player player) {
        ItemStack mainHand = getPlayerMainHand(player);
        ItemStack offHand = getPlayerOffHand(player);

        Optional<ItemType> mainHandItem = itemRegistrar.getItemType(mainHand);
        Optional<ItemType> offHandItem = itemRegistrar.getItemType(offHand);

        mainHandItem.ifPresent(a -> handleEvent(a, event, player));
        offHandItem.ifPresent(a -> handleEvent(a, event, player));
    }

    @SuppressWarnings("unchecked")
    private void handleEvent(ItemType itemType, Event event, Player player) {
        itemType.getBehaviours(event.getClass())
                .stream()
                .filter(a -> a instanceof EventItemBehaviour)
                .map(a -> (EventItemBehaviour) a)
                .forEach(a -> a.onEvent(event, player));
    }


    private ItemStack getPlayerMainHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    private ItemStack getPlayerOffHand(Player player) {
        return player.getInventory().getItemInOffHand();
    }
}
