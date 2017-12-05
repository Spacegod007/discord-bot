package execution.command;

import execution.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Random;

import static java.lang.Integer.parseInt;

/**
 * A command that generates a random number
 */
public class RandomNumberCommand implements ICommand
{
    /**
     * The event that triggered this command
     */
    private final GuildMessageReceivedEvent event;

    /**
     * The message send that triggered this command
     */
    private Message message;

    /**
     * The constructor of the random number command
     * @param event that triggered this command
     */
    public RandomNumberCommand(GuildMessageReceivedEvent event)
    {
        this.event = event;
    }


    @Override
    public void run()
    {
        message = event.getMessage();
        message.delete().submit(false);

        String[] command = message.getContent().split(" ");

        int highest = 10;
        int lowest = 0;

        if (command.length > 1)
        {
            try
            {
                highest = parseInt(command[1]);

                if (command.length > 2)
                {
                    highest = parseInt(command[2]);
                    lowest = parseInt(command[1]);
                }
            }
            catch (NumberFormatException e)
            {
                message.getChannel().sendMessage("Sorry, your numbers couldn't be used for some reason :(").queue();

                highest = 10;
                lowest = 0;
            }
        }

        if (highest < 0 || lowest < 0)
        {
            errorMessage("Both the lowest and the highest value have to be positive numbers");
            return;
        }

        if (highest <= lowest)
        {
            errorMessage("The highest value needs to be higher than the lowest value");
            return;
        }

        randomNumberBetween(lowest, highest);
    }

    /**
     * Sends a message in the channel
     * @param send the message to be send
     */
    private void errorMessage(String send)
    {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.CYAN);

        embedBuilder.setDescription(send);

        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    /**
     * Gets a random number between the lowest and highest
     * @param lowest value possible
     * @param highest value possible
     */
    private void randomNumberBetween(int lowest, int highest)
    {
        Random random = new Random(System.currentTimeMillis());

        try
        {
            int randomValue = random.nextInt(highest - lowest) + lowest;
            errorMessage(String.format("Value between %s and %s%nPicked: %s", lowest, highest, randomValue));
        }
        catch (IllegalArgumentException e)
        {
            errorMessage(String.format("Sorry I couldn't figure out what went wrong here but here are the rules for this command:%n" +
                    "- Highest needs to be higher than the lowest%n" +
                    "- All numbers need to be positive%n" +
                    "- All numbers need to be between 0 and %s%n", Integer.MAX_VALUE));
        }
    }
}
