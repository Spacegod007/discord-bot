package execution.command;

import execution.ICommand;
import execution.countdown.CountDown;
import execution.countdown.ICountDownExecution;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

/**
 * A game of tic tac toe
 */
public class TicTacToeCommand implements ICommand, ICountDownExecution
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
     * The constructor of the tic tac toe command
     * @param event which triggered this command
     */
    public TicTacToeCommand(GuildMessageReceivedEvent event)
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
    private void printOption(Message lastSendMessage, TicTacToeOptions option)
    {
        lastSendMessage.editMessage(String.format("I choose %s", option)).queue();
    }

    /**
     * Generates which option is picked
     * @return A TicTacToeOptions option containing the chosen option
     */
    private TicTacToeOptions generateOption()
    {
        int randomNumber = random.nextInt(3 - 1 + 1) + 1;

        switch (randomNumber)
        {
            case 1:
                return TicTacToeOptions.ROCK;
            case 2:
                return TicTacToeOptions.PAPER;
            default:
                return TicTacToeOptions.SCISSORS;
        }
    }
}
