//package twentytwentytwo.test;
//
//import org.junit.*;
//import twentytwentyone.*;
//import static org.junit.Assert.*;
//import org.junit.Test;
//import twentytwentytwo.DayX;
//
//public class BlankTest {
//
//    String entrySample = "AdventOfCode/src/twentytwentytwo/data/dayXsample";
//    String entryInput = "AdventOfCode/src/twentytwentytwo/data/dayXInput";
//
//    private AocTest dayX;
//    private String home;
//
//    @Before
//    public void beforeEach() {
//        testDay = new DayX();
//        home = System.getProperty("user.dir");
//    }
//
//
//    @Test
//    public void testPart1WithSampleData() {
//        testDay.readLines(home + "/" +entrySample, 1);
//
//        String result = testDay.calculatePartOne();
//
//        assertEquals("", result);
//
//    }
//
//    @Test
//    public void testPart1WithTestData() {
//
//        testDay.readLines(home + "/" + entryInput, 1);
//
//        String result = testDay.calculatePartOne();
//
//        assertEquals("", result);
//
//    }
//
//    @Test
//    public void testPart2WithSampleData() {
//
//        testDay.readLines(home + "/" + entrySample, 1);
//
//        String result = testDay.calculatePartTwo();
//
//        assertEquals("", result);
//
//    }
//
//    @Test
//    public void testPart2WithTestData() {
//
//        testDay.readLines(home + "/" + entryInput, 1);
//
//        String result = testDay.calculatePartTwo();
//
//        assertEquals("", result);
//
//    }
//}
