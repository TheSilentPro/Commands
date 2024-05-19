package tsp.commands.command.bukkit;

import org.bukkit.command.CommandSender;
import tsp.commands.command.Cmd;

/**
 * @author TheSilentPro (Silent)
 */
public interface BukkitCommand<H> extends Cmd<CommandSender, H, BukkitCommandContext<CommandSender>> {}