package twentytwentytwo.test;

import org.junit.*;
import twentytwentyone.*;
import org.junit.Assert;
import org.junit.Test;
import twentytwentytwo.Day4;

public class Day4Test {

    String entrySample = "AdventOfCode/src/twentytwentytwo/data/day4sample";
    String entryInput = "AdventOfCode/src/twentytwentytwo/data/day4Input";

    private AocTest day4;
    private String home;

    @Before
    public void beforeEach() {
        day4 = new Day4();
        home = System.getProperty("user.dir");
    }


    @Test
    public void testPart1WithSampleData() {
        day4.readLines(home + "/" +entrySample, 1);

        String result = day4.calculatePartOne();

        Assert.assertEquals("2", result);

    }

    @Test
    public void testPart1WithTestData() {

        day4.readLines(home + "/" + entryInput, 1);

        String result = day4.calculatePartOne();

        Assert.assertEquals("464", result);

    }

    @Test
    public void testPart2WithSampleData() {

        day4.readLines(home + "/" + entrySample, 1);

        String result = day4.calculatePartTwo();

        Assert.assertEquals("4", result);

    }

    @Test
    public void testPart2WithTestData() {

        day4.readLines(home + "/" + entryInput, 1);

        String result = day4.calculatePartTwo();

        Assert.assertEquals("770", result);

    }
}
