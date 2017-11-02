package decision;

import execution.exceptions.InvalidGuildCategoryException;
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
    Map timers;
    private final String autoVoiceChannelCategoryName;

    public GuildManager(List<Guild> guilds, Properties properties)
    {
        autoVoiceChannelCategoryName = properties.getProperty("VoiceCreateCategory");

        timers = new HashMap<String, Timer>();

        for (Guild guild : guilds)
        {
            addVoiceChannelTimerCheck(guild);
        }
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event)
    {
        addVoiceChannelTimerCheck(event.getGuild());
        super.onGuildJoin(event);
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event)
    {
        removeVoiceChannelTimerCheck(event.getGuild());
        super.onGuildLeave(event);
    }

    private void addVoiceChannelTimerCheck(Guild guild)
    {
        Timer timer = new Timer();
        try
        {
            timer.scheduleAtFixedRate(new VoiceChannelTimeCheck(guild, autoVoiceChannelCategoryName),0, 2000);
        } catch (InvalidGuildCategoryException e)
        {
            e.getGuild().getDefaultChannel().sendMessage(e.getMessage()).queue();
        }
        timers.put(guild.getName(), timer);
    }

    private void removeVoiceChannelTimerCheck(Guild guild)
    {
        timers.remove(guild.getName());
    }
}
