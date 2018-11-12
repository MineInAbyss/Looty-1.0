package com.derongan.minecraft.looty.item.behaviour;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class BehaviourFilterFactory {
    public static Stream<TypedItemBehaviour> onPlayerHitSolid(BiConsumer<Player, Location> onHitEffect) {
        return Stream.of(onPlayerHitBlockLocation(onHitEffect), onPlayerHitEntityLocation(onHitEffect), onPlayerHitBlockLocationAdventure(onHitEffect));
    }

    private static TypedItemBehaviour onPlayerHitEntityLocation(BiConsumer<Player, Location> onHitEffect) {
        return new TypedItemBehaviour<EntityDamageByEntityEvent>() {
            @Override
            public Class<EntityDamageByEntityEvent> getType() {
                return EntityDamageByEntityEvent.class;
            }

            @Override
            public void onEvent(EntityDamageByEntityEvent event, Player player) {
                onHitEffect.accept(player, event.getEntity().getLocation());
            }
        };
    }

    private static TypedItemBehaviour onPlayerHitBlockLocationAdventure(BiConsumer<Player, Location> onHitEffect) {
        return new TypedItemBehaviour<PlayerAnimationEvent>() {
            @Override
            public Class<PlayerAnimationEvent> getType() {
                return PlayerAnimationEvent.class;
            }

            @Override
            public void onEvent(PlayerAnimationEvent event, Player player) {
                if(player.getGameMode() == GameMode.ADVENTURE) {
                    Block target = player.getTargetBlockExact(5);
                    if(target != null)
                        onHitEffect.accept(player, target.getLocation());
                }
            }
        };
    }

    private static TypedItemBehaviour onPlayerHitBlockLocation(BiConsumer<Player, Location> onHitEffect) {
        return new TypedItemBehaviour<PlayerInteractEvent>() {
            @Override
            public Class<PlayerInteractEvent> getType() {
                return PlayerInteractEvent.class;
            }

            @Override
            public void onEvent(PlayerInteractEvent event, Player player) {
                if(player.getGameMode() != GameMode.ADVENTURE && event.getAction() == Action.LEFT_CLICK_BLOCK)
                    onHitEffect.accept(player, event.getClickedBlock().getLocation());
            }
        };
    }
}
