package execution;

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
        String[] command = message.getContent().split(" ");
        if (command.length > 1)
        {
            List<Category> categories = event.getGuild().getCategories();

            for (Category category : categories)
            {
                System.out.println(category.getName());
                if (category.getName().equalsIgnoreCase(autoVoiceChannelCategoryName))
                {
                    category.createVoiceChannel(joinArray(command, 1, " ")).queue();
                    break;
                }
            }
        }
        else
        {
            String authorMention = message.getAuthor().getAsMention();
            String authorName = message.getAuthor().getName();
            String errorMessage = String.format(authorMention + "Command not executed due to missing channel name argument%nPlease specify a name like so: \"!makechannel " + authorName + "'s channel\"");
            message.getChannel().sendMessage(errorMessage).queue();
        }
    }

    /**
     * Joins an array with with spaces in between
     * @param skip x number of values
     * @return a string that
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
