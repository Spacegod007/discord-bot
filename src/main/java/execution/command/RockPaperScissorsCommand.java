package execution.command;

import execution.ICommand;
import execution.countdown.CountDown;
import execution.countdown.ICountDownExecution;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

/**
 * A game of Rock paper scissors
 */
public class RockPaperScissorsCommand implements ICommand, ICountDownExecution
{

    /**
     * The event that triggered this command
     */
    private final GuildMessageReceivedEvent event;

    /**
     * A random object which is used to get a random result
     */
    private final Random random;

    /**
     * The constructor of the rock paper scissors command
     * @param event which triggered this command
     */
    public RockPaperScissorsCommand(GuildMessageReceivedEvent event)
    {
        this.event = event;

        random = new Random();
    }

    /**
     * The execution of the command in a separate thread
     */
    @Override
    public void run()
    {
        event.getMessage().delete().submit(false);
        int countdownFrom = 3;

        new CountDown(countdownFrom, event.getChannel(), this);
    }

    /**
     * The execution of the command after the countdown is done
     * @param lastSendMessage the last message that was send
     */
    @Override
    public void executeAfterCountDown(Message lastSendMessage)
    {

        printOption(lastSendMessage, generateOption());
    }

    /**
     * Prints the option the bot chooses in chat
     * @param lastSendMessage which will be edited to show the chosen option
     * @param option that was chosen and needs to be printed
     */
    private void printOption(Message lastSendMessage, RockPaperScissorsOptions option)
    {
        lastSendMessage.editMessage(String.format("I choose %s", option)).queue();
    }

    /**
     * Generates which option is picked
     * @return A RockPaperScissorsOptions option containing the chosen option
     */
    private RockPaperScissorsOptions generateOption()
    {
        int randomNumber = random.nextInt(3) + 1;

        switch (randomNumber)
        {
            case 1:
                return RockPaperScissorsOptions.ROCK;
            case 2:
                return RockPaperScissorsOptions.PAPER;
            default:
                return RockPaperScissorsOptions.SCISSORS;
        }
    }
}
