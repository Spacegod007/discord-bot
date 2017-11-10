package execution.command;

import decision.Commands;
import execution.ICommand;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import toolbox.StringHelper;

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
            //check if argument equals "-NAME"
            if (command[1].equalsIgnoreCase(Commands.MAKECHANNEL.getArguments().get(1)))
            {
                makeChannelFromAuthorName(message);
            }
            //check if argument equals "-GAME"
            else if (command[1].equalsIgnoreCase(Commands.MAKECHANNEL.getArguments().get(0)))
            {
                makeChannelFromAuthorGame(message);
            }
            //check if argument is a channel name
            else if (command[1].length() > 1)
            {
                makeChannelFromGivenName(message, command);
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
     * Gets the game the author is playing and
     * @param message that triggered this command
     */
    private void makeChannelFromAuthorGame(Message message)
    {
        try
        {
            Game game = message.getGuild().getMember(message.getAuthor()).getGame();
            createVoiceChannelInCategory(message, game.getName());
        }
        catch (NullPointerException e)
        {
            message.getChannel().sendMessage( String.format("%s, according to Discord you're not playing anything. Did you mean -name?", message.getAuthor().getAsMention()));
        }
    }

    /**
     * Gets the author name from the message and creates a channel using that name
     * @param message that triggered this command
     */
    private void makeChannelFromAuthorName(Message message)
    {
        String channelName = String.format("%s's channel", message.getAuthor().getName());
        createVoiceChannelInCategory(message, channelName);
    }

    /**
     * Makes a channel using the given parameters
     * @param message that triggered this command
     * @param command which contains the channel name
     */
    private void makeChannelFromGivenName(Message message, String[] command)
    {
        createVoiceChannelInCategory(message, StringHelper.joinArray(command, 1, " "));
    }

    /**
     * Searches for category in list and creates a voice channel in it
     * @param message that was originally send
     * @param channelName name of to be created channel
     */
    private void createVoiceChannelInCategory(Message message, String channelName)
    {
        List<Category> categories = event.getGuild().getCategories();

        if (categories == null)
        {
            errormention(message, "Error, Couldn't make a channel due to missing category '" + autoVoiceChannelCategoryName + "'");
            return;
        }

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
}
