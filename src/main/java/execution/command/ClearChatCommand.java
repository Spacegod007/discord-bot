package execution.command;

import execution.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

/**
 * Supposedly clears the chat
 */
public class ClearChatCommand implements ICommand
{
    /**
     * The event that triggered this command
     */
    private final GuildMessageReceivedEvent event;

    /**
     * Constructs the clearchat command
     * @param event that triggered this command
     */
    public ClearChatCommand(GuildMessageReceivedEvent event)
    {
        this.event = event;
    }

    @Override
    public void run()
    {
        Message message = event.getMessage();
        message.delete().submit(false);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("For the time being this command is not supported by this bot.");
        embedBuilder.setDescription(String.format("%s%n%s%n%s",
                "This is due to Discord's influence and they are currently developing an alternative way to clear chat in a channel.",
                "See: https://feedback.discordapp.com/forums/326712-discord-dream-land/suggestions/12808896-purge-chat",
                "For more information"
        ));
        embedBuilder.setColor(Color.cyan);

        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}
