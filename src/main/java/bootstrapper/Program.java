package bootstrapper;

import interaction.Bot;

import java.util.Properties;

import static bootstrapper.PropertiesManager.checkForProperties;
import static bootstrapper.PropertiesManager.getProperties;

/**
 * Program class
 * Initialises entire project
 */
class Program
{
    /**
     * Main starting point of application
     * @param args arguments to alter program behaviour
     */
    public static void main(String[] args)
    {
        Properties properties = getProperties();

        if (properties != null)
        {
            if (checkForProperties(properties))
            {
                new Bot(properties);
            }
            else
            {
                System.exit(-1);
            }
        }
    }
}
