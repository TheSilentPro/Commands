package tsp.commands.command.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.commands.command.AbstractCommand;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author TheSilentPro (Silent)
 */
public abstract class AbstractBukkitCommand
        extends AbstractCommand<CommandSender, JavaPlugin, BukkitCommandContext<CommandSender>>
        implements BukkitCommand
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
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        handle(new BukkitCommandContext<>(commandSender, strings, this));
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        return onTab(new BukkitCommandContext<>(commandSender, strings, this));
    }

    @Override
    public void register(JavaPlugin plugin) {
        PluginCommand pluginCommand = plugin.getCommand(getName());
        if (pluginCommand == null) {
            throw new NoSuchElementException("Command '" + getName() + "' is not registered in the plugin.yml!");
        }

        pluginCommand.setExecutor(this);
    }

}