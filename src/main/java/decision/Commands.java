package decision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * An enumeration that lists all the available commands
 */
public enum Commands
{
    /**
     * A command that shows the ping to the server
     */
    PING(new ArrayList<>(Collections.singletonList(
            new CommandArgument("-TIME", "changes the type of time notation")
    )), "A command that shows the ping to the server"),

    /**
     * A command that makes a channel in the specified location
     */
    MAKECHANNEL(new ArrayList<>(Arrays.asList(
            new CommandArgument("-GAME", "instead of specifying the channel name, the game the person is currently playing will be used, if the person isn't playing any game an error message will be shown"),
            new CommandArgument("-NAME", "instead of specifying the channel name, the person his/her name will be used as channelname 'Person's channel'")
    )), "command that makes a channel in the specified location"),

    HELP(new ArrayList<>(Collections.singletonList(
            new CommandArgument("-COMMAND", "specifies a specific command to request the help from")
    )), "A command that shows the information about all commands");

    /**
     * Contains a list of arguments for each command
     */
    private final List<CommandArgument> arguments;

    /**
     * Contains a description of the command
     */
    private final String description;

    /**
     * A command that can be executed by this bot
     * @param arguments that can be used for this command
     * @param description of what the command does
     */
    Commands(List<CommandArgument> arguments, String description)
    {
        this.arguments = arguments;
        this.description = description;
    }

    /**
     * Gets the description of a specified argument
     * @param argumentName specifies of what argument the description needs to be returned
     * @return the description of the argument or null if the command doesn't contain the specified argument
     */
    public String getArgumentDescription(String argumentName)
    {
        for (CommandArgument argument : arguments)
        {
            if (argument.getArgument().equalsIgnoreCase(argumentName))
            {
                return  argument.getDescription();
            }
        }
        return null;
    }

    /**
     * Gets all command arguments as strings in a List
     * @return a list containing strings with all arguments for the specified command
     */
    public List<String> getArguments()
    {
        List<String> returnables = new ArrayList<>();

        for (CommandArgument commandArgument : arguments)
        {
            returnables.add(commandArgument.argument);
        }

        return returnables;
    }

    /**
     * Gets the description of the specified command
     * @return a string containingthe description of the specified command
     */
    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString()
    {
        StringBuilder returnable = new StringBuilder(String.format("%s:%n", super.toString()));
        for (CommandArgument argument : arguments)
        {
            returnable.append(String.format("%s%n", argument.toString()));
        }

        return returnable.toString();
    }

    /**
     * A class containing information for arguments of a command
     */
    static class CommandArgument
    {
        private final String argument;
        private final String description;

        /**
         * Contains information for arguments of a command
         * @param argument specifies the argument
         * @param description specifies the description of the argument
         */
        CommandArgument(String argument, String description)
        {
            this.argument = argument;
            this.description = description;
        }

        /**
         * Gets the argument
         * @return a string containing the argument
         */
        String getArgument()
        {
            return argument;
        }

        /**
         * Gets the description of the argument
         * @return a string containing the description of the argument
         */
        String getDescription()
        {
            return description;
        }

        @Override
        public String toString()
        {
            return String.format("%s: %s", argument, description);
        }
    }
}