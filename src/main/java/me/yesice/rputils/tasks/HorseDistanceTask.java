package me.yesice.rputils.tasks;

import me.yesice.rputils.utils.HorseUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HorseDistanceTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Horse horse = HorseUtil.getSpawnedHorse(player).orElse(null);
            if (horse == null) continue;

            if (player.getLocation().distance(horse.getLocation()) >= 15) {
                horse.remove();
                player.sendMessage(Component.text("§4Il tuo cavallo è andato via."));
                return;
            }
        }
    }
}
