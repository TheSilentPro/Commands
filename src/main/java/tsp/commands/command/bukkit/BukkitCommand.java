package tsp.commands.command.bukkit;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.commands.command.Cmd;

/**
 * @author TheSilentPro (Silent)
 */
public interface BukkitCommand extends Cmd<CommandSender, JavaPlugin, BukkitCommandContext<CommandSender>>, CommandExecutor, TabCompleter {}