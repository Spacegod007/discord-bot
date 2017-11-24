package execution.countdown;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A countdown class used to countdown till the command needs to be triggered
 */
public class CountDown extends TimerTask
{
    /**
     * The timer which is used to countdown every second
     */
    private final Timer timer;

    /**
     * The channel where messages about the countdown are printed
     */
    private final TextChannel channel;

    /**
     * The class where the executeAfterCountDown will be triggered after the countdown is done
     */
    private ICountDownExecution countDownExecution;

    /**
     * A temporary value to see at what point in the countdown the system is
     */
    private int count;

    /**
     * The last message that was send while counting down
     */
    private Message lastSendMessage;

    /**
     * The constructor of the countdown class
     * @param from what number needs to be count downwards
     * @param channel where messages need to be send to
     * @param countDownExecution class where the executeAfterCountDown method needs to be triggered
     */
    public CountDown(int from, TextChannel channel, ICountDownExecution countDownExecution)
    {
        this.count = from;
        this.channel = channel;
        this.countDownExecution = countDownExecution;

        timer = new Timer();

        timer.scheduleAtFixedRate(this, 1000, 1000);

    }

    /**
     * The execution of the countdown
     */
    @Override
    public void run()
    {
        if (count == 0)
        {
            timer.cancel();

            if (countDownExecution != null)
            {
                countDownExecution.executeAfterCountDown(lastSendMessage);
            }
        }
        else if (count > 0)
        {
            printCountdown();
        }
    }

    /**
     * Prints the countdown in chat
     */
    private void printCountdown()
    {
        if (lastSendMessage != null)
        {
            lastSendMessage = lastSendMessage.editMessage(String.format("%s...", count)).complete();
        }
        else
        {
            lastSendMessage = channel.sendMessage(String.format("%s...", count)).complete();
        }
        count--;
    }
}