package decision;

import execution.*;
import execution.command.HelpCommand;
import execution.command.MakeChannelCommand;
import execution.command.PingCommand;
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
    private final String voiceCreateCategory;

    /**
     * Manages actions taken when what command needs to be executed or not
     * @param properties containing prefix for all commands
     */
    public CommandManager(Properties properties)
    {
        prefix = properties.getProperty("Prefix");
        voiceCreateCategory = properties.getProperty("VoiceCreateCategory");
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
            ICommand executableCommand;
            Commands command = Commands.valueOf(stringCommand.substring(1).toUpperCase());

            switch (command)
            {
                case PING:
                    executableCommand = new PingCommand(event);
                    break;
                case MAKECHANNEL:
                    executableCommand = new MakeChannelCommand(event, voiceCreateCategory);
                    break;
                case HELP:
                    executableCommand = new HelpCommand(event, prefix);
                    break;
                default:
                    return;
            }

            new Thread(executableCommand).start();
        }
        catch (IllegalArgumentException ignored)
        { }
    }
}
