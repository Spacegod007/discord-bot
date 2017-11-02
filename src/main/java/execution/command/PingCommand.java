package execution.command;

import execution.ICommand;
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
    private final GuildMessageReceivedEvent event;

    public PingCommand(GuildMessageReceivedEvent event)
    {
        this.event = event;
    }

    @Override
    public void run()
    {
        Message message = event.getMessage();
        message.delete().submit(false);

        String lineSeparator = System.getProperties().getProperty("line.separator");

        String text = MessageFormat.format("Pong!{0}{1},{0}Your ping is: {2}ms", lineSeparator, message.getAuthor().getName(), event.getJDA().getPing());

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.CYAN);
        embedBuilder.setDescription(text);

        event.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}
