package bootstrapper;

import interaction.Bot;

import java.io.*;
import java.util.Properties;

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

        new Bot(properties);
    }

    /**
     * Loads properties from properties/bot.properties file
     * @return properties object containing main settings for the application
     */
    private static Properties getProperties()
    {
        File file = new File("properties/bot.properties");
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream(file))
        {
            properties.load(inputStream);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return properties;
    }
}
