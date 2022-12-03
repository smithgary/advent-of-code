package twentytwentytwo;

import twentytwentyone.*;
import java.util.*;
import java.util.stream.*;

public class Day3 extends DataLoader implements AocTest {

    @Override
    public String calculatePartOne() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        Optional<Integer> prioritySum = startingList.stream()
                .map(s -> splitInTwo(s))
                .map(list -> getCommonCharacter(list, 2))
                .map(c -> getPriority(c))
                .reduce(Integer::sum);

        return prioritySum.isPresent() ? prioritySum.get().toString() : "";
    }

    @Override
    public String calculatePartTwo() {
        List<List<String>> groupsOfThree = getGroupsOfThree();

        Optional<Integer> prioritySum = groupsOfThree.stream()
                .map(list -> getCommonCharacter(list, 3))
                .map(c -> getPriority(c))
                .reduce(Integer::sum);

        return prioritySum.isPresent() ? prioritySum.get().toString() : "";

    }
    public List<String> splitInTwo(String s) {
        List<String> newList = new ArrayList<>();
        newList.add(s.substring(0, s.length()/2));
        newList.add(s.substring(s.length()/2));
        return newList;
    }

    public Character getCommonCharacter(List<String> lists, Integer howMany) {
        char[] left = lists.get(0).toCharArray();
        char[] right = lists.get(1).toCharArray();
        char[] middle = lists.get(1).toCharArray();
        if (howMany > 2) {
            middle = lists.get(2).toCharArray();
        }
        Set<Character> leftList = new TreeSet<>();
        Set<Character> rightList = new TreeSet<>();
        Set<Character> middleList = new TreeSet<>();

        for (char c: left) {
            leftList.add(c);
        }
        for (char c: right) {
            rightList.add(c);
        }
        leftList.retainAll(rightList);

        if (howMany > 2) {
            for (char c: middle) {
                middleList.add(c);
            }
            leftList.retainAll(middleList);
        }

        return leftList.isEmpty()? ':': leftList.stream().findFirst().get();
    }

    public Integer getPriority(Character c) {
        Integer priorityValue;
        Boolean isLowerCase = c > 90;
        if (isLowerCase) {
            // ASCII a=97 z=122
            priorityValue = c - 96;
        } else {
            // ASCII A = 65 Z=90
            priorityValue = c - 64 + 26;
        }

        return priorityValue;
    }


    public List<List<String>> getGroupsOfThree() {

        Map<Integer, List<String>> groups = column1.stream()
                .collect(Collectors.groupingBy(s -> column1.indexOf(s)/3));

        List<List<String>> newGroupings = new ArrayList<>(groups.values());

        return newGroupings;

    }
}
