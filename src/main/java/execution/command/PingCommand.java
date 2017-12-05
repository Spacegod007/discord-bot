package execution.command;

import decision.Commands;
import execution.ICommand;
import execution.TimeTypes;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.text.MessageFormat;

/**
 * A command that prints a message which shows the ping of a user.
 */
public class PingCommand implements ICommand
{
    /**
     * The event that triggered this command
     */
    private final GuildMessageReceivedEvent event;

    /**
     * The constructor of the ping command
     * @param event
     */
    public PingCommand(GuildMessageReceivedEvent event)
    {
        this.event = event;
    }

    @Override
    public void run()
    {
        Message message = event.getMessage();
        String[] command = message.getContent().split(" ");
        message.delete().submit(false);

        long pingValue = message.getJDA().getPing();
        float pingWithDecimals = -1;

        TimeTypes timeType = TimeTypes.MILLISECOND;

        if (command.length > 1)
        {
            //check if the first argument is an allowed parameter
            if (command.length > 2 && command[1].equalsIgnoreCase(Commands.PING.getArguments().get(0)))
            {
                timeType = getTypeFromString(command[2]);
            }
            //check if the first argument is a TimeType value
            else if (isATimeType(command[1]))
            {
                timeType = getTypeFromString(command[1]);
            }

            switch (timeType)
            {
                case SECOND:
                    pingWithDecimals = pingValue;
                    pingWithDecimals /= 1000;
                    break;
                case DECISECOND:
                    pingWithDecimals = pingValue;
                    pingWithDecimals /= 100;
                    break;
                case CENTISECOND:
                    pingWithDecimals = pingValue;
                    pingWithDecimals /= 10;
                    break;
                case MICROSECOND:
                    pingValue *= 1000;
                    break;
                case NANOSECOND:
                    pingValue *= 1000000;
                    break;
                case MILLISECOND:
                case NULL:
                default:
                    break;
            }
        }

        EmbedBuilder embeddedMessage;

        if (pingWithDecimals != -1)
        {
            embeddedMessage = createEmbeddedMessage(message.getAuthor().getName(), String.valueOf(pingWithDecimals), timeType);
        }
        else
        {
            embeddedMessage = createEmbeddedMessage(message.getAuthor().getName(), String.valueOf(pingValue), timeType);
        }

        event.getChannel().sendMessage(embeddedMessage.build()).queue();
    }

    /**
     * Checks gets the TimeType the parameter represents
     * @param type of time value
     * @return the TimeType the type parameter represents (checks on both full name and short versions)
     */
    private TimeTypes getTypeFromString(String type)
    {
        for(TimeTypes timeType : TimeTypes.values())
        {
            if (timeType.name().equalsIgnoreCase(type))
            {
                return timeType;
            }

            for (String shortVersion : timeType.getShortVersions())
            {
                if (shortVersion.equalsIgnoreCase(type))
                {
                    return timeType;
                }
            }
        }

        return TimeTypes.NULL;
    }

    /**
     * checks if the parameter is equal to a TimeType
     * @param timeType that is equal to one of the types in the enumeration TimeTypes
     * @return true if its equal to
     */
    private boolean isATimeType(String timeType)
    {
        for (TimeTypes currentTimeType : TimeTypes.values())
        {
            if (currentTimeType.name().equalsIgnoreCase(timeType))
            {
                return currentTimeType != TimeTypes.NULL;
            }

            for (String shortVersion : currentTimeType.getShortVersions())
            {
                if (shortVersion.equalsIgnoreCase(timeType))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates an embedded message
     * @param authorName The name of the sender of the command
     * @param ping the time it takes for the packages to be received as string
     * @param timeType that was used to calculate the ping
     * @return a pre-build version of the embedded message
     */
    private EmbedBuilder createEmbeddedMessage(String authorName, String ping, TimeTypes timeType)
    {
        String lineSeparator = System.getProperties().getProperty("line.separator");

        String text = MessageFormat.format("Pong!{0}{1},{0}Your ping is: {2}{3}", lineSeparator, authorName, ping, timeType.getShortVersions().get(0));

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.CYAN);
        embedBuilder.setDescription(text);

        return embedBuilder;
    }
}
