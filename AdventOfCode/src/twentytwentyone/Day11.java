package twentytwentyone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import util.Utilities;

public class Day11  extends DataLoader implements AocTest {
    Utilities utilities = new Utilities();

    @Override
    public String calculatePartOne() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        List<List<Integer>> a = startingList.stream()
                .map(str -> utilities.getIntegersFromStrings(str))
                .map(ints -> step(ints))
                .collect(Collectors.toList());


        /**
        startingList.stream()
                .map(str -> utilities.getIntegersFromStrings(str))
                .map(ints -> step(ints))
                .forEach(System.out::println);

        List<List<Integer>> transformed = startingList.stream()
                .map(str -> utilities.getIntegersFromStrings(str))
                .map(ints -> step(ints))
                .collect(Collectors.toList());

        List<List<Integer>> transformed2 = applyFlash(transformed);
        transformed2.forEach(System.out::println);

        List<List<Integer>> transformed3 = transformed2.stream()
                .map(ints -> step(ints))
                .collect(Collectors.toList());

        List<List<Integer>> transformed4 = applyFlash(transformed3);
        transformed4.forEach(System.out::println);
        */
        return null;
    }

    @Override
    public String calculatePartTwo() {
        return null;
    }

    public List<List<Integer>> performStep(List<List<Integer>> before) {
        // Flash count - can only flash once per step - set to zero initially
        List<List<Integer>> flashCount = new ArrayList<>();
        for(int i=0; i<before.size(); i++) {
            for (int j=0; j<before.get(0).size(); j++) {
                flashCount.get(i).set(j, 0);
            }
        }

        List<List<Integer>> updated = new ArrayList<>();
        // increment by 1
        for(int i=0; i<before.size(); i++) {
            for (int j=0; j<before.get(0).size(); j++) {
                Integer beforeValue = before.get(i).get(j);
                updated.get(i).set(j, beforeValue +1);
            }
        }

        // greater than 9 ? flashes
        for(int i=0; i<before.size(); i++) {
            for (int j = 0; j < before.get(0).size(); j++) {
                if (updated.get(i).get(j) > 9) {
                    updated.get(i).set(j, 0);
                    // mark as having flashed
                    flashCount.get(i).set(j, 1);
                }
            }
        }
        List<List<Integer>> updatedWithFlash = applyFlash(updated, flashCount);


        return updatedWithFlash;
    }
    public List<Integer> step(List<Integer> integers) {
        List<Integer> updated = new ArrayList<>();
        // increment by 1
        for(int i=0; i<integers.size(); i++) {
            updated.add(integers.get(i) + 1);
        }
        // greater than 9 ? flashes
        for(int i=0; i<updated.size(); i++) {
            if(updated.get(i) > 9) {
                updated.set(i, 0);
            }
        }
        return updated;
    }

    //
    public List<List<Integer>> applyFlash(List<List<Integer>> allRows, List<List<Integer>> flashCount) {
        List<List<Integer>> newList = List.copyOf(allRows);
        for(int i=0; i< allRows.size() -1; i++) {
            for (int j=0; j<allRows.get(0).size() -1; j++) {
                if (countSurroundingZeros(allRows, i, j) > 0) {
                    newList.get(i).set(j, 0);
                }
            }
        }
        return newList;
    }

    public Integer countSurroundingZeros(List<List<Integer>> allRows, Integer row, Integer col){

        Integer countSurroundingZeros = 0;

        Boolean isFirstRow = (row == 0);
        Boolean isLastRow  = (row == allRows.size() -1);
        Boolean isFirstColumn  = (col == 0);
        Boolean isLastColumn  = (col == allRows.get(0).size() -1);

        Integer topLeft =-1;
        Integer above =-1;
        Integer topRight =-1;
        Integer bottomLeft =-1;
        Integer below =-1;
        Integer bottomRight =-1;
        Integer left =-1;
        Integer right =-1;

        if (isFirstRow) {
            if (!isFirstColumn) {
                left = utilities.getValueLeft(allRows, row, col);
                bottomLeft = utilities.getValueDiagonallyBottomLeft(allRows, row, col);
            }
            if (!isLastColumn) {
                bottomRight = utilities.getValueDiagonallyBottomRight(allRows, row, col);
                right = utilities.getValueRight(allRows, row, col);
            }
            below = utilities.getValueBelow(allRows, row, col);
        }
        if (isLastRow) {
            if (!isFirstColumn) {
                left = utilities.getValueLeft(allRows, row, col);
                topLeft = utilities.getValueDiagonallyTopLeft(allRows, row, col);
            }
            if (!isLastColumn) {
                topRight = utilities.getValueDiagonallyTopRight(allRows, row, col);
                right = utilities.getValueRight(allRows, row, col);
            }
            above = utilities.getValueAbove(allRows, row, col);
        }

        //first row
        if (isFirstRow) {
            allRows.get(row).forEach(System.out::print);
            // first col
            if (isFirstColumn) {
                if (right == 0) countSurroundingZeros +=1;
                if (below == 0) countSurroundingZeros +=1;
                if (bottomRight == 0) countSurroundingZeros +=1;
            }
            // all other cols
            if (!isFirstColumn && !isLastColumn) {
                if (left == 0) countSurroundingZeros +=1;
                if (bottomLeft == 0) countSurroundingZeros +=1;
                if (below == 0) countSurroundingZeros +=1;
                if (bottomRight == 0) countSurroundingZeros +=1;
                if (right == 0) countSurroundingZeros +=1;
            }
            // last col
            if (isLastColumn) {
                if (left == 0) countSurroundingZeros +=1;
                if (below == 0) countSurroundingZeros +=1;
                if (bottomLeft == 0) countSurroundingZeros +=1;
            }

        }
        // all other rows
        if (!isFirstRow && !isLastRow) {
            // first col
            if (isFirstColumn) {
                if (right == 0) countSurroundingZeros +=1;
                if (above == 0) countSurroundingZeros +=1;
                if (topRight == 0) countSurroundingZeros +=1;
                if (below == 0) countSurroundingZeros +=1;
                if (bottomRight == 0) countSurroundingZeros +=1;
            }
            // all other cols
            if (!isFirstColumn && !isLastColumn) {
                if (above == 0) countSurroundingZeros +=1;
                if (topLeft == 0) countSurroundingZeros +=1;
                if (left == 0) countSurroundingZeros +=1;
                if (bottomLeft == 0) countSurroundingZeros +=1;
                if (below == 0) countSurroundingZeros +=1;
                if (bottomRight == 0) countSurroundingZeros +=1;
                if (right == 0) countSurroundingZeros +=1;
                if (topRight == 0) countSurroundingZeros +=1;
            }
            // last col
            if (isLastColumn) {
                if (above == 0) countSurroundingZeros +=1;
                if (topLeft == 0) countSurroundingZeros +=1;
                if (left == 0) countSurroundingZeros +=1;
                if (bottomLeft == 0) countSurroundingZeros +=1;
                if (below == 0) countSurroundingZeros +=1;
            }
        }
        //last row
        if (isLastRow) {
            // first col
            if (isFirstColumn) {
                if (right == 0) countSurroundingZeros +=1;
                if (above == 0) countSurroundingZeros +=1;
                if (topRight == 0) countSurroundingZeros +=1;
            }
            // all other cols
            if (!isFirstColumn && !isLastColumn) {
                if (left == 0) countSurroundingZeros +=1;
                if (topLeft == 0) countSurroundingZeros +=1;
                if (above == 0) countSurroundingZeros +=1;
                if (topRight == 0) countSurroundingZeros +=1;
                if (right == 0) countSurroundingZeros +=1;
            }
            // last col
            if (isLastColumn) {
                if (left == 0) countSurroundingZeros +=1;
                if (above == 0) countSurroundingZeros +=1;
                if (topLeft == 0) countSurroundingZeros +=1;
            }
        }
        return countSurroundingZeros;
    }

}
