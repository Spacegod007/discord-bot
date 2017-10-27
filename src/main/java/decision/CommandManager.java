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
        String messageContent = message.getContent();
        String[] command = messageContent.split(" ");

        ICommand executableCommand = null;

        if (!command[0].startsWith(prefix))
            return;

        if (command[0].equalsIgnoreCase("!ping"))
        {
            executableCommand = new PingCommand(event);
        }

        if (executableCommand != null)
        {
            new Thread(executableCommand).start();
        }
    }
}
