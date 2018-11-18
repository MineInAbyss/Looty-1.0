package com.derongan.minecraft.looty.item.handling.items;

import com.derongan.minecraft.looty.item.ItemAction;
import com.derongan.minecraft.looty.item.handling.ActionType;
import com.derongan.minecraft.looty.item.handling.ItemRarity;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Material;

import java.util.Collection;
import java.util.List;

public class ItemTypeBuilder {
    private String name;
    private List<String> lore;
    private Material material;
    private short durability;

    private ItemRarity rarity;

    private Multimap<ActionType, ItemAction> entities;

    public ItemTypeBuilder() {
        entities = ArrayListMultimap.create();
    }

    public ItemTypeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemTypeBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemTypeBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemTypeBuilder setDurability(Number durability) {
        this.durability = durability.shortValue();
        return this;
    }

    public ItemTypeBuilder setRarity(ItemRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public ItemTypeBuilder addEntityAction(ItemAction itemAction) {
        entities.put(itemAction.getActionType(), itemAction);
        return this;
    }


    public ItemType build() {
        BuiltItemType type = new BuiltItemType();

        //TODO force not null on some of these
        type.name = name;
        type.lore = lore;
        type.durability = durability;
        type.material = material;
        type.rarity = rarity;
        type.entities = entities;

        return type;
    }

    private class BuiltItemType implements ItemType {
        private String name;
        private List<String> lore;
        private Material material;
        private short durability;
        private ItemRarity rarity;
        private Multimap<ActionType, ItemAction> entities;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<String> getLore() {
            return lore;
        }

        @Override
        public Material getMaterial() {
            return material;
        }

        @Override
        public short getDurability() {
            return durability;
        }

        @Override
        public ItemRarity getRarity() {
            return rarity;
        }

        @Override
        public Collection<ItemAction> getActions(ActionType actionType) {
            return entities.get(actionType);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BuiltItemType)) return false;

            BuiltItemType that = (BuiltItemType) o;

            if (getDurability() != that.getDurability()) return false;
            return getMaterial() == that.getMaterial();
        }

        @Override
        public int hashCode() {
            int result = getMaterial().hashCode();
            result = 31 * result + (int) getDurability();
            return result;
        }
    }
}
