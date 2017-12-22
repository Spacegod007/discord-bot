package execution;

import decision.ContinuationCommandManager;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.OffsetDateTime;

/**
 * A command that won't stop after a single execution
 */
public abstract class ContinuationCommand extends ListenerAdapter implements ICommand
{
    /**
     * Moment of last interaction with the command
     */
    private OffsetDateTime lastInteraction;

    /**
     * The initiator of the command
     */
    private final User user;

    /**
     * Constructs the continuation command
     * @param manager that manages the continuation commands
     * @param user that initiated the command
     */
    public ContinuationCommand(ContinuationCommandManager manager, User user)
    {
        this.user = user;
        lastInteraction = OffsetDateTime.now();

        manager.checkRunningContinuations(this);
    }

    /**
     * Gets the user that initiated this command
     * @return an User object which contains the initiator of this command
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Gets the last interaction the user initiated
     * @return a date with time of the last interaction
     */
    public OffsetDateTime getLastInteraction()
    {
        return lastInteraction;
    }

    @Override
    public void run()
    {
        lastInteraction = OffsetDateTime.now();
    }
}
