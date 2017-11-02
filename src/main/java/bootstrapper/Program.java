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

        if (properties != null)
        {
            if (!properties.containsKey("Token"))
            {
                System.out.println("Error, token property not found in properties file.");
            }
            else if (!properties.containsKey("Prefix"))
            {
                System.out.println("Error, Prefix property not found in properties file.");
            }
            else if (!properties.containsKey("VoiceCreateCategory"))
            {
                System.out.println("Error, VoiceCreateCategory property not found in properties file.");
            }
            else
            {
                new Bot(properties);
            }
        }
    }

    /**
     * Loads properties from properties/bot.properties file
     * @return properties object containing main settings for the application
     */
    private static Properties getProperties()
    {
        File file = new File("bot.properties");
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream(file))
        {
            properties.load(inputStream);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error, Properties file not found, please place one in the main directory of this program");
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return properties;
    }
}
