//package twentytwentytwo.test;
//
//import org.junit.*;
//import twentytwentyone.*;
//import org.junit.Assert;
//import org.junit.Test;
//import twentytwentytwo.Day1;
//
//public class BlankTest {
//
//    String testSample = "AdventOfCode/src/twentytwentytwo/data/dayXTest";
//    String entrySample = "AdventOfCode/src/twentytwentytwo/data/dayXsample";
//    String entryInput = "AdventOfCode/src/twentytwentytwo/data/dayXInput";
//
//    private AocTest dayX;
//    private String home;
//
//    @Before
//    public void beforeEach() {
//        dayX = new Day1();
//        home = System.getProperty("user.dir");
//    }
//
//    @Test
//    public void testPart1WithScreenData() {
//        dayX.readLines(home + "/" + testSample, 1);
//
//        String result = dayX.calculatePartOne();
//
//        Assert.assertEquals(null, result);
//
//    }
//
//    @Test
//    public void testPart1WithSampleData() {
//        dayX.readLines(home + "/" +entrySample, 1);
//
//        String result = dayX.calculatePartOne();
//
//        Assert.assertEquals(null, result);
//
//    }
//
//    @Test
//    public void testPart1WithTestData() {
//
//        dayX.readLines(home + "/" + entryInput, 1);
//
//        String result = dayX.calculatePartOne();
//
//        Assert.assertEquals(null, result);
//
//    }
//
//    @Test
//    public void testPart2WithSampleData() {
//
//        dayX.readLines(home + "/" + entrySample, 1);
//
//        String result = dayX.calculatePartTwo();
//
//        //Assert.assertEquals(null, result);
//
//    }
//
//    @Test
//    public void testPart2WithTestData() {
//
//        dayX.readLines(home + "/" + entryInput, 1);
//
//        String result = dayX.calculatePartTwo();
//
//        Assert.assertEquals(null, result);
//
//    }
//}
