package tsp.commands.command.context;

import tsp.commands.command.util.BiOptional;
import tsp.commands.command.util.CommandAssertionException;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * @author TheSilentPro (Silent)
 */
public interface CommandContext<T> {

    T sender();

    String[] rawArgs();

    default BiOptional<String, CommandContext<T>> rawArg(int index, @Nullable String message) {
        if (rawArgs().length >= index) {
            return BiOptional.ofNullable(rawArgs()[index - 1], this);
        } else {
            if (message != null) reply(message);
            return BiOptional.empty(this);
        }
    }

    default BiOptional<String, CommandContext<T>> rawArg(int index) {
        return rawArg(index, null);
    }

    default Argument[] args() {
        Argument[] args = new Argument[rawArgs().length];
        for (int i = 0; i < rawArgs().length; i++) {
            args[i] = new SimpleArgument(i, rawArgs()[i]);
        }
        return args;
    }

    default BiOptional<Argument, CommandContext<T>> arg(int index) {
        return args().length >= index ? BiOptional.ofNullable(args()[index - 1], this) : BiOptional.empty(this);
    }

    default CommandContext<T> args(Consumer<String[]> action) {
        action.accept(rawArgs());
        return this;
    }

    default CommandContext<T> assertion(boolean result) throws CommandAssertionException {
        if (result) {
            return this;
        } else {
            return new EmptyCommandContext<>();
        }
    }

    /**
     * Assert the minimum {@link #args() arguments} length.
     * @param minLength The minimum length.
     * @return Context
     */
    default CommandContext<T> assertLength(int minLength) {
        return assertion(rawArgs().length >= minLength);
    }

    /**
     * Reply to the sender.
     *
     * @param message The message to send.
     * @return Context
     */
    CommandContext<T> reply(String message);

    /**
     * Reply to the sender with multiple messages.
     *
     * @param message The messages to send.
     * @return Context
     */
    default CommandContext<T> reply(String... message) {
        for (String msg : message) {
            reply(msg);
        }
        return this;
    }

}