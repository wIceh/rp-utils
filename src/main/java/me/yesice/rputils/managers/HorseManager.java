package me.yesice.rputils.managers;

import me.yesice.rputils.RpUtils;
import me.yesice.rputils.constans.Permission;
import me.yesice.rputils.utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class HorseManager {

    private final Map<UUID, Long> horseCooldown = new HashMap<>();

    public boolean hasHorse(Player player) {
        return RpUtils.getInstance().getConfig().getStringList("have-horse").contains(player.getUniqueId().toString())
                || player.hasPermission(Permission.SPAWN_HORSE_BYPASS.permission());
    }

    public Optional<Horse> getSpawnedHorse(Player player) {
        World world = player.getLocation().getWorld();
        List<Entity> entities = world.getEntities();

        NamespacedKey key = new NamespacedKey(RpUtils.getInstance(), "horse");
        for (Entity entity : entities) {
            if (!(entity instanceof Horse horse)) continue;
            if (!horse.getPersistentDataContainer().has(key)) continue;

            String owner = horse.getPersistentDataContainer().get(key, PersistentDataType.STRING);
            if (owner == null) continue;
            if (!owner.equals(player.getUniqueId().toString())) continue;

            return Optional.of(horse);
        }

        return Optional.empty();
    }

    public Optional<String> getHorseOwner(Horse horse) {
        NamespacedKey key = new NamespacedKey(RpUtils.getInstance(), "horse");
        if (!horse.getPersistentDataContainer().has(key)) return Optional.empty();

        String owner = horse.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (owner == null) return Optional.empty();

        return Optional.of(owner);
    }

    public void spawnHorse(Player player, double speed, double jumpStrength) {
        Location location = player.getLocation().clone();
        Horse horse = player.getLocation().getWorld().spawn(location, Horse.class);

        horse.customName(Component.text("§a" + Util.toSmallText("cavallo di ") + player.getName()));
        horse.setCustomNameVisible(true);
        horse.getPersistentDataContainer().set(new NamespacedKey(RpUtils.getInstance(), "horse"),
                PersistentDataType.STRING, player.getUniqueId().toString());
        horse.setOwner(player);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.setStyle(Horse.Style.NONE);
        horse.setColor(Horse.Color.BROWN);
        horse.setAgeLock(true);
        horse.setAge(1);
        horse.setRemoveWhenFarAway(false);
        horse.setAI(false);
        if (speed != -1)
            Objects.requireNonNull(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(speed);
        if (jumpStrength != -1) {
            horse.setJumpStrength(jumpStrength);
        } else {
            horse.setJumpStrength(0);
        }
    }

    public List<Horse> getSpawnedHorses(World world) {
        List<Horse> horses = new ArrayList<>();
        List<Entity> entities = world.getEntities();

        NamespacedKey key = new NamespacedKey(RpUtils.getInstance(), "horse");
        for (Entity entity : entities) {
            if (!(entity instanceof Horse horse)) continue;
            if (!horse.getPersistentDataContainer().has(key)) continue;

            horses.add(horse);
        }

        return horses;
    }

    public Optional<Horse> getSpawnedHorse(String uniqueId, World world) {
        List<Horse> spawnedHorses = getSpawnedHorses(world);
        for (Horse horse : spawnedHorses) {
            if (!horse.getUniqueId().toString().equals(uniqueId)) continue;
            return Optional.of(horse);
        }

        return Optional.empty();
    }

    public Map<UUID, Long> getHorseCooldown() {
        return horseCooldown;
    }
}
