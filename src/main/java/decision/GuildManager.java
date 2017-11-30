package decision;

import execution.timecheck.VoiceChannelTimeCheck;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.*;

/**
 * A class that manages all guild actions executed
 */
public class GuildManager extends ListenerAdapter
{
    private final Map<String, Timer> timers;
    private final String voiceChannelCategoryName;

    /**
     * Manages all automated guild interactions
     * @param guilds the bot got added to
     * @param properties containing a "VoiceCreateCategory" key value
     */
    public GuildManager(List<Guild> guilds, Properties properties)
    {
        voiceChannelCategoryName = properties.getProperty("VoiceCreateCategory");

        timers = new HashMap<>();

        //make timers for existing guilds
        for (Guild guild : guilds)
        {
            addVoiceChannelTimerCheck(guild);
        }
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event)
    {
        Guild guild = event.getGuild();


        //add channeltimer for new server
        addVoiceChannelTimerCheck(guild);
        super.onGuildJoin(event);
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event)
    {
        //remove channeltimer for old server
        removeVoiceChannelTimerCheck(event.getGuild());
        super.onGuildLeave(event);
    }

    /**
     * General method that creates a new timer and adds it to the mapping of guild timers
     * @param guild where the timer is dedicated to
     */
    private void addVoiceChannelTimerCheck(Guild guild)
    {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new VoiceChannelTimeCheck(guild, voiceChannelCategoryName),0, 2000);
        //noinspection unchecked
        timers.put(guild.getName(), timer);
    }

    /**
     * General method that removes a timer from the mapping of guild timers
     * @param guild where the timer is dedicated to
     */
    private void removeVoiceChannelTimerCheck(Guild guild)
    {
        //gets timer by guild name and cancels it afterwards
        timers.remove(guild.getName()).cancel();
    }
}
