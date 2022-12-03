package twentytwentytwo.test;

import org.junit.*;
import org.junit.Assert;
import org.junit.Test;
import twentytwentytwo.Day3;

public class Day3Test {

    String entrySample = "AdventOfCode/src/twentytwentytwo/data/day3sample";
    String entryInput = "AdventOfCode/src/twentytwentytwo/data/day3Input";

    private Day3 day3;
    private String home;

    @Before
    public void beforeEach() {
        day3 = new Day3();
        home = System.getProperty("user.dir");
    }

    @Test
    public void testPart1WithSampleData() {
        day3.readLines(home + "/" +entrySample, 1);

        String result = day3.calculatePartOne();

        Assert.assertEquals("157", result);

    }

    @Test
    public void testPart1WithTestData() {

        day3.readLines(home + "/" + entryInput, 1);

        String result = day3.calculatePartOne();

        Assert.assertEquals("7824", result);

    }

    @Test
    public void testPart2WithSampleData() {

        day3.readLines(home + "/" + entrySample, 1);

        String result = day3.calculatePartTwo();

        Assert.assertEquals("70", result);

    }

    @Test
    public void testPart2WithTestData() {

        day3.readLines(home + "/" + entryInput, 1);

        String result = day3.calculatePartTwo();

        Assert.assertEquals("2798", result);

    }
}
