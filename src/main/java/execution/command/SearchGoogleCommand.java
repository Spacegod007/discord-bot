package execution.command;

import execution.ICommand;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import toolbox.StringHelper;

public class SearchGoogleCommand implements ICommand
{
    private final Message message;

    public SearchGoogleCommand(GuildMessageReceivedEvent event)
    {
        this.message = event.getMessage();
    }

    @Override
    public void run()
    {
        message.delete().submit(false);
        String[] command = message.getContent().split(" ");
        String query = StringHelper.joinArray(command, 1, " ");

        //TODO remove next statement
        message.getChannel().sendMessage("Will be implemented shortly!").queue();

        printResults(searchGoogle(query));
    }

    /**
     * Searches on google using the query paremeter
     * @param query what needs to be looked up
     * @return results from google search
     */
    private Object searchGoogle(String query)
    {
        //TODO execute search on google
        return null;
    }

    /**
     * Prints results in Discord
     * @param results
     */
    private void printResults(Object results)
    {
        //TODO print returnvalues in readable fashion
    }
}
