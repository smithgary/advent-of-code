package twentytwentytwo;

import twentytwentyone.*;
import util.*;

import java.util.*;
import java.util.stream.*;

public class Day6 extends DataLoader implements AocTest {
    Utilities utilities = new Utilities();

    @Override
    public String calculatePartOne() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        for(int i=0; i<startingList.size(); i++) {
            System.out.println(getUniqueOccurrence(startingList.get(i), 4));
        }
        String firstOccurrence = getUniqueOccurrence(startingList.get(0), 4).toString();
        return firstOccurrence;
    }

    @Override
    public String calculatePartTwo() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        for(int i=0; i<startingList.size(); i++) {
            System.out.println(getUniqueOccurrence(startingList.get(i), 14));
        }
        String firstOccurrence = getUniqueOccurrence(startingList.get(0), 14).toString();
        return firstOccurrence;
    }

    public Integer getUniqueOccurrence(String s, Integer required) {
        Integer firstFourUnique = 0;
        char[] input = s.toCharArray();
        for(int i=required -1; i<input.length; i++){
            Set<Character> uniqueOrNot = new HashSet<>();
            for(int j=0; j<required; j++) {
                uniqueOrNot.add(input[i-j]);
            }
            if (uniqueOrNot.size() == required) {
                firstFourUnique = i + 1;
                return firstFourUnique;
            }
        }
        return firstFourUnique;
    }
}
