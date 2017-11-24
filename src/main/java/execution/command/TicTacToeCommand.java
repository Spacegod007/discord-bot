package execution.command;

import execution.ICommand;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A game of tic tac toe
 */
public class TicTacToeCommand implements ICommand
{

    /**
     * The event that triggered this command
     */
    private final GuildMessageReceivedEvent event;

    /**
     * A timer to schedule a countdown
     */
    private final Timer timer;

    /**
     * A random object which is used to get a random result
     */
    private final Random random;

    /**
     * The last send message by the bot
     */
    private Message lastSendMessage;

    /**
     * The option chosen by the bot
     */
    private TicTacToeOptions chosenOption;

    /**
     * The constructor of the tic tac toe command
     * @param event which triggered this command
     */
    public TicTacToeCommand(GuildMessageReceivedEvent event)
    {
        this.event = event;

        timer = new Timer();
        random = new Random();
    }

    /**
     * The execution of the command in a separate thread
     */
    @Override
    public void run()
    {
        int countdownFrom = 3;

        CountDown countDown = new CountDown(countdownFrom);

        timer.scheduleAtFixedRate(countDown, 1000, 1000);

    }

    /**
     * Prints the option the bot chooses in chat
     */
    private void printOption()
    {
        lastSendMessage.editMessage(String.format("I choose %s", chosenOption)).queue();
    }

    /**
     * Generates which option is picked
     */
    private void generateOption()
    {
        int randomNumber = random.nextInt(3 - 1 + 1) + 1;

        switch (randomNumber)
        {
            case 1:
                chosenOption = TicTacToeOptions.ROCK;
                break;
            case 2:
                chosenOption = TicTacToeOptions.PAPER;
                break;
            default:
                chosenOption = TicTacToeOptions.SCISSORS;
                break;
        }
    }

    //TODO make this into a separate class which uses an interface to execute a method on an optional overlaying class
    /**
     * A countdown class used to countdown till the command needs to be triggered
     */
    private class CountDown extends TimerTask
    {
        /**
         * A temporary value to see at what point in the countdown the system is
         */
        private int count;

        /**
         * The constructor of the countdown class
         * @param from what number needs to be count downwards
         */
        public CountDown(int from)
        {
            count = from;
        }

        /**
         * The execution of the countdown
         */
        @Override
        public void run()
        {
            if (count == 0)
            {
                this.cancel();
                generateOption();
                printOption();
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
                lastSendMessage = event.getChannel().sendMessage(String.format("%s...", count)).complete();
            }
            count--;
        }
    }
}
