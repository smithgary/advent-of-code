package twentytwentytwo.test;

import org.junit.*;
import twentytwentyone.*;
import static org.junit.Assert.*;
import org.junit.Test;
import twentytwentytwo.Day8;

public class Day8Test {

    String entrySample = "AdventOfCode/src/twentytwentytwo/data/day8sample";
    String entryInput = "AdventOfCode/src/twentytwentytwo/data/day8Input";

    private AocTest testDay;
    private String home;

    @Before
    public void beforeEach() {
        testDay = new Day8();
        home = System.getProperty("user.dir");
    }


    @Test
    public void testPart1WithSampleData() {
        testDay.readLines(home + "/" +entrySample, 1);

        String result = testDay.calculatePartOne();

        assertEquals("", result);

    }

    @Test
    public void testPart1WithTestData() {

        testDay.readLines(home + "/" + entryInput, 1);

        String result = testDay.calculatePartOne();

        assertEquals("", result);

    }

    @Test
    public void testPart2WithSampleData() {

        testDay.readLines(home + "/" + entrySample, 1);

        String result = testDay.calculatePartTwo();

        assertEquals("", result);

    }

    @Test
    public void testPart2WithTestData() {

        testDay.readLines(home + "/" + entryInput, 1);

        String result = testDay.calculatePartTwo();

        assertEquals("", result);

    }
}
