package twentytwentytwo.test;

import org.junit.*;
import twentytwentyone.*;
import org.junit.Test;
import twentytwentytwo.Day6;

import static org.junit.Assert.*;

public class Day6Test {

    String entrySample = "AdventOfCode/src/twentytwentytwo/data/day6sample";
    String entryInput = "AdventOfCode/src/twentytwentytwo/data/day6Input";

    private AocTest dayX;
    private String home;

    @Before
    public void beforeEach() {
        dayX = new Day6();
        home = System.getProperty("user.dir");
    }


    @Test
    public void testPart1WithSampleData() {
        dayX.readLines(home + "/" +entrySample, 1);

        String result = dayX.calculatePartOne();

        assertEquals("7", result);

    }

    @Test
    public void testPart1WithTestData() {

        dayX.readLines(home + "/" + entryInput, 1);

        String result = dayX.calculatePartOne();

        assertEquals("1816", result);

    }

    @Test
    public void testPart2WithSampleData() {

        dayX.readLines(home + "/" + entrySample, 1);

        String result = dayX.calculatePartTwo();

        assertEquals("19", result);

    }

    @Test
    public void testPart2WithTestData() {

        dayX.readLines(home + "/" + entryInput, 1);

        String result = dayX.calculatePartTwo();

        assertEquals("2625", result);

    }
}
