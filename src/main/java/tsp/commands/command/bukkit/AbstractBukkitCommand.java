package tsp.commands.command.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.commands.command.AbstractCommand;

import java.util.List;

/**
 * @author TheSilentPro (Silent)
 */
public abstract class AbstractBukkitCommand
        extends AbstractCommand<CommandSender, JavaPlugin, BukkitCommandContext<CommandSender>>
        implements BukkitCommand<JavaPlugin>, CommandExecutor, TabCompleter
{

    public AbstractBukkitCommand(String permission, String permissionMessage, String name, String... aliases) {
        super(permission, permissionMessage, name, aliases);
    }

    public AbstractBukkitCommand(String permission, String name, String... aliases) {
        super(permission, name, aliases);
    }

    public AbstractBukkitCommand(String name, String... aliases) {
        super(name, aliases);
    }

    public AbstractBukkitCommand(String name, String alias) {
        super(name, alias);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        handle(new BukkitCommandContext<>(commandSender, strings));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return onTab(new BukkitCommandContext<>(commandSender, strings));
    }

    @Override
    public void register(JavaPlugin plugin) {
        //noinspection DataFlowIssue
        plugin.getCommand(getName()).setExecutor(this);
    }

}