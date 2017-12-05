package decision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An enumeration that lists all the available commands
 */
@SuppressWarnings("SpellCheckingInspection")
public enum Commands
{
    /**
     * A command that shows all commands there are
     */
    HELP("A command that shows the information about all commands",
            new CommandArgument("-COMMAND", "specifies a specific command to request the help from")
    ),

    /**
     * A command that shows the ping to the server
     */
    PING("A command that shows the ping to the server",
            new CommandArgument("-TIME", "changes the type of time notation")
    ),

    /**
     * A command that makes a channel in the specified location
     */
    MAKECHANNEL("A command that makes a channel in the specified location",
            new CommandArgument("-GAME", "instead of specifying the channel name, the game the person is currently playing will be used, if the person isn't playing any game an error message will be shown"),
            new CommandArgument("-NAME", "instead of specifying the channel name, the person his/her name will be used as channelname 'Person's channel'")
    ),
    MKC("Short for \"MakeChannel\""),

    /**
     * A command to play a game of rock paper scissors
     */
    ROCKPAPERSCISSORS("A game of tic tac toe played in the current channel"
    ),
    RPC("Short for \"Rock Paper Scissors\""),

    /**
     * A command to start a countdown from a specified number
     */
    COUNTDOWN("A command to countdown from a specified number",
            new CommandArgument("[number]", "from what number the countdown needs to start")
    ),

    RANDOMNUMBER("A command to get a random number",
            new CommandArgument("[highest]", "highest value possible"),
            new CommandArgument("[lowest] [highest]", "lowest and highest value possible")
    ),
    RNUM("Short for \"Random Number\""
    ),

    WHOAREYOU("A command which tells you who this bot is"
    ),
    WAY("Short for \"Who Are You\""),

    /**
     * A command which supposedly clears the chat in the current channel
     */
    CLEARCHAT("A command which supposedly clears the chat in the current channel"
    );

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
     * @param description of what the command does
     * @param arguments that can be used for this command
     */
    Commands(String description, CommandArgument ... arguments)
    {
        this.arguments = Arrays.asList(arguments);
        this.description = description;
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

    @Override
    public String toString()
    {
        StringBuilder returnable = new StringBuilder(String.format("%s:%n%s%n", super.toString(), description));
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

        @Override
        public String toString()
        {
            return String.format("%s: %s", argument, description);
        }
    }
}