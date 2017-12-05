package execution.command;

import decision.Commands;
import execution.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

/**
 * A command that shows the information about all commands
 */
public class HelpCommand implements ICommand
{
    /**
     * Event that triggered this command
     */
    private final GuildMessageReceivedEvent event;

    /**
     * The prefix of every command
     */
    private final String prefix;

    /**
     * The constructor of the help command
     * @param event that triggered this command
     * @param prefix of every command
     */
    public HelpCommand(GuildMessageReceivedEvent event, String prefix)
    {
        this.event = event;
        this.prefix = prefix;
    }

    @Override
    public void run()
    {
        final Message message = event.getMessage();
        String[] command = message.getContent().split(" ");
        boolean errorOccurrence = false;

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.CYAN);


        String printable;

        try{
            //check if argument equals "-COMMAND"
            if (command[1].equalsIgnoreCase(Commands.HELP.getArguments().get(0)))
            {
                embedBuilder.setTitle("Here's a description of the specified command");
                printable = getSpecifiedCommandHelp(Commands.valueOf(command[2].toUpperCase()));
            }
            else if (isACommand(command[1]))
            {
                embedBuilder.setTitle("Here's a description of the specified command");
                printable = getSpecifiedCommandHelp(Commands.valueOf(command[1].toUpperCase()));
            }
            else
            {
                embedBuilder.setTitle("Here are commands I recognise with some arguments which alter their behaviour");
                printable = getAllCommandsHelp();
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            embedBuilder.setTitle("Here are commands I recognise with some arguments which alter their behaviour");
            printable = getAllCommandsHelp();
        }
        catch (IllegalArgumentException e)
        {
            embedBuilder.setTitle("Sorry I don't recognise that command");
            printable = "";
            errorOccurrence = true;
        }

        if (!errorOccurrence)
        {
            message.delete().submit(false);
        }

        embedBuilder.setDescription(printable);

        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    /**
     * checks if the command is equal to a value in the Commands enumeration
     * @param command which is supposed to be equal to a command in the Commands enumeration
     * @return true if the command parameter is equal to a Command, false if it isn't equal
     */
    private boolean isACommand(String command)
    {
        for (Commands commandOption : Commands.values())
        {
            if (commandOption.name().equalsIgnoreCase(command))
            {
                return true;
            }
        }
        return  false;
    }

    /**
     * Gets a string containing the full help of all commands
     * @return a string containing the full help of all commands
     */
    private String getAllCommandsHelp()
    {
        StringBuilder printable = new StringBuilder();

        printable.append(String.format("To initiate any command type: \"%s\" in front of the command%n%n", prefix));

        for(Commands command : Commands.values())
        {
            printable.append(String.format("%s%n", command.toString()));
        }

        return printable.toString();
    }

    /**
     * Gets a string containing the help of a specific command
     * @param command of which the help needs to be obtained
     * @return the help of the specified command
     */
    private String getSpecifiedCommandHelp(Commands command)
    {
        return command.toString();
    }
}
