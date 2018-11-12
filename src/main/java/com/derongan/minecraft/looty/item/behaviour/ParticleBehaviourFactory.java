package com.derongan.minecraft.looty.item.behaviour;

import com.derongan.minecraft.looty.events.TimerEvent;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.stream.Stream;

public class ParticleBehaviourFactory {
    public static Stream<TypedItemBehaviour> addParticleToHead(Particle particle, long period) {
        return Stream.of(new TypedItemBehaviour<TimerEvent>() {
            @Override
            public Class<TimerEvent> getType() {
                return TimerEvent.class;
            }

            @Override
            public void onEvent(TimerEvent event, Player player) {
                if(event.tickCount() % period == 0)
                    player.getWorld().spawnParticle(particle, player.getEyeLocation().add(0,.1,0), 0);
            }
        });
    }

    public static Stream<TypedItemBehaviour> addParticleToRightHand(Particle particle, long period) {
        return Stream.of(new TypedItemBehaviour<TimerEvent>() {
            @Override
            public Class<TimerEvent> getType() {
                return TimerEvent.class;
            }

            @Override
            public void onEvent(TimerEvent event, Player player) {
                if(event.tickCount() % period == 0)
                    player.getWorld().spawnParticle(particle, getRightHand(player), 0);
            }
        });
    }


    private static Location getRightHand(Player player) {
        double yawRightHandDirection = Math.toRadians(-1 * player.getEyeLocation().getYaw() - 45);
        double x = 0.5 * Math.sin(yawRightHandDirection) + player.getLocation().getX();
        double y = player.getLocation().getY() + 1 + .3;
        double z = 0.5 * Math.cos(yawRightHandDirection) + player.getLocation().getZ();

        return new Location(player.getWorld(), x, y, z);
    }

}
