package tsp.commands.command.bukkit;

import tsp.commands.command.context.CommandContext;

import javax.annotation.Nullable;

/**
 * Represents a {@link CommandContext} for minecraft.
 *
 * @author TheSilentPro (Silent)
 */
public interface MinecraftCommandContext<T> extends CommandContext<T> {

    @Override
    default MinecraftCommandContext<T> assertion(boolean result) {
        if (result) {
            return this;
        } else {
            return new EmptyMinecraftCommandContext<>();
        }
    }

    boolean checkPermission(@Nullable String permission);

    default boolean checkPermission() {
        return checkPermission(null);
    }

    /**
     * Assert that the {@link #sender() sender} has a permission.
     *
     * @return Context
     */
    default CommandContext<T> assertPermission() {
        return assertion(checkPermission());
    }

    boolean isConsole(@Nullable String message);

    default boolean isConsole() {
        return isConsole(null);
    }

    /**
     * Assert that the {@link #sender() sender} is console.
     *
     * @param message The message to send if it is not.
     * @return Context
     */
    default MinecraftCommandContext<T> assertConsole(@Nullable String message) {
        return assertion(isConsole(message));
    }

    /**
     * Assert that the {@link #sender() sender} is console.
     *
     * @return Context
     */
    default MinecraftCommandContext<T> assertConsole() {
        return assertConsole(null);
    }

    boolean isPlayer(@Nullable String message);

    default boolean isPlayer() {
        return isPlayer(null);
    }

    /**
     * Assert that the {@link #sender() sender} is a player.
     *
     * @param message The message to send if it is not.
     * @return Context
     */
    default MinecraftCommandContext<T> assertPlayer(@Nullable String message) {
        return assertion(isPlayer(message));
    }

    /**
     * Assert that the {@link #sender() sender} is a player.
     *
     * @return Context
     */
    default MinecraftCommandContext<T> assertPlayer() {
        return assertPlayer(null);
    }

}