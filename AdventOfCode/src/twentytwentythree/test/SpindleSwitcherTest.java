package twentytwentythree.test;

import org.junit.Before;
import org.junit.Test;
import twentytwentyone.AocTest;
import twentytwentythree.Day1;
import twentytwentythree.RecipeLine;
import twentytwentythree.SpindleSwitcher;
import twentytwentytwo.Day6;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
public class SpindleSwitcherTest {

    @Test
    public void testMain(){
        SpindleSwitcher spindleSwitcher = new SpindleSwitcher();
        spindleSwitcher.displayOptions();

    }

    @Test
    public void testUI(){
        SpindleSwitcher spindleSwitcher = new SpindleSwitcher();
        spindleSwitcher.displayOptions();
        System.out.println("a");
    }

    @Test
    public void testGetHeadWithMostCapacityFor_AsExpected(){
        SpindleSwitcher spindleSwitcher = new SpindleSwitcher();
        spindleSwitcher.setRecipeLines(getTestRecipeLines());
        List<Integer> inactiveSpindles = List.of(4,5);
        spindleSwitcher.setInactiveSpindles(inactiveSpindles);
        RecipeLine testRecipeLine = getTestRecipeLine();

        String headWithMostCapacity = spindleSwitcher.getHeadWithMostCapacityFor(testRecipeLine, getTestRecipeLines());
        // Head 1 => 2.42875308, 2.42875308 SUM = 4.85750616
        // Head 2 => 2.42622705, 2.41750977 SUM = 4.84373682 *** Smallest
        // Head 3 => 2.4329136, 2.39452785, 2.39452698 SUM = 7.22196843
        assertEquals("2", headWithMostCapacity);

    }

    public RecipeLine getTestRecipeLine(){
        RecipeLine recipeLine = new RecipeLine();
        recipeLine.setHead("4");
        recipeLine.setSection("41");
        recipeLine.setProduct("416999901");
        recipeLine.setPattern("B");
        recipeLine.setNumber("21");
        recipeLine.setUptake("2.42875308");
        recipeLine.setGroup("1/4 Gauge");
        recipeLine.setRecall("COMPBLAC");
        return recipeLine;
    }

    public List<RecipeLine> getTestRecipeLines(){
        List<RecipeLine> testLines = new ArrayList<>();
        // Six - all same group, pattern, section, head
        RecipeLine rl1 = new RecipeLine.RecipeLineBuilder()
                .withNumber("1").withHead("1").withSection("41").withProduct("416999901")
                .withPattern("B").withUptake("2.42875308").withGroup("1/4 Gauge").withRecall("COMPBLAC")
                .build();
        testLines.add(rl1);
        RecipeLine rl2 = new RecipeLine.RecipeLineBuilder()
                .withNumber("2").withHead("1").withSection("41").withProduct("416999901")
                .withPattern("B").withUptake("2.42875308").withGroup("1/4 Gauge").withRecall("COMPBLAC")
                .build();
        testLines.add(rl2);
        RecipeLine rl3 = new RecipeLine.RecipeLineBuilder()
                .withNumber("3").withHead("2").withSection("41").withProduct("416999901")
                .withPattern("B").withUptake("2.42622705").withGroup("1/4 Gauge").withRecall("COMPBLAC")
                .build();
        testLines.add(rl3);
        RecipeLine rl4 = new RecipeLine.RecipeLineBuilder()
                .withNumber("4").withHead("2").withSection("41").withProduct("416999901")
                .withPattern("B").withUptake("2.41750977").withGroup("1/4 Gauge").withRecall("COMPBLAC")
                .build();
        testLines.add(rl4);
        RecipeLine rl5 = new RecipeLine.RecipeLineBuilder()
                .withNumber("5").withHead("3").withSection("41").withProduct("416999901")
                .withPattern("B").withUptake("2.4329136").withGroup("1/4 Gauge").withRecall("COMPBLAC")
                .build();
        testLines.add(rl5);
        RecipeLine rl6 = new RecipeLine.RecipeLineBuilder()
                .withNumber("6").withHead("3").withSection("41").withProduct("416999901")
                .withPattern("B").withUptake("2.39452785").withGroup("1/4 Gauge").withRecall("COMPBLAC")
                .build();
        testLines.add(rl6);
        // One - different group, same pattern, section and head
        RecipeLine rl7 = new RecipeLine.RecipeLineBuilder()
                .withNumber("6").withHead("2").withSection("41").withProduct("416999901")
                .withPattern("B").withUptake("2.39452785").withGroup("1/8 Gauge").withRecall("COMPBLAC")
                .build();
        testLines.add(rl7);
        // One - different pattern, same group, section, head
        RecipeLine rl8 = new RecipeLine.RecipeLineBuilder()
                .withNumber("6").withHead("2").withSection("41").withProduct("416999901")
                .withPattern("C").withUptake("2.39452785").withGroup("1/4 Gauge").withRecall("COMPBLAC")
                .build();
        testLines.add(rl8);
        // One - different section, same group, pattern, head
        RecipeLine rl9 = new RecipeLine.RecipeLineBuilder()
                .withNumber("6").withHead("2").withSection("42").withProduct("416999901")
                .withPattern("B").withUptake("2.39452785").withGroup("1/4 Gauge").withRecall("COMPBLAC")
                .build();
        testLines.add(rl9);
        // One - different head, same pattern, section, group
        RecipeLine rl10 = new RecipeLine.RecipeLineBuilder()
                .withNumber("6").withHead("3").withSection("41").withProduct("416999901")
                .withPattern("B").withUptake("2.39452698").withGroup("1/4 Gauge").withRecall("COMPBLAC")
                .build();
        testLines.add(rl10);
        return testLines;
    }
}
