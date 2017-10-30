package decision;

import execution.ICommand;
import execution.PingCommand;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Properties;

/**
 * Manages actions taken when what command needs to be executed or not
 */
public class CommandManager extends ListenerAdapter
{
    private final String prefix;

    /**
     * Manages actions taken when what command needs to be executed or not
     * @param properties containing prefix for all commands
     */
    public CommandManager(Properties properties)
    {
        prefix = properties.getProperty("Prefix");
    }

    /**
     * Triggers when a message is received
     * @param event containing message details
     */
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        Message message = event.getMessage();

        String stringCommand = message.getContent().split(" ")[0];

        if (!stringCommand.startsWith(prefix))
        {
            return;
        }

        try
        {
            ICommand executableCommand = null;
            Commands command = Commands.valueOf(stringCommand.substring(1).toUpperCase());

            switch (command)
            {
                case PING:
                    executableCommand = new PingCommand(event);
                    break;
                default:
                    break;
            }

            if (executableCommand != null)
            {
                new Thread(executableCommand).start();
            }
        }
        catch (IllegalArgumentException ignored)
        { }
    }
}
