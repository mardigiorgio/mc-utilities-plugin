package me.mahko.mahkoutils.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.util.Calendar;


public class midnightkick extends BukkitRunnable {

    @Override
    public void run() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        //checks if time is past midnight
        LocalTime target = LocalTime.now();
        Boolean targetInZone =
                (target.isAfter(LocalTime.parse("05:00:00"))
                        &&
                        target.isBefore(LocalTime.parse("12:00:00"))
                );

        //checks day of week
        if (day != 7) {
            if (day != 1) {
                    if (targetInZone) {
                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                            Bukkit.getLogger().info("Past midnight kicking all players");
                            player.kickPlayer(ChatColor.RED + "Go to bed, come back at 7 AM");

                        }
                    }

            }


        }
    }
}