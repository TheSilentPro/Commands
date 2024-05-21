package tsp.commands.command.bukkit;

import tsp.commands.command.context.EmptyCommandContext;

import javax.annotation.Nullable;

/**
 * @author TheSilentPro (Silent)
 */
public class EmptyMinecraftCommandContext<T> extends EmptyCommandContext<T> implements MinecraftCommandContext<T> {

    @Override
    public boolean checkPermission(@Nullable String permission) {
        return false;
    }

    @Override
    public boolean isConsole(@Nullable String message) {
        return false;
    }

    @Override
    public boolean isPlayer(@Nullable String message) {
        return false;
    }

}