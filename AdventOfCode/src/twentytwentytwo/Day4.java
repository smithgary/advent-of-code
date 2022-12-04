package twentytwentytwo;

import twentytwentyone.*;
import util.*;

import java.util.*;
import java.util.stream.*;

public class Day4 extends DataLoader implements AocTest {
    Utilities utilities = new Utilities();

    @Override
    public String calculatePartOne() {
        List<String> startingList = column1.stream().collect(Collectors.toList());
        List<List<List<Integer>>> oneListContainsOther = startingList.stream()
                .map(s -> s.split(","))
                .map(a -> getIntegerRanges(a))
                .filter(a -> a.get(0).containsAll(a.get(1)) || a.get(1).containsAll(a.get(0)))
                .collect(Collectors.toList());

        return oneListContainsOther.isEmpty() ? "" : String.valueOf(oneListContainsOther.size());
    }

    @Override
    public String calculatePartTwo() {
        List<String> startingList = column1.stream().collect(Collectors.toList());
        List<List<List<Integer>>> oneListContainsOther = startingList.stream()
                .map(s -> s.split(","))
                .map(a -> getIntegerRanges(a))
                .filter(a -> a.get(0).stream().anyMatch(a.get(1)::contains) || a.get(1).stream().anyMatch(a.get(0)::contains))
                .collect(Collectors.toList());

        oneListContainsOther.stream().forEach(System.out::println);

        return oneListContainsOther.isEmpty() ? "" : String.valueOf(oneListContainsOther.size());

    }

    public List<List<Integer>> getIntegerRanges(String[] hyphenated) {
        List<List<Integer>> newList = new ArrayList<>();
        newList.add(getIntegerList(hyphenated[0]));
        newList.add(getIntegerList(hyphenated[1]));
        return newList;
    }

    public List<Integer> getIntegerList(String hypenatedRange) {
        String[] split = hypenatedRange.split("-");
        int first = Integer.parseInt(split[0]);
        int last = Integer.parseInt(split[1]);
        List<Integer> newList = new ArrayList<>();
        IntStream.range(first, last +1).forEach(i -> newList.add(i));
        return newList;
    }
}
