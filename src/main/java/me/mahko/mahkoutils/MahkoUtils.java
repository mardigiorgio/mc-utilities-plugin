package me.mahko.mahkoutils;

import me.mahko.mahkoutils.tasks.midnightkick;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.time.LocalTime;
import java.util.Calendar;

public final class MahkoUtils extends JavaPlugin implements Listener {



    @Override
    public void onEnable() {

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);
        if(getConfig().getBoolean("tasks.midnightshutdown")) {
            BukkitTask midnight = new midnightkick().runTaskTimer(this, 0L, 600);
            getLogger().info("MidnightKick Enabled");
        } else {
            getLogger().info("MidnightKick Disabled");
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(getConfig().getBoolean("tasks.midnightshutdown")) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            LocalTime target = LocalTime.now();
            Boolean targetInZone =
                    (target.isAfter( LocalTime.parse( "05:00:00" ))
                            &&
                            target.isBefore( LocalTime.parse( "12:00:00" ))

                    );
            //checks day of week test
            if (day != 7){
                if (day != 1){
                    if (targetInZone) {
                        event.getPlayer().kickPlayer(ChatColor.RED + "Go to bed, come back at 7 AM");
                }
            }


            }

            }

        }

    }

