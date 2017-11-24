package execution.command;

import execution.ICommand;
import execution.countdown.CountDown;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

/**
 * A command that initiates a countdown
 */
public class CountDownCommand implements ICommand
{
    /**
     * The event that triggered this command
     */
    private final GuildMessageReceivedEvent event;

    /**
     * The constructor of the command
     * @param event that triggered this command
     */
    public CountDownCommand(GuildMessageReceivedEvent event)
    {
        this.event = event;
    }

    /**
     * The execution of the command
     */
    @Override
    public void run()
    {
        Message message = event.getMessage();
        message.delete().submit(false);
        String[] command = message.getContent().split(" ");

        //check if a time parameter was supplied
        if (command.length < 2)
        {
            message.getChannel().sendMessage(String.format("Where do I need to start counting down?%n3, 5, 10... where?")).queue();
            return;
        }

        try
        {
            int from = Integer.parseInt(command[1]);

            //joke message about time travel
            if (from < 0)
            {
                message.getChannel().sendMessage(String.format("Ye, time travel is possible.. but only forward!%nI need a positive number.")).queue();
                return;
            }

            //check for minimum time value required
            if (from < 3)
            {
                message.getChannel().sendMessage("Sorry... Shortest countdown supported is 3 seconds").queue();
                return;
            }

            //timer would have too many delays if the limit was any higher
            if (from > 10)
            {
                message.getChannel().sendMessage("Sorry... Longest countdown supported is 10 seconds").queue();
            }

            //execute the countdown
            new CountDown(from, message.getTextChannel());
        }
        //another value was than a number was given when counting down
        catch (NumberFormatException e)
        {
            message.getChannel().sendMessage(String.format("Please specify a number instead of...%nWhatever you just send")).queue();
        }
    }
}
