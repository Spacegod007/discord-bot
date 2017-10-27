package interaction;

import decision.CommandManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.Presence;

import javax.security.auth.login.LoginException;
import java.util.Properties;

/**
 * Main bot class, manages entire bot
 */
public class Bot extends ListenerAdapter
{
    private JDA api;
    private Presence me;

    public Bot(Properties properties)
    {
        String token = properties.getProperty("Token");
        try
        {
            api = new JDABuilder(AccountType.BOT).setToken(token).buildBlocking();
            me = api.getPresence();
            me.setGame(Game.of("tweaking myself ;)"));

            api.addEventListener(new CommandManager(properties));
        }
        catch (LoginException | InterruptedException | RateLimitedException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
