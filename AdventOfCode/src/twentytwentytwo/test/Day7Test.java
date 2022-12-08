package twentytwentytwo.test;

import org.junit.*;
import twentytwentyone.*;
import twentytwentytwo.Day7;

import static org.junit.Assert.*;

public class Day7Test {

    String entrySample = "AdventOfCode/src/twentytwentytwo/data/day7sample";
    String entryInput = "AdventOfCode/src/twentytwentytwo/data/day7Input";

    private AocTest dayX;
    private String home;

    @Before
    public void beforeEach() {
        dayX = new Day7();
        home = System.getProperty("user.dir");
    }


    @Test
    public void testPart1WithSampleData() {
        dayX.readLines(home + "/" +entrySample, 1);

        String result = dayX.calculatePartOne();

        assertEquals("95437", result);

    }

    @Test
    public void testPart1WithTestData() {

        dayX.readLines(home + "/" + entryInput, 1);

        String result = dayX.calculatePartOne();

        assertEquals("1118405", result);

    }

    @Test
    public void testPart2WithSampleData() {

        dayX.readLines(home + "/" + entrySample, 1);

        String result = dayX.calculatePartTwo();

        assertEquals("24933642", result);

    }

    @Test
    public void testPart2WithTestData() {

        dayX.readLines(home + "/" + entryInput, 1);

        String result = dayX.calculatePartTwo();

        assertEquals("12545514", result);

    }
}
