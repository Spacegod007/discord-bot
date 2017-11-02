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

    private static void makeProperties(Properties properties, File file)
    {
        properties.setProperty("Token", "");
        properties.setProperty("Prefix", "");
        properties.setProperty("VoiceCreateCategory", "");

        try (OutputStream outputStream = new FileOutputStream(file))
        {
            properties.store(outputStream, "Properties file stores general bot properties");
            System.out.println("Done creating properties file.");
            System.out.println("Properties file located at:" + System.getProperties().getProperty("line.separator") + file.getPath());
            System.out.println("Please provide it with the requested options before running the program again.");
        }
        catch (IOException ignored) { }
    }
}
