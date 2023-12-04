package twentytwentythree.test;

import org.junit.Before;
import org.junit.Test;
import twentytwentyone.AocTest;
import twentytwentythree.Day1;
import twentytwentytwo.Day6;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class Day1Test {

//    String entrySample = "AdventOfCode/src/twentytwentythree/data/day1sample";
    String entrySample = "AdventOfCode/src/twentytwentythree/data/day1Part1";
    String entryInput = "AdventOfCode/src/twentytwentythree/data/day1Input";

    private AocTest dayX;
    private String home;

    @Before
    public void beforeEach() {
        dayX = new Day1();
        home = System.getProperty("user.dir");
    }


    @Test
    public void testPart1WithSampleData() {
        dayX.readLines(home + "/" +entrySample, 1);

        String result = dayX.calculatePartOne();

        assertEquals("142", result);

    }

    @Test
    public void testPart1WithTestData() {

        dayX.readLines(home + "/" + entryInput, 1);

        String result = dayX.calculatePartOne();

        assertEquals("54630", result);

    }

    @Test
    public void testPart2WithSampleData() {
        String part2Sample = "AdventOfCode/src/twentytwentythree/data/day1pt2sample";

        dayX.readLines(home + "/" + part2Sample, 1);

        String result = dayX.calculatePartTwo();

        assertEquals("281", result);

    }

    @Test
    public void testPart2WithTestData() {

        dayX.readLines(home + "/" + entrySample, 1);

        String result = dayX.calculatePartTwo();

        assertEquals("55040", result);

    }

    @Test
    public void testGetInt_2() {
        String test = "atwo";
        Day1 d1 = new Day1();
        Integer returned = d1.getIntValueForString(test);
        assertEquals(Integer.valueOf(2), returned);
    }

    @Test
    public void testGetInt_8() {
        String test = "aneightzz";
        Day1 d1 = new Day1();
        Integer returned = d1.getIntValueForString(test);
        assertEquals(Integer.valueOf(8 + 48), returned);
    }

    @Test
    public void testGetInt_9() {
        String test = "anninezz";
        Day1 d1 = new Day1();
        Integer returned = d1.getIntValueForString(test);
        assertEquals(Integer.valueOf(9 + 48), returned);
    }

    @Test
    public void testGetInt_6() {
        String test = "hzdlftdtfqfdbxgsix";
        Day1 d1 = new Day1();
        Integer returned = d1.getIntValueForString(test);
        assertEquals(Integer.valueOf(6 + 48), returned);
    }


}
