package tsp.commands.command.context;

import javax.annotation.Nullable;

/**
 * An *empty* {@link CommandContext<T>}.
 * Used as a way to stop a chain process.
 *
 * @param <T> Type.
 * @see tsp.commands.command.context.CommandContext#assertion(boolean)
 *
 * @author TheSilentPro (Silent)
 */
public class EmptyCommandContext<T,A> implements CommandContext<T> {

    private static final String[] EMPTY = new String[0];

    @Nullable
    @Override
    public T sender() {
        return null;
    }

    @Override
    public String[] rawArgs() {
        return EMPTY;
    }

    @Override
    public CommandContext<T> reply(String message) {
        return this;
    }

}