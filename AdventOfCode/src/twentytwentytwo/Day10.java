package twentytwentytwo;

import twentytwentyone.AocTest;
import twentytwentyone.DataLoader;
import util.Utilities;

import java.util.List;
import java.util.stream.Collectors;

public class Day10 extends DataLoader implements AocTest {
    Utilities utilities = new Utilities();

    @Override
    public String calculatePartOne() {
        List<String> startingList = column1.stream().collect(Collectors.toList());
        return "";
    }

    @Override
    public String calculatePartTwo() {
        return "";
    }
}
