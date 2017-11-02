package execution.exceptions;

import net.dv8tion.jda.core.entities.Guild;

/**
 * Created by Jordi van Roij on 02-Nov-17.
 */
public class InvalidGuildCategoryException extends Exception
{
    private Guild guild;

    public InvalidGuildCategoryException(Guild guild, String message)
    {
        super(message);
        this.guild = guild;
    }

    public Guild getGuild()
    {
        return guild;
    }
}
