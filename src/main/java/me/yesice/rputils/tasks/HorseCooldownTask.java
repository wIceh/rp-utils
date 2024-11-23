package me.yesice.rputils.tasks;

import me.yesice.rputils.RpUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class HorseCooldownTask extends BukkitRunnable {

    @Override
    public void run() {
        Map<UUID, Long> horseCooldown = RpUtils.getInstance().getHorseManager().getHorseCooldown();

        for (Map.Entry<UUID, Long> entry : horseCooldown.entrySet()) {
            UUID key = entry.getKey();
            long value = entry.getValue();

            long time = System.currentTimeMillis() - value;
            long seconds = time / 1000;

            if (seconds >= 300) {
                horseCooldown.remove(key);
                return;
            }
        }
    }
}
