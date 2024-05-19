package tsp.commands.command.bukkit;

import tsp.commands.command.context.CommandContext;

import javax.annotation.Nullable;

/**
 * Represents a {@link CommandContext} for minecraft.
 *
 * @author TheSilentPro (Silent)
 */
public interface MinecraftCommandContext<T> extends CommandContext<T> {

    boolean checkPermission(String permission, @Nullable String permissionMessage);

    default boolean checkPermission(String permission) {
        return checkPermission(permission, null);
    }

    /**
     * Assert that the {@link #sender() sender} has a permission.
     *
     * @param permission The permission.
     * @param permissionMessage The permission message to send if they do not.
     * @return Context
     */
    default CommandContext<T> assertPermission(String permission, @Nullable String permissionMessage) {
        return assertion(checkPermission(permission, permissionMessage));
    }

    /**
     * Assert that the {@link #sender() sender} has a permission.
     *
     * @param permission The permission.
     * @return Context
     */
    default CommandContext<T> assertPermission(String permission) {
        return assertPermission(permission, null);
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
    default CommandContext<T> assertConsole(@Nullable String message) {
        return assertion(isConsole(message));
    }

    /**
     * Assert that the {@link #sender() sender} is console.
     *
     * @return Context
     */
    default CommandContext<T> assertConsole() {
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
    default CommandContext<T> assertPlayer(@Nullable String message) {
        return assertion(isPlayer(message));
    }

    /**
     * Assert that the {@link #sender() sender} is a player.
     *
     * @return Context
     */
    default CommandContext<T> assertPlayer() {
        return assertPlayer(null);
    }

}