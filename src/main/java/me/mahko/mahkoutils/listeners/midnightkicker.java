package me.mahko.mahkoutils.listeners;

import me.mahko.mahkoutils.MahkoUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalTime;
import java.util.Calendar;

public class midnightkicker implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (MahkoUtils.getPlugin().getConfig().getBoolean("tasks.midnightshutdown")) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);

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
                        if (!event.getPlayer().hasPermission("mahkoutils.admin")) {
                            event.getPlayer().kickPlayer(ChatColor.RED + "Go to bed, come back at 7 AM");
                        }

                    }
                }


            }

        }
    }


}