package tsp.commands.command.bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * @author TheSilentPro (Silent)
 */
public class BukkitCommandContext<T extends CommandSender> implements MinecraftCommandContext<T> {

    private final T sender;
    private final String[] arguments;

    public BukkitCommandContext(T sender, String[] arguments) {
        this.sender = sender;
        this.arguments = arguments;
    }

    @Override
    public T sender() {
        return sender;
    }

    @Override
    public String[] rawArgs() {
        return arguments;
    }

    @Override
    public boolean checkPermission(String permission, @Nullable String permissionMessage) {
        if (sender.hasPermission(permission)) {
            return true;
        } else {
            if (permissionMessage != null) sender.sendMessage(permissionMessage);
            return false;
        }
    }

    public boolean isConsole(@Nullable String message) {
        if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender) {
            return true;
        } else {
            if (message != null) sender.sendMessage(message);
            return false;
        }
    }

    @Override
    public boolean isPlayer(@Nullable String message) {
        if (sender instanceof Player) {
            return true;
        } else {
            if (message != null) sender.sendMessage(message);
            return false;
        }
    }

    public boolean isOp(@Nullable String message) {
        if (sender.isOp()) {
            return true;
        } else {
            if (message != null) sender.sendMessage(message);
            return false;
        }
    }

    public BukkitCommandContext<T> assertOp(@Nullable String message) {
        isOp(message);
        return this;
    }

    public BukkitCommandContext<T> assertOp() {
        return assertOp(null);
    }

    @Override
    public BukkitCommandContext<T> reply(String message) {
        sender.sendMessage(message);
        return this;
    }

}
