package com.derongan.minecraft.looty.item.handling;

import com.derongan.minecraft.looty.item.ItemType;
import org.bukkit.entity.Entity;

import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * Holds references to entities spawned by items
 */
public class ProjectileRegistrar {
    Map<Entity, ItemTypeAndOrigin> projectileMap;

    public ProjectileRegistrar() {
        // The weak hashmap means that if minecraft decides to release the projectile, we do too for free
        this.projectileMap = new WeakHashMap<>();
    }

    public Optional<ItemTypeAndOrigin> getItemType(Entity entity){
        return Optional.of(projectileMap.get(entity));
    }

    public void registerProjectile(Entity projectile, ItemType itemType, Entity origin){
        projectileMap.put(projectile, new ItemTypeAndOrigin(itemType, origin));
    }


    class ItemTypeAndOrigin{
        ItemType itemType;
        Entity origin;

        public ItemTypeAndOrigin(ItemType itemType, Entity origin) {
            this.itemType = itemType;
            this.origin = origin;
        }
    }
}
