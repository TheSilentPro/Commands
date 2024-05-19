package tsp.commands.command;

import tsp.commands.command.context.CommandContext;

import java.util.Optional;

/**
 * @author TheSilentPro (Silent)
 */
public abstract class AbstractCommand<T, H, C extends CommandContext<T>> implements Cmd<T, H, C> {

    private final String name;
    private final String[] aliases;
    private final String permission;
    private final String permissionMessage;

    public AbstractCommand(String permission, String permissionMessage, String name, String... aliases) {
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
        this.permissionMessage = permissionMessage;
    }

    public AbstractCommand(String permission, String name, String... aliases) {
        this(permission, null, name, aliases);
    }

    public AbstractCommand(String name, String... aliases) {
        this(null, null, name, aliases);
    }

    public AbstractCommand(String name, String alias) {
        this(null, null, name, new String[]{alias});
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String[]> getAliases() {
        return Optional.ofNullable(aliases);
    }

    @Override
    public Optional<String> getPermission() {
        return Optional.ofNullable(permission);
    }

    @Override
    public Optional<String> getPermissionMessage() {
        return Optional.ofNullable(permissionMessage);
    }

}