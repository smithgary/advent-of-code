package twentytwentyone;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9 {
    ArrayList<String> entries = new ArrayList<>();
    ArrayList<List<Integer>> integerEntries = new ArrayList<>();
    ArrayList<List<Integer>> bucketNumbersLR = new ArrayList<>();
    ArrayList<List<Integer>> bucketNumbers = new ArrayList<>();
    Map<Integer, Integer> bucketMap = new HashMap<>();

    int rowLength;
    int totalRows;
    public Day9(){
    }

//Answer to part 2
    public void findBasins() {
        rowLength = integerEntries.get(0).size();
        totalRows = entries.size();

        Integer currentBucket = 1;
        for (int rowNum = 0; rowNum < totalRows; rowNum++) {
            for (int colNum = 0; colNum < rowLength; colNum++) {

                if(isBasinEdge(rowNum, colNum)){
                    bucketNumbersLR.get(rowNum).set(colNum, 0);
                } else {
                    if(isValueToLeftBasinEdge(rowNum, colNum)){
                        currentBucket +=1;
                    }
                    bucketNumbersLR.get(rowNum).set(colNum, currentBucket);
                }
                //System.out.println("[" + rowNum + "],[" + colNum + "]"+ integerEntries.get(rowNum).get(colNum) + " - bucket: " + bucketNumbersLR.get(rowNum).get(colNum));
            }
        }

        //Create LRBlock items
        Integer currentLRBucket = 1;

        Map<Integer, LRBlock> blockMap = new HashMap<>();
        for (int rowNum = 0; rowNum < totalRows; rowNum++) {
            for (int colNum = 0; colNum < rowLength; colNum++) {
                Integer thisLRBucketNumber = bucketNumbersLR.get(rowNum).get(colNum);
                if(thisLRBucketNumber > 0) {
                    if(isValueToLeftBasinEdge(rowNum, colNum)) {
                        LRBlock lrBlock = new LRBlock(colNum, rowNum, integerEntries);
                        blockMap.put(currentLRBucket,lrBlock);
                    }
                    if(isValueToRightBasinEdge(rowNum, colNum)) {
                        blockMap.get(currentLRBucket).setRightMostColumn(colNum);
                        currentLRBucket ++;
                    }
                }
            }
        }

        printOutLRBlocks(blockMap);

        Integer currentFinalBucket = 1;
        for (int rowNum = 0; rowNum < totalRows; rowNum++) {
            for (int colNum = 0; colNum < rowLength; colNum++) {
                if(isBasinEdge(rowNum, colNum)){
                    bucketNumbers.get(rowNum).set(colNum, 0);

                } else {
                    BlockFinder blockFinder = new BlockFinder(rowNum, colNum);

                    Optional<LRBlock> maybeBlock = blockMap.entrySet().stream()
                            .filter(lrBlock -> (lrBlock.getValue().isInRow(blockFinder.getRowNum()) && lrBlock.getValue().containsColNumber(blockFinder.getColNum())))
                            .map(Map.Entry::getValue)
                            .findFirst();

                    if (maybeBlock.isPresent()) {
                        LRBlock block = maybeBlock.get();
                        if(block.getBasinNumber() != 0) {
                            bucketNumbers.get(rowNum).set(colNum, block.getBasinNumber());
                        } else {
                            if(block.hasBlockAbove()) {
                                Integer columnInRowAboveThisOneHavingABasin = block.getAboveBasinColumn();
                                Integer basinNumberFromAbove = bucketNumbers.get(rowNum - 1).get(columnInRowAboveThisOneHavingABasin);
                                bucketNumbers.get(rowNum).set(colNum, basinNumberFromAbove);

                            } else {
                                currentFinalBucket ++;
                                bucketNumbers.get(rowNum).set(colNum, currentFinalBucket);
                                block.setBasinNumber(currentFinalBucket);

                            }
                        }
                    } else {
                        System.out.println("No block found for " + rowNum + ", " + colNum);
                    }
                }
            }
        }
    }

    public void summariseBuckets() {
        //System.out.println(bucketNumbers.stream().flatMap(Collection::stream).collect(Collectors.groupingBy(bucket -> bucket)));
        final Map<Integer, List<Integer>> collectionOfBuckets = bucketNumbers
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(bucket -> bucket));

        collectionOfBuckets.entrySet()
                .stream()
                .forEach(buckets -> bucketMap.put(buckets.getKey(), buckets.getValue().size()));

        bucketMap.entrySet().stream()
                .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));

        final List<Map.Entry<Integer, Integer>> topThreeSummarised = bucketMap.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(0))
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().thenComparing(Map.Entry::getKey).reversed())
                .limit(3)
                .collect(Collectors.toList());

        topThreeSummarised.stream().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
        System.out.println(topThreeSummarised.stream().map(entry -> entry.getValue()).reduce(1, (a, b) -> a * b));

    }

    public void printOutBucketNumbers(){
        for (int rowNum = 0; rowNum < totalRows; rowNum++) {
            System.out.println();
            for (int colNum = 0; colNum < rowLength; colNum++) {
                System.out.print(bucketNumbers.get(rowNum).get(colNum) + " ");
            }
        }
    }
    public void printOutBucketNumbersLR(){
        for (int rowNum = 0; rowNum < totalRows; rowNum++) {
            System.out.println();
            for (int colNum = 0; colNum < rowLength; colNum++) {
                System.out.print(bucketNumbersLR.get(rowNum).get(colNum) + " ");
            }
        }
        System.out.println();
    }
    public void printOutLRBlocks(Map<Integer, LRBlock> blockMap){
        blockMap.entrySet().stream().forEach(block -> {
            System.out.println("Row: " + block.getValue().row + " Start: " + block.getValue().leftMostColumn + " End : " + block.getValue().rightMostColumn);
        });
    }

    private Boolean isBasinEdge(Integer currentRow, Integer currentCol) {
        return (integerEntries.get(currentRow).get(currentCol)) == 9;
    }
    private Boolean isValueAboveBasinEdge(Integer currentRow, Integer currentCol) {
        return (integerEntries.get(currentRow - 1).get(currentCol)) == 9;
    }
    private Boolean isValueToRightBasinEdge(Integer currentRow, Integer currentCol) {
        if(isLastCol(currentRow, currentCol)) {
            return true;
        }
        return (integerEntries.get(currentRow).get(currentCol + 1)) == 9;
    }
    private Boolean isValueToLeftBasinEdge(Integer currentRow, Integer currentCol) {
        if(isFirstCol(currentRow, currentCol)) {
            return true;
        }
        return (integerEntries.get(currentRow).get(currentCol - 1)) == 9;
    }

    private Boolean isFirstCol(Integer currentRow, Integer currentCol) {
        return currentCol == 0;
    }
    private Boolean isLastCol(Integer currentRow, Integer currentCol) {
        return currentCol == rowLength - 1;
    }



    //Answer to part 1
    public void findLowPoints() {
        rowLength = integerEntries.get(0).size();
        totalRows = entries.size();
        //each one -> 4 comparisons
        ArrayList<Integer> lowPointValues = new ArrayList<>();
        ArrayList<Integer> riskLevels = new ArrayList<>();

        for (int rowNum = 0; rowNum < totalRows; rowNum++) {
            for (int colNum = 0; colNum < rowLength; colNum++) {
                if(isCurrentPositionLessThanSurrounding(rowNum, colNum)) {
                    System.out.println("Lowest " + rowNum + " " + colNum + " " + integerEntries.get(rowNum).get(colNum));
                    lowPointValues.add(integerEntries.get(rowNum).get(colNum));
                    riskLevels.add(integerEntries.get(rowNum).get(colNum) + 1);
                }
            }
        }
        System.out.println( "Total Risk:" + riskLevels.stream().reduce(0, Integer::sum));
    }

    private Boolean isCurrentPositionGreaterThanSurrounding(Integer currentRow, Integer currentCol){
        Integer thisValue = integerEntries.get(currentRow).get(currentCol);
        ArrayList<Integer> surroundingValues = getSurroundingValues(currentRow, currentCol);
        return thisValue > surroundingValues.stream().reduce(0, Math::max);
    }

    private Boolean isCurrentPositionLessThanSurrounding(Integer currentRow, Integer currentCol) {
        Integer thisValue = integerEntries.get(currentRow).get(currentCol);
        ArrayList<Integer> surroundingValues = getSurroundingValues(currentRow, currentCol);
        return thisValue < getMin(surroundingValues);
    }

    private Integer getMin(ArrayList<Integer> arrayList) {
        Collections.sort(arrayList);
        return arrayList.get(0);
    }

    private ArrayList<Integer> getSurroundingValues(Integer currentRow, Integer currentCol) {
        Boolean hasValueToRight = currentCol < (rowLength - 1);
        Boolean hasValueToLeft = currentCol > 0;
        Boolean hasValueAbove = currentRow > 0;
        Boolean hasValueBelow = currentRow < (totalRows -1);

        final ArrayList<Integer> right = getRight(currentRow, currentCol);
        final ArrayList<Integer> left = getLeft(currentRow, currentCol);
        final ArrayList<Integer> above = getAbove(currentRow, currentCol);
        final ArrayList<Integer> below = getBelow(currentRow, currentCol);

        Integer rightValue = hasValueToRight ? integerEntries.get(right.get(0)).get(right.get(1)) : null;
        Integer leftValue = hasValueToLeft ? integerEntries.get(left.get(0)).get(left.get(1)) : null;
        Integer aboveValue = hasValueAbove ? integerEntries.get(above.get(0)).get(above.get(1)) : null;
        Integer belowValue = hasValueBelow ? integerEntries.get(below.get(0)).get(below.get(1)) : null;

        final ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(rightValue, leftValue, aboveValue, belowValue));
        List<Integer> cleanIntegers = integers.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return (ArrayList<Integer>) cleanIntegers;

    }

    //right (if colNum < rowLength - 1)
    private ArrayList<Integer> getRight(Integer currentRow, Integer currentCol) {
        ArrayList<Integer> rightPosition = new ArrayList<>();
        if(currentCol < rowLength - 1) {
            rightPosition.add(currentRow);
            rightPosition.add(currentCol + 1);
        } else {
            // Can't return self.. has to be invalid
            rightPosition.add(currentRow);
            rightPosition.add(currentCol);
        }
        return rightPosition;
    }

    //left (if colNum > 0)
    private ArrayList<Integer> getLeft(Integer currentRow, Integer currentCol) {
        ArrayList<Integer> leftPosition = new ArrayList<>();
        if(currentCol > 0) {
            leftPosition.add(currentRow);
            leftPosition.add(currentCol - 1);
        } else {
            leftPosition.add(currentRow);
            leftPosition.add(currentCol);
        }
        return leftPosition;
    }

    //above (if rowNum > 0)
    private ArrayList<Integer> getAbove(Integer currentRow, Integer currentCol) {
        ArrayList<Integer> abovePosition = new ArrayList<>();
        if(currentRow > 0) {
            abovePosition.add(currentRow - 1);
            abovePosition.add(currentCol);
        } else {
            abovePosition.add(currentRow);
            abovePosition.add(currentCol);
        }
        return abovePosition;
    }

    //below (if rowNum < totalRows - 1)
    private ArrayList<Integer> getBelow(Integer currentRow, Integer currentCol) {
        ArrayList<Integer> belowPosition = new ArrayList<>();
        if(currentRow < totalRows -1) {
            belowPosition.add(currentRow + 1);
            belowPosition.add(currentCol);
        } else {
            belowPosition.add(currentRow);
            belowPosition.add(currentCol);
        }
        return belowPosition;
    }

    // Use this
    public void mapLinesToInts() {
        entries.stream().forEach(line -> {
            final char[] chars = line.toCharArray();
            List<Integer> intsForLine = new ArrayList<>();
            List<Integer> intsForBucket = new ArrayList<>();
            List<Integer> intsForBucketLR = new ArrayList<>();

            for (int i = 0; i < chars.length; i++) {
                intsForLine.add(Integer.parseInt(chars[i] + ""));
                intsForBucket.add(0);
                intsForBucketLR.add(0);
            }
            integerEntries.add(intsForLine);
            bucketNumbersLR.add(intsForBucketLR);
            bucketNumbers.add(intsForBucket);
        });
    }

    // Use this one
    public void printOutLines() {
        entries.forEach(System.out::println);
    }
    public void printOutIntegers() {
        integerEntries.forEach(System.out::println);
    }


    //Use this
    public void readLines(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> {
                entries.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class LRBlock {
    Integer row;
    Integer leftMostColumn;
    Integer rightMostColumn;
    ArrayList<List<Integer>> integerEntries;
    Integer basinNumber = 0;
    Integer aboveBasinColumn = 0;
    List<Integer> basinsAbove;

    public LRBlock(Integer leftMostColumn, Integer row, ArrayList<List<Integer>> integerEntries){
        this.leftMostColumn = leftMostColumn;
        this.row = row;
        this.integerEntries = integerEntries;
    }
    public void setRightMostColumn(Integer rightMostColumn) {
        this.rightMostColumn = rightMostColumn;
    }

    public Integer getBasinNumber() {
        return this.basinNumber;
    }

    public Integer getAboveBasinColumn() {
        return this.aboveBasinColumn;
    }
    public void setBasinNumber(Integer basinNumber) {
        this.basinNumber = basinNumber;
    }

    public Boolean isInRow(Integer rowNumber) {
        return rowNumber.equals(row);
    }
    public Boolean containsColNumber(Integer columnNumber) {
        return columnNumber >= leftMostColumn &&
                columnNumber <= rightMostColumn;
    }
    public void setAboveBasin(Integer basinNumber) {
        this.aboveBasinColumn = basinNumber;
    }
    public Boolean isBlockAtRowEnd() {
        return rightMostColumn == integerEntries.get(0).size();
    }

    public Boolean hasBlockAbove(){
        if (row == 0) {
            return false;
        }
        Boolean hasAnyBlockGotANonBasinEdge = false;
        for (int i = leftMostColumn; i < rightMostColumn + 1; i++) {
            Integer aboveI = integerEntries.get(row - 1).get(i);
            if(aboveI < 9) {
                hasAnyBlockGotANonBasinEdge = true;
                setAboveBasin(i);
                break;
            }
        }
        return hasAnyBlockGotANonBasinEdge;
    }

    public void populateBasinsAbove(Map<Integer, LRBlock> blockMap) {
        if (this.hasBlockAbove()) {
            for (int i =leftMostColumn; i < rightMostColumn + 1; i++) {
                BlockFinder blockFinder = new BlockFinder(row - 1, i);
                Optional<LRBlock> maybeBlock = blockMap.entrySet().stream()
                        .filter(lrBlock -> (lrBlock.getValue().isInRow(blockFinder.getRowNum()) && lrBlock.getValue().containsColNumber(blockFinder.getColNum())))
                        .map(Map.Entry::getValue)
                        .findFirst();
                if(maybeBlock.isPresent()) {
                    LRBlock aboveBlock = maybeBlock.get();
                    basinsAbove.add(aboveBlock.getBasinNumber());
                } else {
                    // directly above must be a basin edge, = 9
                }
            }
        }
    }

    public List<Integer> getBasinsAbove() {
        return this.basinsAbove;
    }
}

/**
 * Class to hold immutable row and column values from for loops for use
 * in a stream that relies on immutable values.
 */
class BlockFinder{
    private final Integer rowNum;
    private final Integer colNum;

    public BlockFinder(Integer rowNum, Integer colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public Integer getColNum() {
        return colNum;
    }
}