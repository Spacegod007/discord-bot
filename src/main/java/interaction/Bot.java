package interaction;

import decision.CommandManager;
import decision.GuildManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.managers.Presence;

import javax.security.auth.login.LoginException;
import java.util.Properties;

/**
 * Main bot class, manages entire bot
 */
public class Bot
{
    /**
     * The JDA which is the main point of interaction for the bot to the library
     */
    private JDA jda;

    /**
     * The properties the bot uses
     */
    private final Properties properties;

    /**
     * Constructs a bot object which manages the interactions
     * @param properties containing the properties which the bot needs to operate
     */
    public Bot(Properties properties)
    {
        this.properties = properties;
        String token = properties.getProperty("DiscordToken");
        try
        {
            jda = new JDABuilder(AccountType.BOT).setToken(token).buildBlocking();
            Presence me = jda.getPresence();

            setPresenceValues(me);
            addEventListeners();

            //set status to online to mark that the bot is done loading
            me.setStatus(OnlineStatus.ONLINE);
        }
        catch (InterruptedException | RateLimitedException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        catch (LoginException e)
        {
            System.out.printf("%s%n%s%n", "Error, login failed", "Invalid token received");
        }
    }

    /**
     * Sets default values of the bot presence
     * @param me is the presence of the bot itself
     */
    private void setPresenceValues(Presence me)
    {
        //set status to busy while loading
        me.setStatus(OnlineStatus.DO_NOT_DISTURB);

        //sets the game the bot is "playing"
        me.setGame(Game.of(properties.getProperty("Playing")));
    }

    /**
     * Adds event listeners to the bot
     */
    private void addEventListeners()
    {
        jda.addEventListener(
                new CommandManager(jda, properties),
                new GuildManager(jda.getGuilds(), properties)
        );
    }
}
