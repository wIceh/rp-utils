package me.yesice.rputils.utils;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class ItemUtil {

    public static GuiItem getFiller() {
        return ItemBuilder.from(Material.PAPER)
                .name(Component.text("§r"))
                .model(62)
                .asGuiItem();
    }
}
