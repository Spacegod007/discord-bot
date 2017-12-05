package decision;

import execution.ICommand;
import execution.command.*;
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
        String stringCommand = event.getMessage().getContent().split(" ")[0];

        //checks if the message is not a command
        if (!stringCommand.startsWith(prefix) || stringCommand.length() < 2 || event.getAuthor().isBot())
        {
            return;
        }

        try
        {
            Commands command = Commands.valueOf(stringCommand.substring(1).toUpperCase());

            ICommand executableCommand = getCommandInstance(command, event);

            new Thread(executableCommand).start();
        }
        catch (IllegalArgumentException ignored)
        { /* message was not a command but it looked enough alike to trigger an exception */ }
    }

    /**
     * Gets an instance of the specified command
     * @param command of which an instance is needed
     * @param event that triggered a command
     * @return an ICommand object containing the specified command
     */
    private ICommand getCommandInstance(Commands command, GuildMessageReceivedEvent event)
    {
        switch (command)
        {
            case PING:
                return new PingCommand(event);
            case MAKECHANNEL:
            case MKC:
                return new MakeChannelCommand(event, voiceCreateCategory);
            case HELP:
                return new HelpCommand(event, prefix);
            case CLEARCHAT:
                return new ClearChatCommand(event);
            case ROCKPAPERSCISSORS:
            case RPC:
                return new RockPaperScissorsCommand(event);
            case COUNTDOWN:
                return new CountDownCommand(event);
            case RANDOMNUMBER:
            case RNUM:
                return new RandomNumberCommand(event);
            case WHOAREYOU:
            case WAY:
                return new WhoAreYouCommand(event);
            default:
                return null;
        }
    }
}
