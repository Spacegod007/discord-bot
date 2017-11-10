package execution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An enumeration which represents types of time units
 */
public enum TimeTypes
{
    NULL,

    /**
     * It's a second
     */
    SECOND("SEC"),

    /**
     * 1/10 of a second
     */
    DECISECOND("DS", "DSEC"),

    /**
     * 1/100 of a second
     */
    CENTISECOND("CS", "CSEC"),

    /**
     * 1/1000 of a second
     */
    MILLISECOND("MS", "MSEC"),

    /**
     * 1/1000 of a millisecond
     * (1/1000000 of a second)
     */
    MICROSECOND("US", "USEC"),

    /**
     * 1/1000000 of a microsecond
     * (1/1000000000 of a second)
     */
    NANOSECOND("NS", "NSEC");

    private List<String> shortVersions;

    TimeTypes(String ... shortVersions)
    {
        this.shortVersions = new ArrayList<>(Arrays.asList(shortVersions));
    }

    public List<String> getShortVersions()
    {
        return shortVersions;
    }
}
