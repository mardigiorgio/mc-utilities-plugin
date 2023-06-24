package me.mahko.mahkoutils;
import me.mahko.mahkoutils.commands.upgradehome;
import me.mahko.mahkoutils.commands.upgradeHomeCompletion;
import me.mahko.mahkoutils.listeners.midnightkicker;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import me.mahko.mahkoutils.tasks.midnightkick;


public final class MahkoUtils extends JavaPlugin implements Listener {

    private static MahkoUtils plugin;
    private static LuckPerms luckPerms;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

        if (provider != null) {
            luckPerms = provider.getProvider();
            getLogger().info("LuckPerms API loaded successfully!");
        } else {
            getLogger().severe("LuckPerms API failed to load, disabling dependent features.");
        }


        if(getConfig().getBoolean("tasks.midnightshutdown")) {
            BukkitTask midnight = new midnightkick().runTaskTimer(this, 0L, 600L);
            getLogger().info("MidnightKick Enabled");
        } else {
            getLogger().info("MidnightKick Disabled");
        }
        getServer().getPluginManager().registerEvents(new midnightkicker(), this);
        getCommand("upgradehome").setExecutor(new upgradehome());
        getCommand("upgradehome").setTabCompleter(new upgradeHomeCompletion());
    }

    public static MahkoUtils getPlugin() {
        return  plugin;
    }

    public static LuckPerms getLuckPermsApi() {
        return luckPerms;
    }
}

