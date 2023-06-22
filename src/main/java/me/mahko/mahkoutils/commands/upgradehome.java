package me.mahko.mahkoutils.commands;

import me.mahko.mahkoutils.MahkoUtils;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import net.luckperms.api.LuckPerms;

import java.util.*;


public class upgradehome implements CommandExecutor {

    Boolean timeout;
    LuckPerms luckPerms = MahkoUtils.getLuckPermsApi();
    List<String> uids = new ArrayList<>();
    UUID uid;
    User user;
    int cost;




    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getLogger().info("Only players can issue this command");
            return true;
        }

        cost = 32;

        Player player = (Player) sender;
        
        user = luckPerms.getUserManager().getUser(player.getUniqueId());
        uid = player.getUniqueId();

        if (hasPermission(user, "cmi.command.sethome.2")) {cost=64;}

        if (hasPermission(user, "cmi.command.sethome.3")) {
            player.sendRawMessage(ChatColor.RED + "Max home upgrade reached!");
            return true;
        }

        if(args.length == 0) {
            timeout = true;
            int i = 0;
            for (ItemStack is : player.getInventory().getContents()) {
                if (is != null && is.getType() == Material.DIAMOND_BLOCK){
                    i = i + is.getAmount();
                }
            }

            if (i >= cost) {
                player.sendRawMessage(ChatColor.YELLOW + "This will remove " + cost +" diamond blocks from your inventory, are you sure? " + ChatColor.GREEN + "(You have 10 seconds to /upgradehome confirm)");
                Bukkit.getLogger().info("Player has 32 diamond-blocks");
                uids.add(player.getUniqueId().toString());
                new BukkitRunnable(){
                    @Override
                    public void run(){
                        Boolean finalTimeout = timeout;
                        if (finalTimeout) {
                            uids.clear();
                            player.sendRawMessage(ChatColor.RED + "Upgrade expired!");
                        }
                    }
                }.runTaskLater(MahkoUtils.getPlugin(), 200L);

            } else {
                player.sendRawMessage(ChatColor.RED + "You need " + cost + " diamond blocks for this upgrade!");
                Bukkit.getLogger().info("Player does not have " + cost + " diamond-blocks");
            }

        } else if(args.length == 1) {
            if(args[0].equals("confirm")) {
                timeout = false;
                if(uids.size() != 0) {
                    for(String x : uids) {
                        if(Objects.equals(x, player.getUniqueId().toString())) {
                            int i = 0;
                            for (ItemStack is : player.getInventory().getContents()) {
                                if (is != null && is.getType() == Material.DIAMOND_BLOCK){
                                    i = i + is.getAmount();
                                }
                            }

                            player.getInventory().removeItem(new ItemStack(Material.DIAMOND_BLOCK, cost));
                            player.updateInventory();

                            if (i >= cost) {
                                if(hasPermission(user, "cmi.command.sethome.2")) {
                                    addPermission(uid, "cmi.command.sethome.3");
                                    player.sendRawMessage(ChatColor.GREEN + "Home limit upgraded to 3! (Maximum)");
                                } else {
                                    addPermission(uid, "cmi.command.sethome.2");
                                    player.sendRawMessage(ChatColor.GREEN + "Home limit upgraded to 2!");
                                }
                                uids.clear();
                                return true;


                            } else {
                                player.sendRawMessage(ChatColor.RED + "You need " + cost + " diamond blocks for this upgrade!");
                                Bukkit.getLogger().info("Player does not have 32 diamond-blocks");
                            }
                            Bukkit.getLogger().info("Diamond Blocks confirmed " + i);
                            return true;
                        }

                    }
                    player.sendRawMessage(ChatColor.RED + "Upgradehome not awaiting confirmation.");
                    return true;
                }

                player.sendRawMessage(ChatColor.RED + "Upgradehome not awaiting confirmation.");
                return true;
            }
        }else {

            return true;

        }

        return true;

    }

    public void addPermission(UUID userUuid, String permission) {
        // Load, modify, then save
        luckPerms.getUserManager().modifyUser(userUuid, user -> {
            // Add the permission
            user.data().add(Node.builder(permission).build());
        });
    }

    public boolean hasPermission(User user, String permission) {
        return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }
}
