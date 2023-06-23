package me.mahko.mahkoutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class upgradeHomeCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {

        if (args.length == 1) {
            List<String> tabSuggest = new ArrayList<>();
            tabSuggest.add("confirm");
            return tabSuggest;

        } else {
            List<String> tabSuggest = new ArrayList<>();
            tabSuggest.add("");
            return tabSuggest;
        }
    }
}
