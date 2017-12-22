package decision;

import execution.ContinuationCommand;
import net.dv8tion.jda.core.JDA;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Manages commands with followup commands
 */
public class ContinuationCommandManager
{
    /**
     * Active commands
     */
    private final List<ContinuationCommand> executedCommands;

    /**
     * jda for interaction with services
     */
    private final JDA jda;

    /**
     * Makes sure methods won't overlap in different threads
     */
    private final Object synchronize;

    /**
     * Constructs the manager of commands with followups
     * @param jda for interaction with discord services
     */
    ContinuationCommandManager(JDA jda)
    {
        this.jda = jda;

        synchronize = new Object();
        executedCommands = new ArrayList<>();
     /*
      Timer to check when a continuationCommand has expired its duration
     */
        Timer activeTimer = new Timer();

        activeTimer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                removeTenMinuteActiveContinuations();
            }
        }, 0, 60000);
    }

    /**
     * Checks if the user of the new continuation command is already running a continuation command
     * And adds it to a list of existing continuations
     * @param newContinuation to be executed
     */
    public void checkRunningContinuations(ContinuationCommand newContinuation)
    {
        synchronized (synchronize)
        {
            for (int i = 0; i < executedCommands.size(); i++)
            {
                ContinuationCommand continuationCommand = executedCommands.get(i);

                if (newContinuation.getUser().equals(continuationCommand.getUser()))
                {
                    executedCommands.remove(continuationCommand);
                    jda.removeEventListener(continuationCommand);
                    break;
                }
            }

            executedCommands.add(newContinuation);
            jda.addEventListener(newContinuation);
        }
    }

    /**
     * Removes continuation commands which have been running for over 10 minutes
     */
    private void removeTenMinuteActiveContinuations()
    {
        synchronized (synchronize)
        {
            for (int i = 0; i < executedCommands.size(); )
            {
                ContinuationCommand continuationCommand = executedCommands.get(i);
                OffsetDateTime currentTime = OffsetDateTime.now();

                if (currentTime.toEpochSecond() > (continuationCommand.getLastInteraction().toEpochSecond() + 600))
                {
                    executedCommands.remove(continuationCommand);
                } else
                {
                    i++;
                }
            }
        }
    }
}
