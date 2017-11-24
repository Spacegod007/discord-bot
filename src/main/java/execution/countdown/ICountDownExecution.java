package execution.countdown;

import net.dv8tion.jda.core.entities.Message;

/**
 * Marks a class that can be executed after a countdown is done
 */
public interface ICountDownExecution
{
    /**
     * Gets executed after the countdown is done
     * @param lastSendMessage the last message that was printed if this needs te be edited to show new results
     */
    void executeAfterCountDown(Message lastSendMessage);
}
