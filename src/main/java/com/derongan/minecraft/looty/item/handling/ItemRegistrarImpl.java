package com.derongan.minecraft.looty.item.handling;

import com.derongan.minecraft.looty.item.handling.items.ItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemRegistrarImpl implements ItemRegistrar {
    private Map<ItemTypeKey, ItemType> itemTypes;

    public ItemRegistrarImpl() {
        itemTypes = new HashMap<>();
    }

    @Override
    public void registerItem(ItemType itemType) {
        itemTypes.put(new ItemTypeKey(itemType), itemType);
    }

    @Override
    public void unregisterItem(ItemType itemType) {
        itemTypes.remove(new ItemTypeKey(itemType));
    }

    @Override
    public Optional<ItemType> getItemType(ItemStack itemStack) {
        return Optional.ofNullable(itemTypes.get(new ItemTypeKey(itemStack)));
    }

    @Override
    public Collection<ItemType> getAllTypes() {
        return Collections.unmodifiableCollection(itemTypes.values());
    }

    private class ItemTypeKey {
        private Material material;
        private short durability;

        public ItemTypeKey(ItemStack stack) {
            material = stack.getType();
            ItemMeta meta = stack.getItemMeta();
            durability = (meta == null) ? 0 : (short) ((Damageable) meta).getDamage();
        }

        public ItemTypeKey(ItemType type) {
            material = type.getMaterial();
            durability = type.getDurability();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ItemTypeKey)) return false;

            ItemTypeKey that = (ItemTypeKey) o;

            if (durability != that.durability) return false;
            return material == that.material;
        }

        @Override
        public int hashCode() {
            int result = material != null ? material.hashCode() : 0;
            result = 31 * result + (int) durability;
            return result;
        }
    }
}
