package twentytwentytwo.test;

import org.junit.*;
import twentytwentytwo.*;

public class Day2Test {

    String entrySample = "AdventOfCode/src/twentytwentytwo/data/day2sample";
    String entryInput = "AdventOfCode/src/twentytwentytwo/data/day2Input";

    private Day2 day2;
    private String home;

    @Before
    public void beforeEach() {
        day2 = new Day2();
        home = System.getProperty("user.dir");
    }

    @Test
    public void testPart1WithSampleData() {
        day2.readLines(home + "/" + entrySample, 1);

        String result = day2.calculatePartOne();

        Assert.assertEquals("15", result);

    }

    @Test
    public void testPart1WithInputData() {
        day2.readLines(home + "/" + entryInput, 1);

        String result = day2.calculatePartOne();

        Assert.assertEquals("12679", result);

    }


    @Test
    public void testPart2WithSampleData() {

        day2.readLines(home + "/" + entrySample, 1);

        String result = day2.calculatePartTwo();

        Assert.assertEquals( "12", result);

    }

    @Test
    public void testPart2WithTestData() {

        day2.readLines(home + "/" + entryInput, 1);

        String result = day2.calculatePartTwo();

        Assert.assertEquals("14470", result);

    }
}
