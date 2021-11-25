package commands;

import interaction.User;

public interface Command {
    boolean execute(String argument);
    boolean execute();
    boolean execute(String argument, Object oArgument);
    boolean execute(String argument, Object oArgument, User user);
    boolean execute(User user);
}
