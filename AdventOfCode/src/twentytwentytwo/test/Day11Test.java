package twentytwentytwo.test;

import org.junit.*;
import twentytwentyone.*;
import static org.junit.Assert.*;
import org.junit.Test;
import twentytwentytwo.Day11;

public class Day11Test {

    String entrySample = "AdventOfCode/src/twentytwentytwo/data/day11sample";
    String entryInput = "AdventOfCode/src/twentytwentytwo/data/day11Input";

    private AocTest testDay;
    private String home;

    @Before
    public void beforeEach() {
        testDay = new Day11();
        home = System.getProperty("user.dir");
    }


    @Test
    public void testPart1WithSampleData() {
        testDay.readLines(home + "/" +entrySample, 1);

        String result = testDay.calculatePartOne();

        assertEquals("10605", result);

    }

    @Test
    public void testPart1WithTestData() {

        testDay.readLines(home + "/" + entryInput, 1);

        String result = testDay.calculatePartOne();

        assertEquals("66124", result);

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
