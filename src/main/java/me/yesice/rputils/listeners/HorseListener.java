package me.yesice.rputils.listeners;

import me.yesice.rputils.RpUtils;
import me.yesice.rputils.utils.HorseUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.*;

public class HorseListener implements Listener {

    private static final Map<UUID, Long> horseCooldown = new HashMap<>();

    @EventHandler
    public void onHorseDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Horse horse)) return;

        String owner = HorseUtil.getHorseOwner(horse).orElse(null);
        if (owner == null) return;

        event.getDrops().clear();

        Player player = Bukkit.getPlayer(UUID.fromString(owner));
        if (player == null) return;

        player.sendMessage(Component.text("§4Il tuo cavallo è morto."));
        horseCooldown.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Horse horse = HorseUtil.getSpawnedHorse(player).orElse(null);
        if (horse == null) return;

        horse.remove();
    }

    @EventHandler
    public void onHorseInventoryClick(InventoryClickEvent event) {
        if (!(event.getClickedInventory() instanceof HorseInventory horseInventory)) return;
        InventoryHolder holder = horseInventory.getHolder();
        if (!(holder instanceof Horse horse)) return;

        Horse spawnedHorse = HorseUtil.getSpawnedHorse(horse.getUniqueId().toString(), horse.getWorld()).orElse(null);
        if (spawnedHorse == null) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onHorseInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!(event.getRightClicked() instanceof Horse horse)) return;

        String owner = HorseUtil.getHorseOwner(horse).orElse(null);
        if (owner == null) return;

        if (!player.getUniqueId().toString().equals(owner)) {
            event.setCancelled(true);
            player.sendMessage(Component.text("§4Questo cavallo non ti appartiene."));
        }
    }

    public static Map<UUID, Long> getHorseCooldown() {
        return horseCooldown;
    }
}
