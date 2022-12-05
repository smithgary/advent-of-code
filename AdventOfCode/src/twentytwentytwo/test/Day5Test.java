package twentytwentytwo.test;

import org.junit.*;
import twentytwentyone.*;
import org.junit.Assert;
import org.junit.Test;
import twentytwentytwo.Day5;

public class Day5Test {

    String entrySample = "AdventOfCode/src/twentytwentytwo/data/day5sample";
    String entryInput = "AdventOfCode/src/twentytwentytwo/data/day5Input";

    private AocTest dayX;
    private String home;

    @Before
    public void beforeEach() {
        dayX = new Day5();
        home = System.getProperty("user.dir");
    }


    @Test
    public void testPart1WithSampleData() {
        dayX.readLines(home + "/" +entrySample, 1);

        String result = dayX.calculatePartOne();

        Assert.assertEquals("CMZ", result);

    }

    @Test
    public void testPart1WithTestData() {

        dayX.readLines(home + "/" + entryInput, 1);

        String result = dayX.calculatePartOne();

        Assert.assertEquals("FZCMJCRHZ", result);

    }

    @Test
    public void testPart2WithSampleData() {

        dayX.readLines(home + "/" + entrySample, 1);

        String result = dayX.calculatePartTwo();

        Assert.assertEquals("MCD", result);

    }

    @Test
    public void testPart2WithTestData() {

        dayX.readLines(home + "/" + entryInput, 1);

        String result = dayX.calculatePartTwo();

        Assert.assertEquals("JSDHQMZGF", result);

    }
}
