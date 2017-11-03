package interaction;

import decision.CommandManager;
import decision.GuildManager;
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
    public Bot(Properties properties)
    {
        String token = properties.getProperty("Token");
        try
        {
            JDA api = new JDABuilder(AccountType.BOT).setToken(token).buildBlocking();
            Presence me = api.getPresence();

            me.setGame(Game.of(properties.getProperty("Playing")));

            //Hook event listeners
            api.addEventListener(new GuildManager(api.getGuilds(), properties));
            api.addEventListener(new CommandManager(properties));

        }
        catch (LoginException | InterruptedException | RateLimitedException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
