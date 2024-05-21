package tsp.commands.command.bukkit;

import org.bukkit.command.CommandSender;

/**
 * @author TheSilentPro (Silent)
 */
public class EmptyBukkitCommandContext<T extends CommandSender> extends BukkitCommandContext<T> {

    public EmptyBukkitCommandContext() {
        //noinspection DataFlowIssue
        super(null, null, null);
    }

}
