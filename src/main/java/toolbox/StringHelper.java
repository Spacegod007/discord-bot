package toolbox;

public class StringHelper
{
    private StringHelper()
    { }

    /**
     * Joins an array with with spaces in between
     * @param array values that need to get put in a single line after eachother
     * @param skip x number of values
     * @param insert a value that separates the elements in the single line
     * @return a string that exists of all elements of the array in a line.
     */
    public static String joinArray(String[] array, int skip, String insert)
    {
        StringBuilder returnvalue = new StringBuilder();
        for (int i = skip; i < array.length; i++)
        {
            returnvalue.append(array[i]).append(insert);
        }
        return returnvalue.toString();
    }
}
