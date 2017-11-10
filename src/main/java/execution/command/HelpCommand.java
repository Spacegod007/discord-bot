package execution.command;

import decision.Commands;
import execution.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A command that shows the information about all commands
 */
public class HelpCommand implements ICommand
{
    private final GuildMessageReceivedEvent event;

    public HelpCommand(GuildMessageReceivedEvent event)
    {
        this.event = event;
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

    private String getAllCommandsHelp()
    {
        StringBuilder printable = new StringBuilder();

        for(Commands command : Commands.values())
        {
            printable.append(String.format("%s%n", command.toString()));
        }

        return printable.toString();
    }

    private String getSpecifiedCommandHelp(Commands command)
    {
        return command.toString();
    }
}
