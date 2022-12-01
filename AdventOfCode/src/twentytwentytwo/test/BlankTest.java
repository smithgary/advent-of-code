package twentytwentytwo.test;

import org.junit.*;
import twentytwentyone.*;
import org.junit.Assert;
import org.junit.Test;
import twentytwentytwo.Day1;

public class BlankTest {

    String testSample = "AdventOfCode/src/twentytwentytwo/data/day1Test";
    String entrySample = "AdventOfCode/src/twentytwentytwo/data/day1sample";
    String entryInput = "AdventOfCode/src/twentytwentytwo/data/day1Input";

    private Day1 day1;
    private String home;

    @Before
    public void beforeEach() {
        day1 = new Day1();
        home = System.getProperty("user.dir");
    }

    @Test
    public void testPart1WithScreenData() {
        day1.readLines(home + "/" + testSample, 1);

        String result = day1.calculatePartOne();

        Assert.assertEquals(null, result);

    }

    @Test
    public void testPart1WithSampleData() {
        day1.readLines(home + "/" +entrySample, 1);

        String result = day1.calculatePartOne();

        Assert.assertEquals(null, result);

    }

    @Test
    public void testPart1WithTestData() {

        day1.readLines(home + "/" + entryInput, 1);

        String result = day1.calculatePartOne();

        Assert.assertEquals(null, result);

    }

    @Test
    public void testPart2WithSampleData() {

        day1.readLines(home + "/" + entrySample, 1);

        String result = day1.calculatePartTwo();

        //Assert.assertEquals(null, result);

    }

    @Test
    public void testPart2WithTestData() {
        Day11 day1 = new Day11();
        day1.readLines(home + "/" + entryInput, 1);

        String result = day1.calculatePartTwo();

        Assert.assertEquals(null, result);

    }
}
