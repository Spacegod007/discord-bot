package execution.timecheck;

import execution.exceptions.InvalidGuildCategoryException;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.TimerTask;

/**
 * A class to check for empty voicechannels and removes them if they exist for more than 1 minute and are empty
 */
public class VoiceChannelTimeCheck extends TimerTask
{
    private final String autoVoiceChannelCategoryName;
    private final Guild guild;

    public VoiceChannelTimeCheck(Guild guild, String autoVoiceChannelCategoryName) throws InvalidGuildCategoryException
    {
        this.guild = guild;
        this.autoVoiceChannelCategoryName = autoVoiceChannelCategoryName;

        List<Category> guildCategories = guild.getCategoriesByName(autoVoiceChannelCategoryName, true);

        if (guildCategories.size() != 1)
        {
            guild.getDefaultChannel().sendMessage(String.format("This server does not contain a category by the name: '" + autoVoiceChannelCategoryName + "'," +
                    "%nWhich is required for this an automatic channel creation system." +
                    "%nIf you own this discord bot, change the 'VoiceCreateCategory' property in the bot.properties file to the desired category.")).queue();
        }
    }

    @Override
    public void run()
    {
        List<Category> voiceChannelCategory = guild.getCategoriesByName(autoVoiceChannelCategoryName, true);

        for (Category voiceCategory : voiceChannelCategory)
        {
            List<VoiceChannel> voiceChannels = voiceCategory.getVoiceChannels();

            for (VoiceChannel voiceChannel : voiceChannels)
            {
                if (voiceChannel.getMembers().isEmpty() && isTimeOneMinuteLater(voiceChannel.getCreationTime()))
                {
                    voiceChannel.delete().queue();
                }
            }
        }
    }

    private boolean isTimeOneMinuteLater(OffsetDateTime origin)
    {
        return (OffsetDateTime.now().toEpochSecond() - origin.toEpochSecond()) > 60;
    }
}
