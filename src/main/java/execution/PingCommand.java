package execution;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

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
        String text = String.format("Pong!%n`" + event.getJDA().getPing() + "ms`");

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.CYAN);
        embedBuilder.setDescription(text);

        event.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}
