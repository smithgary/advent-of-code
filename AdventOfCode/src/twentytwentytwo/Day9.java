package twentytwentytwo;

import twentytwentyone.AocTest;
import twentytwentyone.DataLoader;

import java.util.*;
import java.util.stream.Collectors;

public class Day9 extends DataLoader implements AocTest {
    List<List<Boolean>> tailHits;
    @Override
    public String calculatePartOne() {
        List<String> startingList = column1.stream().collect(Collectors.toList());
        Map<String, Integer> limits = getLimits(startingList);
        intitaliseGrid(limits);
        processMoves(limits, startingList, 1);
        Long countTailPositions = tailHits.stream().flatMap(List::stream).filter(t -> t).count();
        return countTailPositions.toString();
    }

    @Override
    public String calculatePartTwo() {
        List<String> startingList = column1.stream().collect(Collectors.toList());
        Map<String, Integer> limits = getLimits(startingList);
        intitaliseGrid(limits);
        processMoves(limits, startingList, 9);
        Long countTailPositions = tailHits.stream().flatMap(List::stream).filter(t -> t).count();
        return countTailPositions.toString();
    }

    public void processMoves(Map<String, Integer> limits, List<String> moves, Integer tailLength) {
        // Set starting position, ie = x = limits.L + 1, y=limits.D + 1
        // set tail same as head
        Integer headX = Math.abs(limits.get("L"));
        Integer headY = Math.abs(limits.get("D"));

        // replace tailX with knotX.get(knot);
        // Initialise knot positions; 0 = head, last = one to be counted
        List<Integer> knotX = new ArrayList<>();
        List<Integer> knotY = new ArrayList<>();
        for (int knot=0; knot < tailLength +1; knot ++) {
            knotX.add(headX);
            knotY.add(headY);
        }

        for (int i=0; i< moves.size(); i++) {
            System.out.println(" == " + moves.get(i) + " == " );
            String[] singleMove = moves.get(i).split(" ");
            String direction = singleMove[0];
            Integer steps = Integer.parseInt(singleMove[1]);

            for (int j=0; j<steps; j++) {
                if (direction.equals("R")) {
                    knotX.set(0, knotX.get(0) + 1);
                }
                if(direction.equals("L")) {
                    knotX.set(0, knotX.get(0) - 1);
                }
                if(direction.equals("U")) {
                    knotY.set(0, knotY.get(0) + 1);
                }
                if(direction.equals("D")) {
                    knotY.set(0, knotY.get(0) - 1);
                }
                //assess tail(s)
                for (int knot=1; knot<tailLength +1; knot++) {
                    Boolean tailShouldMove = false;
                    if (((knotX.get(knot-1) - knotX.get(knot)) > 1) ||
                            ((knotX.get(knot) - knotX.get(knot-1)) > 1) ||
                            ((knotY.get(knot-1) - knotY.get(knot)) > 1) ||
                            ((knotY.get(knot) - knotY.get(knot-1)) > 1)) {
                        tailShouldMove = true;
                    }

                    if (tailShouldMove) {
                        Boolean isMoveCompleted = false;
                        // if same col, move closer by 1
                        if (knotX.get(knot-1) == knotX.get(knot)) {
                            if (knotY.get(knot-1) > knotY.get(knot)) {
                                knotY.set(knot, knotY.get(knot) + 1);
                            } else {
                                knotY.set(knot, knotY.get(knot) - 1);
                            }
                            isMoveCompleted = true;
                        }
                        // if same row, move closer by 1
                        if (knotY.get(knot -1) == knotY.get(knot)) {
                            if (knotX.get(knot-1) > knotX.get(knot)) {
                                knotX.set(knot, knotX.get(knot) + 1);
                            } else {
                                knotX.set(knot, knotX.get(knot) - 1);
                            }
                            isMoveCompleted = true;
                        }
                        // if neither of the above, move diagonally towards..
                        if (!isMoveCompleted) {
                            if (knotX.get(knot-1) > knotX.get(knot)) {
                                knotX.set(knot, knotX.get(knot) + 1);
                            }
                            if (knotX.get(knot-1) < knotX.get(knot)) {
                                knotX.set(knot, knotX.get(knot) - 1);
                            }
                            if (knotY.get(knot-1) > knotY.get(knot)) {
                                knotY.set(knot, knotY.get(knot) + 1);
                            }
                            if (knotY.get(knot-1) < knotY.get(knot)) {
                                knotY.set(knot, knotY.get(knot) - 1);
                            }
                        }
                    }
                    //mark last tail position as true
                    if (knot == tailLength) {
                        System.out.println("Tail - X:" + knotX.get(knot) + " Y:" + knotY.get(knot));
                        tailHits.get(knotX.get(knot)).set(knotY.get(knot), true);
                    }
                }
            }
        }
    }

    public void printTailGrid(){
        tailHits.stream().map(listBoolean -> listBoolean.stream().map(b -> b ? '#' : '.').collect(Collectors.toList())).forEach(System.out::println);
    }
    public void intitaliseGrid(Map<String, Integer> limits) {
        Integer width = limits.get("R") - limits.get("L") +1; // + 5;
        Integer height = limits.get("U") - limits.get("D") +1; // + 5;
        tailHits = new ArrayList<>();

        for(int i=0; i<width; i++) {
            List<Boolean> b = new ArrayList<>();
            for(int j=0; j<height; j++) {
                b.add(false);
            }
            tailHits.add(b);
        }
    }

    public Map<String, Integer> getLimits(List<String> moves) {
        Map<String, Integer> maxMoves = new HashMap<>();
        maxMoves.put("R", 0);
        maxMoves.put("L", 0);
        maxMoves.put("U", 0);
        maxMoves.put("D", 0);

        Integer positionX = 0;
        Integer positionY = 0;

        for(int i=0; i< moves.size(); i++) {
            String[] singleMove = moves.get(i).split(" ");
            String direction = singleMove[0];
            Integer steps = Integer.parseInt(singleMove[1]);
            if(direction.equals("R")) {
                positionX += steps;
                if (positionX > maxMoves.get(direction)) {
                   maxMoves.put(direction, positionX);
                }
            }
            if(direction.equals("L")) {
                positionX -= steps;
                if (positionX < maxMoves.get(direction)) {
                    maxMoves.put(direction, positionX);
                }
            }
            if(direction.equals("U")) {
                positionY += steps;
                if (positionY > maxMoves.get(direction)) {
                    maxMoves.put(direction, positionY);
                }
            }
            if(direction.equals("D")) {
                positionY -= steps;
                if (positionY < maxMoves.get(direction)) {
                    maxMoves.put(direction, positionY);
                }
            }
        }
        System.out.println("Max: Up " + maxMoves.get("U"));
        System.out.println("Max: Down " + maxMoves.get("D"));
        System.out.println("Max: Left " + maxMoves.get("L"));
        System.out.println("Max: Right " + maxMoves.get("R"));

        return maxMoves;
    }
}
