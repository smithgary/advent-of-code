package twentytwentytwo.test;

import org.junit.*;
import twentytwentyone.*;
import static org.junit.Assert.*;
import org.junit.Test;
import twentytwentytwo.Day9;

public class Day9Test {

    String entrySample = "AdventOfCode/src/twentytwentytwo/data/day9sample";
    String entryInput = "AdventOfCode/src/twentytwentytwo/data/day9Input";
    String entryExtended = "AdventOfCode/src/twentytwentytwo/data/day9ExtendedSample";

    private AocTest testDay;
    private String home;

    @Before
    public void beforeEach() {
        testDay = new Day9();
        home = System.getProperty("user.dir");
    }


    @Test
    public void testPart1WithSampleData() {
        testDay.readLines(home + "/" +entrySample, 1);

        String result = testDay.calculatePartOne();

        assertEquals("13", result);

    }

    @Test
    public void testPart1WithTestData() {

        testDay.readLines(home + "/" + entryInput, 1);

        String result = testDay.calculatePartOne();

        assertEquals("6486", result);

    }

    @Test
    public void testPart2WithSampleData() {

        testDay.readLines(home + "/" + entryExtended, 1);

        String result = testDay.calculatePartTwo();

        assertEquals("36", result);

    }

    @Test
    public void testPart2WithTestData() {

        testDay.readLines(home + "/" + entryInput, 1);

        String result = testDay.calculatePartTwo();

        assertEquals("2678", result);

    }
}
