package util;

import java.util.ArrayList;
import java.util.List;

public class Utilities {

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
