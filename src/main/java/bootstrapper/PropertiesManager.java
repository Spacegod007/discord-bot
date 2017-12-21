package bootstrapper;

import java.io.*;
import java.util.Properties;

/**
 * Manages the properties file and checks if all required properties exist and are available
 */
class PropertiesManager
{
    /**
     * The location where the properties file is located
     */
    private static final File propertiesFile = new File("bot.properties");

    /**
     * A private constructor to make sure this class can't be instantiated
     */
    private PropertiesManager()
    { }

    /**
     * Checks if the properties are available
     * @param properties object which supposedly contains the properties
     * @return true if all properties exist and contain a value else false
     */
    static boolean checkForProperties(Properties properties)
    {
        boolean returnable = false;
        if (properties != null)
        {
            boolean missingKey = false;
            returnable = true;
            for (RequiredProperties requiredProperty : RequiredProperties.values())
            {
                //checks if the property key value exists
                if (!properties.containsKey(requiredProperty.toString()))
                {
                    missingKey = true;
                }

                //checks if the property with the specified key value contains a value
                if (properties.getProperty(requiredProperty.toString()) == null)
                {
                    //prints the specified error message for the missing property
                    System.out.println(requiredProperty.getMissingMessage());
                    returnable = false;
                }
            }

            //automatically remakes the properties file with all keys and currently existing values
            if (missingKey)
            {
                try
                {
                    makeProperties(properties);
                }
                catch (IOException e)
                {
                    System.out.println("Error in fixing missing property keys file");
                    e.printStackTrace();
                }
            }
        }
        return returnable;
    }

    /**
     * Loads properties from properties/bot.properties file
     * @return properties object containing main settings for the application
     */
    static Properties getProperties()
    {
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream(propertiesFile))
        {
            properties.load(inputStream);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error, Properties file not found");
            System.out.println("Creating properties file automatically");

            try
            {
                makeProperties(properties);
            }
            catch (IOException e1)
            {
                System.out.println("Error, something went wrong while creating a new properties file");
                e.printStackTrace();
            }
            return null;
        }
        catch (IOException e)
        {
            System.out.println("Error, something went wrong while reading the properties file");
            e.printStackTrace();
        }

        return properties;
    }

    /**
     * Creates a properties file in the directory of the application
     * @param properties object that stores the properties data
     */
    private static void makeProperties(Properties properties) throws IOException
    {
        if (!properties.isEmpty())
        {
            prepareProperties(properties);
        }

        try (PrintStream printStream = new PrintStream(new FileOutputStream(propertiesFile)))
        {
            System.out.println("Missing properties, (re)creating properties file...");

            printStream.print("#This is a properties file which stores the configuration of all properties.");
            for (RequiredProperties requiredProperty : RequiredProperties.values())
            {
                printStream.print(requiredProperty.getPropertyString());
            }

            System.out.println("Done creating properties file.");
            System.out.println("Properties file located at:" + System.getProperties().getProperty("line.separator") + propertiesFile.getAbsolutePath());
            System.out.println("Please provide it with the requested options before running the program again.");
        }
    }

    /**
     * Prepares the properties key values
     * @param properties object where key values will be stored
     */
    private static void prepareProperties(Properties properties)
    {
        for (RequiredProperties requiredProperty : RequiredProperties.values())
        {
            requiredProperty.setValue(properties.getProperty(requiredProperty.toString()));
        }
    }
}
