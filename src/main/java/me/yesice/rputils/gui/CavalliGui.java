package me.yesice.rputils.gui;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.yesice.rputils.listeners.HorseListener;
import me.yesice.rputils.utils.HorseUtil;
import me.yesice.rputils.utils.ItemUtil;
import me.yesice.rputils.utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class CavalliGui {

    public void open(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text(Util.toSmallText("seleziona cosa richiamare")))
                .rows(3)
                .disableAllInteractions()
                .create();

        int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
        for (int index : indexes)
            gui.setItem(index, ItemUtil.getFiller());

        gui.setItem(11, getHorseItem(player, gui));
        gui.setItem(13, getUnavailable());
        gui.setItem(14, getUnavailable());
        gui.setItem(15, getUnavailable());

        gui.open(player);
    }

    private GuiItem getHorseItem(Player player, Gui gui) {
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        profile.setProperty(new ProfileProperty("textures", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVmZjViN2ZhZWMxOTBkMzNlZTYxZmMxNTE3Y2NlMmJlNmM0N2RhYzE5OWQ1MTNjYjEyYzI5MDQxYjcwNTYxMSJ9fX0="));
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setPlayerProfile(profile);
        meta.displayName(Component.text("§a" + Util.toSmallText("cavallo")));
        meta.setCustomModelData(1);
        item.setItemMeta(meta);

        return ItemBuilder.from(item).asGuiItem(event -> {
            Horse horse = HorseUtil.getSpawnedHorse(player).orElse(null);

            if (!HorseUtil.hasHorse(player)) {
                player.sendMessage(Component.text("§4Non hai questo cavallo."));
                return;
            }

            if (horse != null) {
                gui.close(player);
                horse.remove();
                player.sendMessage(Component.text("§7Cavallo rimosso."));
            } else {
                gui.close(player);
                if (HorseListener.getHorseCooldown().containsKey(player.getUniqueId())) {
                    long value = HorseListener.getHorseCooldown().get(player.getUniqueId());
                    long target = value + 300000;

                    long remainingMillis = target - System.currentTimeMillis();
                    long remainingSeconds = remainingMillis / 1000;

                    player.sendMessage(Component.text("§4Il tuo cavallo si riprenderà tra " + Util.formatTime(remainingSeconds)));
                    return;
                }
                HorseUtil.spawnHorse(player, -1, -1);
            }
        });
    }

    private GuiItem getUnavailable() {
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        profile.setProperty(new ProfileProperty("textures", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODBjYmRkNGY0NTRmM2U4MzU5YmNjMmI0Y2I4Njk1NWYwZDZkNmM1OGY5Y2E0ZDNjMGI2ZDkzNjRkMTAwYTljNiJ9fX0="));
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setPlayerProfile(profile);
        meta.displayName(Component.text("§7" + Util.toSmallText("non disponibile")));
        meta.setCustomModelData(1);
        item.setItemMeta(meta);

        return ItemBuilder.from(item).asGuiItem();
    }
}
