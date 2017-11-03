package execution.command;

import execution.ICommand;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

/**
 * Command to create a new channel within the configured voicechannel category
 */
public class MakeChannelCommand implements ICommand
{

    private final GuildMessageReceivedEvent event;
    private final String autoVoiceChannelCategoryName;

    public MakeChannelCommand(GuildMessageReceivedEvent event, String autoVoiceChannelCategoryName)
    {
        this.event = event;
        this.autoVoiceChannelCategoryName = autoVoiceChannelCategoryName;
    }

    @Override
    public void run()
    {
        Message message = event.getMessage();
        message.delete().submit(false);

        String[] command = message.getContent().split(" ");

        if (command.length > 1)
        {
            if (command[1].length() > 1)
            {
                List<Category> categories = event.getGuild().getCategories();

                if (categories == null)
                {
                    errormention(message, "Error, Couldn't make a channel due to missing category '" + autoVoiceChannelCategoryName + "'");
                    return;
                }

                createVoiceChannelInCategory(categories, joinArray(command, 1, " "));
            }
            else
            {
                errormention(message, "Error, A channelname is required to be at least 2 characters long");
            }
        }
        else
        {
            errormention(message, String.format("Error, couldn't create a channel due to missing channelname.%n" +
                    "Please create a channel like so: \"!makeChannel " + message.getAuthor().getName() + "'s channel\""));
        }
    }

    /**
     * Searches for category in list and creates a voice channel in it
     * @param categories list of categories
     * @param channelName name of to be created channel
     */
    private void createVoiceChannelInCategory(List<Category> categories, String channelName)
    {
        for (Category category : categories)
        {
            if (category.getName().equalsIgnoreCase(autoVoiceChannelCategoryName))
            {
                category.createVoiceChannel(channelName).queue();
                break;
            }
        }
    }

    /**
     * Sends an error message to the message sender
     * @param message the message that originally got send
     * @param errormessage the message that will be send back in reply
     */
    private void errormention(Message message, String errormessage)
    {
        String authorMention = message.getAuthor().getAsMention();
        String errorMessage = authorMention + " " + errormessage;
        message.getChannel().sendMessage(errorMessage).queue();

    }

    /**
     * Joins an array with with spaces in between
     * @param array values that need to get put in a single line after eachother
     * @param skip x number of values
     * @param insert a value that separates the elements in the single line
     * @return a string that exists of all elements of the array in a line.
     */
    private String joinArray(String[] array, int skip, String insert)
    {
        StringBuilder returnvalue = new StringBuilder();
        for (int i = skip; i < array.length; i++)
        {
            returnvalue.append(array[i]).append(insert);
        }
        return returnvalue.toString();
    }
}
