package tsp.commands.command.context;

/**
 * @author TheSilentPro (Silent)
 */
public class SimpleArgument implements Argument {

    private final int index;
    private final String name;

    public SimpleArgument(int index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public String name() {
        return name;
    }

}