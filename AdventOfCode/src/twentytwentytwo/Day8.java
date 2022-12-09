package twentytwentytwo;

import twentytwentyone.*;
import util.*;

import java.util.*;
import java.util.stream.*;

public class Day8 extends DataLoader implements AocTest {
    Utilities utilities = new Utilities();

    @Override
    public String calculatePartOne() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        List<List<Integer>> treeHeights = startingList.stream().map(s -> utilities.getIntegersFromStrings(s)).collect(Collectors.toList());
        treeHeights.stream().forEach(System.out::println);

        List<List<Boolean>> visibleTrees = getVisibleTrees(treeHeights);
        long totalVisibleTrees = visibleTrees.stream().flatMap(List::stream).filter(b -> b).count();
        return String.valueOf(totalVisibleTrees);
    }

    @Override
    public String calculatePartTwo() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        List<List<Integer>> treeHeights = startingList.stream().map(s -> utilities.getIntegersFromStrings(s)).collect(Collectors.toList());
        treeHeights.stream().forEach(System.out::println);

        List<List<Integer>> productOfVisibleTrees = getProductOfVisibleTrees(treeHeights);
        Integer bestView = productOfVisibleTrees.stream().flatMap(List::stream).reduce(Integer::max).get();

        return bestView.toString();

    }
    public List<List<Boolean>> getVisibleTrees (List<List<Integer>> treeHeights) {
        List<List<Boolean>> visibleTrees = new ArrayList<>();
        for(int i = 0 ; i< treeHeights.size(); i++) {
            List<Boolean> b = new ArrayList<>();
            for(int j=0; j< treeHeights.get(0).size(); j++) {
                b.add(false);
            }
            visibleTrees.add(b);
        }

        for (int row=0; row< treeHeights.get(0).size(); row ++) {
            for (int col = 0; col < treeHeights.size(); col ++) {
                Integer thisTree = treeHeights.get(row).get(col);
                //is perimeterTree ? set true
                Boolean isPerimeterTree = false;
                if (col == 0 || col == treeHeights.size() -1 || row ==0 || row == treeHeights.get(0).size() -1) {
                    visibleTrees.get(row).set(col, true);
                    isPerimeterTree = true;
                }

                if (!isPerimeterTree) {
                    Integer maxToLeft = treeHeights.get(row).subList(0, col).stream().reduce(Integer::max).get();
                    if (maxToLeft < thisTree) {
                        visibleTrees.get(row).set(col, true);
                    }

                    Integer maxToRight = treeHeights.get(row).subList(col + 1, treeHeights.get(row).size()).stream().reduce(Integer::max).get();
                    if (maxToRight < thisTree) {
                        visibleTrees.get(row).set(col, true);
                    }

                    final Integer columnNo = col;
                    Integer maxAbove = IntStream.range(0, row).map(rowNum -> treeHeights.get(rowNum).get(columnNo)).reduce(Integer::max).getAsInt();
                    if (maxAbove < thisTree) {
                        visibleTrees.get(row).set(col, true);
                    }

                    Integer maxBelow = IntStream.range(row + 1, treeHeights.size()).map(rowNum -> treeHeights.get(rowNum).get(columnNo)).reduce(Integer::max).getAsInt();
                    if (maxBelow < thisTree) {
                        visibleTrees.get(row).set(col, true);
                    }
                }
            }
        }
        printoutTrees(visibleTrees);

        return visibleTrees;
    }

    public List<List<Integer>> getProductOfVisibleTrees (List<List<Integer>> treeHeights) {
        List<List<Integer>> leftCount = getLeftCount(treeHeights);
        System.out.println("  **LEFT**  ");
        leftCount.stream().forEach(System.out::println);
        List<List<Integer>> rightCount = getRightCount(treeHeights);
        System.out.println("  **RIGHT**  ");
        rightCount.stream().forEach(System.out::println);
        List<List<Integer>> aboveCount = getAboveCount(treeHeights);
        System.out.println("  **ABOVE**  ");
        aboveCount.stream().forEach(System.out::println);
        List<List<Integer>> belowCount = getBelowCount(treeHeights);
        System.out.println("  **BELOW**  ");
        belowCount.stream().forEach(System.out::println);

        //
        List<List<Integer>> productOfVisibleTrees = new ArrayList<>();
        for(int i = 0 ; i< treeHeights.size(); i++) {
            List<Integer> b = new ArrayList<>();
            for(int j=0; j< treeHeights.get(0).size(); j++) {
                b.add(0);
            }
            productOfVisibleTrees.add(b);
        }

        for (int row=0; row< treeHeights.get(0).size(); row ++) {
            for (int col = 0; col < treeHeights.size(); col++) {

                Boolean isPerimeterTree = false;
                if (col == 0 || col == treeHeights.size() - 1 || row == 0 || row == treeHeights.get(0).size() - 1) {
                    isPerimeterTree = true;
                }

                if (!isPerimeterTree) {
                    Integer above = aboveCount.get(row).get(col);
                    Integer below = belowCount.get(row).get(col);
                    Integer left = leftCount.get(row).get(col);
                    Integer right = rightCount.get(row).get(col);
                    productOfVisibleTrees.get(row).set(col, above * below * left * right);
                }
            }
        }
        System.out.println("  **PRODUCT**  ");
        productOfVisibleTrees.stream().forEach(System.out::println);
        return productOfVisibleTrees;
    }

    public List<List<Integer>> getLeftCount(List<List<Integer>> treeHeights) {
        List<List<Integer>> leftCountOfVisibleTrees = new ArrayList<>();
        for(int i = 0 ; i< treeHeights.size(); i++) {
            List<Integer> b = new ArrayList<>();
            for(int j=0; j< treeHeights.get(0).size(); j++) {
                b.add(0);
            }
            leftCountOfVisibleTrees.add(b);
        }
        for (int row=0; row< treeHeights.get(0).size(); row ++) {
            for (int col = 0; col < treeHeights.size(); col++) {
                Integer thisTreeHeight = treeHeights.get(row).get(col);
                Boolean isPerimeterTree = false;
                if (col == 0 || col == treeHeights.size() - 1 || row == 0 || row == treeHeights.get(0).size() - 1) {
                    isPerimeterTree = true;
                }

                if (!isPerimeterTree) {
                    // Left
                    List<Integer> treeHeightsToLeftEdge = treeHeights.get(row)
                            .subList(0, col)
                            .stream().collect(Collectors.toList());
                    Collections.reverse(treeHeightsToLeftEdge);

                    List<Integer> shorterListToLeft = new ArrayList<>();
                    Boolean isLastTreeFound = false;
                    
                    for (int k=0; k < treeHeightsToLeftEdge.size(); k++) {
                        Integer treeHeightInQuestion = treeHeightsToLeftEdge.get(k);
                        if (!isLastTreeFound) {
                            if (treeHeightInQuestion < thisTreeHeight) {
                                shorterListToLeft.add(treeHeightInQuestion);
                            } else if (treeHeightInQuestion.equals(thisTreeHeight) || treeHeightInQuestion > thisTreeHeight) {
                                shorterListToLeft.add(treeHeightInQuestion);
                                isLastTreeFound = true;
                            }
                        }
                    }
                    leftCountOfVisibleTrees.get(row).set(col, shorterListToLeft.size());
                }
            }
        }
        return leftCountOfVisibleTrees;
    }

    public List<List<Integer>> getRightCount(List<List<Integer>> treeHeights) {
        List<List<Integer>> rightCountOfVisibleTrees = new ArrayList<>();
        for(int i = 0 ; i< treeHeights.size(); i++) {
            List<Integer> b = new ArrayList<>();
            for(int j=0; j< treeHeights.get(0).size(); j++) {
                b.add(0);
            }
            rightCountOfVisibleTrees.add(b);
        }
        for (int row=0; row< treeHeights.get(0).size(); row ++) {
            for (int col = 0; col < treeHeights.size(); col++) {
                Integer thisTreeHeight = treeHeights.get(row).get(col);
                Boolean isPerimeterTree = false;
                if (col == 0 || col == treeHeights.size() - 1 || row == 0 || row == treeHeights.get(0).size() - 1) {
                    isPerimeterTree = true;
                }

                if (!isPerimeterTree) {
                    // Right
                    List<Integer> treeHeightsToRightEdge = treeHeights.get(row)
                            .subList(col +1, treeHeights.get(0).size())
                            .stream().collect(Collectors.toList());
                    //Collections.reverse(treeHeightsToRightEdge);

                    List<Integer> shorterListToRight = new ArrayList<>();
                    Boolean isLastTreeFound = false;

                    for (int k=0; k < treeHeightsToRightEdge.size(); k++) {
                        Integer treeHeightInQuestion = treeHeightsToRightEdge.get(k);
                        if (!isLastTreeFound) {
                            if (treeHeightInQuestion < thisTreeHeight) {
                                shorterListToRight.add(treeHeightInQuestion);
                            } else if (treeHeightInQuestion.equals(thisTreeHeight) || treeHeightInQuestion > thisTreeHeight) {
                                shorterListToRight.add(treeHeightInQuestion);
                                isLastTreeFound = true;
                            }
                        }
                    }
                    rightCountOfVisibleTrees.get(row).set(col, shorterListToRight.size());
                }
            }
        }
        return rightCountOfVisibleTrees;
    }

    public List<List<Integer>> getAboveCount(List<List<Integer>> treeHeights) {
        List<List<Integer>> aboveCountOfVisibleTrees = new ArrayList<>();
        for(int i = 0 ; i< treeHeights.size(); i++) {
            List<Integer> b = new ArrayList<>();
            for(int j=0; j< treeHeights.get(0).size(); j++) {
                b.add(0);
            }
            aboveCountOfVisibleTrees.add(b);
        }
        for (int row=0; row< treeHeights.get(0).size(); row ++) {
            for (int col = 0; col < treeHeights.size(); col++) {
                Integer thisTreeHeight = treeHeights.get(row).get(col);
                Boolean isPerimeterTree = false;
                if (col == 0 || col == treeHeights.size() - 1 || row == 0 || row == treeHeights.get(0).size() - 1) {
                    isPerimeterTree = true;
                }

                if (!isPerimeterTree) {
                    // Above
                    final Integer columnNo = col;
                    List<Integer> treeHeightsToTopEdge = new ArrayList<>(IntStream.range(0, row).map(rowNum -> treeHeights.get(rowNum).get(columnNo)).boxed().toList());
//                    List<Integer> treeHeightsToTopEdge = treeHeights.get(row)
//                            .subList(col +1, treeHeights.get(0).size())
//                            .stream().collect(Collectors.toList());
                    Collections.reverse(treeHeightsToTopEdge);

                    List<Integer> shorterListToTop = new ArrayList<>();
                    Boolean isLastTreeFound = false;

                    for (int k=0; k < treeHeightsToTopEdge.size(); k++) {
                        Integer treeHeightInQuestion = treeHeightsToTopEdge.get(k);
                        if (!isLastTreeFound) {
                            if (treeHeightInQuestion < thisTreeHeight) {
                                shorterListToTop.add(treeHeightInQuestion);
                            } else if (treeHeightInQuestion.equals(thisTreeHeight) || treeHeightInQuestion > thisTreeHeight) {
                                shorterListToTop.add(treeHeightInQuestion);
                                isLastTreeFound = true;
                            }
                        }
                    }
                    aboveCountOfVisibleTrees.get(row).set(col, shorterListToTop.size());
                }
            }
        }
        return aboveCountOfVisibleTrees;
    }

    public List<List<Integer>> getBelowCount(List<List<Integer>> treeHeights) {
        List<List<Integer>> belowCountOfVisibleTrees = new ArrayList<>();
        for(int i = 0 ; i< treeHeights.size(); i++) {
            List<Integer> b = new ArrayList<>();
            for(int j=0; j< treeHeights.get(0).size(); j++) {
                b.add(0);
            }
            belowCountOfVisibleTrees.add(b);
        }
        for (int row=0; row< treeHeights.get(0).size(); row ++) {
            for (int col = 0; col < treeHeights.size(); col++) {
                Integer thisTreeHeight = treeHeights.get(row).get(col);
                Boolean isPerimeterTree = false;
                if (col == 0 || col == treeHeights.size() - 1 || row == 0 || row == treeHeights.get(0).size() - 1) {
                    isPerimeterTree = true;
                }

                if (!isPerimeterTree) {
                    // Below
                    final Integer columnNo = col;
                    List<Integer> treeHeightsToBottomEdge = new ArrayList<>(IntStream.range(row +1, treeHeights.size()).map(rowNum -> treeHeights.get(rowNum).get(columnNo)).boxed().toList());
//                    List<Integer> treeHeightsToTopEdge = treeHeights.get(row)
//                            .subList(col +1, treeHeights.get(0).size())
//                            .stream().collect(Collectors.toList());
                    //Collections.reverse(treeHeightsToBottomEdge);

                    List<Integer> shorterListToBottom = new ArrayList<>();
                    Boolean isLastTreeFound = false;

                    for (int k=0; k < treeHeightsToBottomEdge.size(); k++) {
                        Integer treeHeightInQuestion = treeHeightsToBottomEdge.get(k);
                        if (!isLastTreeFound) {
                            if (treeHeightInQuestion < thisTreeHeight) {
                                shorterListToBottom.add(treeHeightInQuestion);
                            } else if (treeHeightInQuestion.equals(thisTreeHeight) || treeHeightInQuestion > thisTreeHeight) {
                                shorterListToBottom.add(treeHeightInQuestion);
                                isLastTreeFound = true;
                            }
                        }
                    }
                    belowCountOfVisibleTrees.get(row).set(col, shorterListToBottom.size());
                }
            }
        }
        return belowCountOfVisibleTrees;
    }

    public void printoutTrees(List<List<Boolean>> visibleTrees) {
        for(int i = 0 ; i< visibleTrees.size(); i++) {
            for(int j=0; j< visibleTrees.get(0).size(); j++) {
                String tOrF = visibleTrees.get(i).get(j) ? "t" : "f";
                System.out.print(tOrF + " ");
            }
            System.out.println("");
        }
    }


}
