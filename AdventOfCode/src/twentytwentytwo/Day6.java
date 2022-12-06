package twentytwentytwo;

import twentytwentyone.*;
import java.util.*;
import java.util.stream.*;

public class Day6 extends DataLoader implements AocTest {

    @Override
    public String calculatePartOne() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        return getUniqueOccurrence(startingList.get(0), 4).toString();
    }

    @Override
    public String calculatePartTwo() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        return getUniqueOccurrence(startingList.get(0), 14).toString();
    }

    public Integer getUniqueOccurrence(String s, Integer required) {
        Integer firstUniqueCharacterOfRequiredSize = 0;
        char[] input = s.toCharArray();
        for(int i=required -1; i<input.length; i++){
            Set<Character> uniqueOrNot = new HashSet<>();
            for(int j=0; j<required; j++) {
                uniqueOrNot.add(input[i-j]);
            }
            if (uniqueOrNot.size() == required) {
                firstUniqueCharacterOfRequiredSize = i + 1;
                return firstUniqueCharacterOfRequiredSize;
            }
        }
        return firstUniqueCharacterOfRequiredSize;
    }
}
