package util;

import java.util.*;
import java.util.stream.*;

public class Utilities {

    public List<Integer> getListOfIntegersFromListOfString(List<String> input) {
        List<Integer> intList = new ArrayList<>();

        for (int i=0; i < input.size(); i++) {
            String maybeNumber = input.get(i);
            //Assume its a number, might need to try/catch for NumberFormatException
            intList.add(Integer.parseInt(maybeNumber));
        }
        return intList;
    }

    public List<String> splitInTwo(String s) {
        List<String> newList = new ArrayList<>();
        newList.add(s.substring(0, s.length()/2));
        newList.add(s.substring(s.length()/2));
        return newList;
    }

    public List<List<String>> getGroupsOfThree(ArrayList<String> listOfStrings) {

        Map<Integer, List<String>> groups = listOfStrings.stream()
                .collect(Collectors.groupingBy(s -> listOfStrings.indexOf(s)/3));

        List<List<String>> newGroupings = new ArrayList<>(groups.values());

        return newGroupings;

    }


    /**
    Pass in a String of digits and get an List<Integer>
     eg. "5483143223" -> [5, 4, 8, 3, 1, 4, 3, 2, 2, 3]
     */
    public List<Integer> getIntegersFromStrings(String s) {
        List<Integer> intList = new ArrayList<>();

        for (int i=0; i < s.length(); i++) {
            String letter = s.substring(i, i+1);
            intList.add(Integer.parseInt(letter));
        }
        return intList;
    }

    public Integer getValueDiagonallyTopLeft(List<List<Integer>> allRows, Integer row, Integer col) {
        return allRows.get(row -1).get(col -1);
    }
    public Integer getValueDiagonallyTopRight(List<List<Integer>> allRows, Integer row, Integer col) {
        return allRows.get(row -1).get(col -1);
    }
    public Integer getValueDiagonallyBottomLeft(List<List<Integer>> allRows, Integer row, Integer col) {
        return allRows.get(row +1).get(col -1);
    }
    public Integer getValueDiagonallyBottomRight(List<List<Integer>> allRows, Integer row, Integer col) {
        return allRows.get(row +1).get(col +1);
    }
    public Integer getValueLeft(List<List<Integer>> allRows, Integer row, Integer col) {
        return allRows.get(row).get(col -1);
    }
    public Integer getValueRight(List<List<Integer>> allRows, Integer row, Integer col) {
        return allRows.get(row).get(col +1);
    }
    public Integer getValueAbove(List<List<Integer>> allRows, Integer row, Integer col) {
        return allRows.get(row -1).get(col);
    }
    public Integer getValueBelow(List<List<Integer>> allRows, Integer row, Integer col) {
        return allRows.get(row +1).get(col);
    }

}
