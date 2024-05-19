package tsp.commands.command;

import tsp.commands.command.context.CommandContext;

import java.util.List;
import java.util.Optional;

/**
 * @author TheSilentPro (Silent)
 */
public interface Cmd<T, H, C extends CommandContext<T>> {

    String getName();

    default Optional<String> getDescription() {
        return Optional.empty();
    }

    default Optional<String[]> getAliases() {
        return Optional.empty();
    }

    default Optional<String> getPermission() {
        return Optional.empty();
    }

    default Optional<String> getPermissionMessage() {
        return Optional.empty();
    }

    void handle(C ctx);

    default List<String> onTab(C ctx) {
        return null;
    }

    void register(H commandHandler);

}