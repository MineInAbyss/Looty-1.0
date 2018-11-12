package com.derongan.minecraft.looty;

import com.derongan.minecraft.looty.item.ItemRegistrar;
import com.derongan.minecraft.looty.item.items.ItemType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.stream.Collectors;

public class ItemCommandExecutor implements CommandExecutor {
    private ItemRegistrar registrar;

    public ItemCommandExecutor(ItemRegistrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (label.equals("relic")) {
                if (args.length == 0) {
                    return false;
                }
                for (ItemType relicType : registrar.getAllTypes()) {
                    if (relicType.getName().replace(" ", "_").toLowerCase().equals(args[0].toLowerCase())) {
                        player.getInventory().addItem(getItem(relicType));
                        return true;
                    }
                }
            }

            if (label.equals("relics")) {
                player.sendMessage("Relics: " + registrar.getAllTypes().stream()
                        .map(a->a.getRarity().getColor() + a.getName())
                        .map(a -> a.replace(" ", "_"))
                        .collect(Collectors.joining(", ")));
                return true;
            }
        }

        return false;
    }

    private ItemStack getItem(ItemType type) {
        ItemStack item = new org.bukkit.inventory.ItemStack(type.getMaterial());

        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);

        meta.setLore(type.getLore());


        ((Damageable) meta).setDamage(type.getDurability());

        meta.setDisplayName(type.getRarity().getColor() + type.getName());

        item.setItemMeta(meta);

        return item;
    }
}
