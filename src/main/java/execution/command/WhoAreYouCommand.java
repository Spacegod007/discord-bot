package execution.command;

import execution.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

/**
 * A command which informs the user of who the bot is
 */
public class WhoAreYouCommand implements ICommand
{
    /**
     * The event which triggered this command
     */
    private final GuildMessageReceivedEvent event;

    /**
     * The constructor of this command
     * @param event which triggered this command
     */
    public WhoAreYouCommand(GuildMessageReceivedEvent event)
    {
        this.event = event;
    }

    @Override
    public void run()
    {
        event.getMessage().delete().submit(false);
        TextChannel channel = event.getChannel();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.CYAN);
        embedBuilder.setTitle("Who am I?");
        embedBuilder.setDescription(String.format("I am just a humble bot.%nHappy to help :slight_smile:"));

        channel.sendMessage(embedBuilder.build()).queue();
    }
}
