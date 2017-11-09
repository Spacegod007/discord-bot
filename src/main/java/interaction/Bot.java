package interaction;

import decision.CommandManager;
import decision.GuildManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
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
    private JDA jda;
    private Properties properties;

    public Bot(Properties properties)
    {
        this.properties = properties;
        String token = properties.getProperty("Token");
        try
        {
            jda = new JDABuilder(AccountType.BOT).setToken(token).buildBlocking();
            Presence me = jda.getPresence();

            //set status to busy while loading
            me.setStatus(OnlineStatus.DO_NOT_DISTURB);

            me.setGame(Game.of(properties.getProperty("Playing")));
            addEventListeners();

            //set status to online when done loading
            me.setStatus(OnlineStatus.ONLINE);
        }
        catch (LoginException | InterruptedException | RateLimitedException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void addEventListeners()
    {
        jda.addEventListener(new GuildManager(jda.getGuilds(), properties));
        jda.addEventListener(new CommandManager(properties));
    }
}
