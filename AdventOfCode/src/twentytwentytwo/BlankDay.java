package twentytwentytwo;

import twentytwentyone.*;
import util.*;

import java.util.*;
import java.util.stream.*;

public class BlankDay extends DataLoader implements AocTest {
    Utilities utilities = new Utilities();

    @Override
    public String calculatePartOne() {
        List<String> startingList = column1.stream().collect(Collectors.toList());
        return null;
    }

    @Override
    public String calculatePartTwo() {
        return "";
    }
}
