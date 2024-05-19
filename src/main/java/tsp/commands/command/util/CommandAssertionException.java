package tsp.commands.command.util;

/**
 * @author TheSilentPro (Silent)
 */
public class CommandAssertionException extends RuntimeException {

    public CommandAssertionException() {
        super("Command did not pass assertion.");
    }

}