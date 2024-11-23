package me.yesice.rputils;

import me.yesice.rputils.commands.CavalliCommand;
import me.yesice.rputils.listeners.HorseListener;
import me.yesice.rputils.tasks.HorseCooldownTask;
import me.yesice.rputils.tasks.HorseDistanceTask;
import me.yesice.rputils.utils.HorseUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public final class RpUtils extends JavaPlugin {

    private static RpUtils instance;
    private static World world;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;
        world = Bukkit.getWorld(Objects.requireNonNull(getConfig().getString("world")));

        getCommand("cavalli").setExecutor(new CavalliCommand());

        getServer().getPluginManager().registerEvents(new HorseListener(), this);

        new HorseDistanceTask().runTaskTimer(this, 0L, 20L);
        new HorseCooldownTask().runTaskTimer(this, 0L, 20L);
    }

    @Override
    public void onDisable() {
        List<Horse> spawnedHorses = HorseUtil.getSpawnedHorses(world);
        if (!spawnedHorses.isEmpty())
            spawnedHorses.forEach(Entity::remove);
    }

    public static RpUtils getInstance() {
        return instance;
    }

    public static World getWorld() {
        return world;
    }
}
