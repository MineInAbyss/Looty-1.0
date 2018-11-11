package com.derongan.minecraft.looty.item.items;

import com.derongan.minecraft.looty.item.ItemRarity;
import com.derongan.minecraft.looty.item.behaviour.ItemBehaviour;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemTypeBuilder {
    private String name;
    private List<String> lore;
    private Material material;
    private short durability;
    private Collection<ItemBehaviour> behaviours;
    private ItemRarity rarity;

    public ItemTypeBuilder() {
        this.behaviours = new ArrayList<>();
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

    public ItemTypeBuilder addBehaviour(ItemBehaviour behaviour) {
        behaviours.add(behaviour);
        return this;
    }

    public ItemType build() {
        BuiltItemType type = new BuiltItemType();

        //TODO force not null on some of these
        type.name = name;
        type.lore = lore;
        type.behaviours = behaviours;
        type.durability = durability;
        type.material = material;
        type.rarity = rarity;

        return type;
    }

    private class BuiltItemType implements ItemType {
        private String name;
        private List<String> lore;
        private Material material;
        private short durability;
        private Collection<ItemBehaviour> behaviours;
        private ItemRarity rarity;

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
        public Collection<ItemBehaviour> getBehaviours() {
            return behaviours;
        }

        @Override
        public ItemRarity getRarity() {
            return rarity;
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
