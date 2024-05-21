package tsp.commands.command.bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import tsp.commands.command.Cmd;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author TheSilentPro (Silent)
 */
public class BukkitCommandContext<T extends CommandSender> implements MinecraftCommandContext<T> {

    @Nonnull
    private final T sender;
    @Nonnull
    private final String[] arguments;
    @Nonnull
    private final Cmd<T, ?, ?> command;

    public BukkitCommandContext(@Nonnull T sender, @Nonnull String[] arguments, @Nonnull Cmd<T, ?, ?> command) {
        this.sender = sender;
        this.arguments = arguments;
        this.command = command;
    }

    @Nonnull
    @Override
    public T sender() {
        return sender;
    }

    @Nonnull
    @Override
    public String[] rawArgs() {
        return arguments;
    }

    @Nonnull
    @Override
    public Cmd<T, ?, ?> command() {
        return command;
    }

    @Override
    public boolean checkPermission(@Nullable String permissionMessage) {
        if (command().getPermission().isEmpty()) {
            return true;
        }

        if (sender.hasPermission(command().getPermission().get())) {
            return true;
        } else {
            command().getPermissionMessage().ifPresent(sender::sendMessage);
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

    public BukkitCommandContext<T> assertOp() {
        if (isOp(null)) {
            return this;
        } else {
            return new EmptyBukkitCommandContext<>();
        }
    }

    @Override
    public BukkitCommandContext<T> reply(String message) {
        sender.sendMessage(message);
        return this;
    }

}
