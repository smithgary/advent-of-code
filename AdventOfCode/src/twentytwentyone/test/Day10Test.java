package twentytwentyone.test;

import org.junit.Assert;
import org.junit.Test;
import twentytwentyone.Day10;

import java.math.BigInteger;

public class Day10Test {

    String entrySample = "C:/Users/gary/Dev/advent-of-code/AdventOfCode/src/twentytwentyone/data/day10sample";
    String entryInput = "C:/Users/gary/Dev/advent-of-code/AdventOfCode/src/twentytwentyone/data/day10Input";

    @Test
    public void testPart1WithSampleData(){
        Day10 day10 = new Day10();
        day10.readLines(entrySample, 1);

        Integer result = day10.removeCorrupted();

        Assert.assertEquals(Integer.valueOf("26397"), result);

    }

    @Test
    public void testPart1WithTestData(){
        Day10 day10 = new Day10();
        day10.readLines(entryInput, 1);

        Integer result = day10.removeCorrupted();

        Assert.assertEquals(Integer.valueOf("318099"), result);

    }

    @Test
    public void testPart2WithSampleData(){
        Day10 day10 = new Day10();
        day10.readLines(entrySample, 1);

        BigInteger result = day10.completeMissing();

        Assert.assertEquals(BigInteger.valueOf(288957), result);

    }

    @Test
    public void testPart2WithTestData(){
        Day10 day10 = new Day10();
        day10.readLines(entryInput, 1);

        BigInteger result = day10.completeMissing();

        Assert.assertEquals(BigInteger.valueOf(2389738699L), result);

    }

}
