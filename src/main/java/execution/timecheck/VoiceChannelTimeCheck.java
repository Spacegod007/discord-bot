package execution.timecheck;

import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.TimerTask;

/**
 * A class to check for empty voice channels and removes them if they exist for more than 1 minute and are empty
 */
public class VoiceChannelTimeCheck extends TimerTask
{
    private final String voiceChannelCategoryName;
    private final Guild guild;

    public VoiceChannelTimeCheck(Guild guild, String autoVoiceChannelCategoryName)
    {
        this.guild = guild;
        voiceChannelCategoryName = autoVoiceChannelCategoryName;

        List<Category> guildCategories = guild.getCategoriesByName(autoVoiceChannelCategoryName, true);

        if (guildCategories == null || guildCategories.isEmpty())
        {
            try
            {
                Category guildCategory = guild.getCategoryById(Long.parseLong(voiceChannelCategoryName));

                if (guildCategory == null)
                {
                    showCategoryError();
                }
            }
            catch (NumberFormatException ignored)
            {
                showCategoryError();
            }
        }
    }

    private void showCategoryError()
    {
        TextChannel defaultChannel = guild.getDefaultChannel();

        if (defaultChannel != null)
        {
            defaultChannel.sendMessage(String.format("This server does not contain a category by the name: '" + voiceChannelCategoryName + "'," +
                    "%nWhich is required for this an automatic channel creation system." +
                    "%nIf you own this discord bot, change the 'VoiceCreateCategory' property in the bot.properties file to the desired category.")).queue();
        }
    }

    @Override
    public void run()
    {
        //get category where channels exists (should only return one value)
        List<Category> voiceChannelCategory = guild.getCategoriesByName(voiceChannelCategoryName, true);

        if (voiceChannelCategory != null || !voiceChannelCategory.isEmpty())
        {
            for (Category voiceCategory : voiceChannelCategory)
            {
                checkVoiceChannels(voiceCategory.getVoiceChannels());
            }
        }
        else
        {
            try
            {
                checkVoiceChannels(guild.getCategoryById(Long.parseLong(voiceChannelCategoryName)).getVoiceChannels());
            }
            catch (NumberFormatException ignored)
            { }
        }
    }

    /**
     * Checks all voice channels for members and if it exists for more than one minute
     * @param voiceChannels all voice channels that need to be checked
     */
    private void checkVoiceChannels(List<VoiceChannel> voiceChannels)
    {
        //get voice channels which will be checked
        for (VoiceChannel voiceChannel : voiceChannels)
        {
            checkVoiceChannel(voiceChannel);
        }
    }

    /**
     * Checks one voice channel for members and if it exists for more than one minute
     * @param voiceChannel that needs to be checked
     */
    private void checkVoiceChannel(VoiceChannel voiceChannel)
    {
        //check if the channel has any members and if it exists longer than one minute
        if (voiceChannel.getMembers().isEmpty() && hasOneMinutePassedSince(voiceChannel.getCreationTime()))
        {
            voiceChannel.delete().queue();
        }
    }

    /**
     * checks if one minute has passed since given time
     * @param origin the moment since when one minute must have passed for this method to return true
     * @return returns true if one minute has passed, returns false if one minute hasn't passed yet
     */
    private boolean hasOneMinutePassedSince(OffsetDateTime origin)
    {
        return (OffsetDateTime.now().toEpochSecond() - origin.toEpochSecond()) > 60;
    }
}
