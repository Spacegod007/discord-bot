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

    private static boolean checkForProperties(Properties properties)
    {
        if (properties != null)
        {
            if (!properties.containsKey("DiscordToken"))
            {
                System.out.println("Error, token property not found in properties file.");
                return false;
            }
            else if (!properties.containsKey("Prefix"))
            {
                System.out.println("Error, Prefix property not found in properties file.");
                return false;
            }
            else if (!properties.containsKey("VoiceCreateCategory"))
            {
                System.out.println("Error, VoiceCreateCategory property not found in properties file.");
                return false;
            }
            else if (!properties.containsKey("Playing"))
            {
                System.out.println("Error, Playing property not found in properties file.");
                return false;
            }
            return true;
        }
        return false;
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
            System.out.println("Error, Properties file not found");
            System.out.println("Creating properties file automatically");

            makeProperties(properties, file);

            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return properties;
    }

    /**
     * Creates a properties file in the directory of the application
     * @param properties object that stores the properties data
     * @param file object that marks the location of the data
     */
    private static void makeProperties(Properties properties, File file)
    {
        if (properties.isEmpty())
        {
            prepairProperties(properties);
        }

        try (OutputStream outputStream = new FileOutputStream(file))
        {
            properties.store(outputStream, "Properties file stores general bot properties");
            System.out.println("Done creating properties file.");
            System.out.println("Properties file located at:" + System.getProperties().getProperty("line.separator") + file.getPath());
            System.out.println("Please provide it with the requested options before running the program again.");
        }
        catch (IOException ignored) { }
    }

    /**
     * Prepaires the properties key values
     * @param properties object where key values will be stored
     */
    private static void prepairProperties(Properties properties)
    {
        properties.setProperty("DiscordToken", "");
        properties.setProperty("Prefix", "");
        properties.setProperty("VoiceCreateCategory", "");
        properties.setProperty("Playing", "");
    }
}
