package twentytwentyone.test;

import org.junit.Assert;
import org.junit.Test;
import twentytwentyone.Day11;


public class Day11Test {

    String entrySample = "C:/Users/gary/Dev/advent-of-code/AdventOfCode/src/twentytwentyone/data/day11sample";
    String entryInput = "C:/Users/gary/Dev/advent-of-code/AdventOfCode/src/twentytwentyone/data/day11Input";

    @Test
    public void testPart1WithSampleData(){
        Day11 day11 = new Day11();
        day11.readLines(entrySample, 1);

        String result = day11.calculatePartOne();

        Assert.assertEquals(null, result);

    }

    @Test
    public void testPart1WithTestData(){
        Day11 day11 = new Day11();
        day11.readLines(entryInput, 1);

        String result = day11.calculatePartOne();

        Assert.assertEquals(null, result);

    }

    @Test
    public void testPart2WithSampleData(){
        Day11 day11 = new Day11();
        day11.readLines(entrySample, 1);

        String result = day11.calculatePartTwo();

        Assert.assertEquals(null, result);

    }

    @Test
    public void testPart2WithTestData(){
        Day11 day11 = new Day11();
        day11.readLines(entryInput, 1);

        String result = day11.calculatePartTwo();

        Assert.assertEquals(null, result);

    }

}
