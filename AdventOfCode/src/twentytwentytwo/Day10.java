package twentytwentytwo;

import twentytwentyone.AocTest;
import twentytwentyone.DataLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10 extends DataLoader implements AocTest {
    Map<Integer, Integer> xValueAtCycles;
    Map<Integer, Character> pixels;

    @Override
    public String calculatePartOne() {
        List<String> startingList = column1.stream().collect(Collectors.toList());
        List<String[]> operations = startingList.stream().map(s -> s.split(" ")).collect(Collectors.toList());
        xValueAtCycles = new HashMap<>();
        processOperations(operations, false);
        return xValueAtCycles.entrySet().stream()
                .map(Map.Entry::getKey)
                .map(key -> key * xValueAtCycles.get(key))
                .reduce(Integer::sum)
                .get().toString();

    }

    @Override
    public String calculatePartTwo() {
        List<String> startingList = column1.stream().collect(Collectors.toList());
        List<String[]> operations = startingList.stream().map(s -> s.split(" ")).collect(Collectors.toList());
        xValueAtCycles = new HashMap<>();
        pixels = new HashMap<>();
        processOperations(operations, true);
        printPixels();

        return "";
    }
    public void processOperations(List<String[]> operations, Boolean isPart2) {
        final Integer numberOfOperations = operations.size();
        Integer X = 1;
        Integer cycle = 1;
        if (isPart2) {
            assessCycle(cycle, X, isPart2);
        }

        for(int i=0; i<numberOfOperations; i++) {
            Boolean isOperation = operations.get(i).length > 1;
            if (isOperation) {
                cycle +=1;
                assessCycle(cycle, X, isPart2);

                String additionOrSubtraction = operations.get(i)[1];
                Boolean isSubtraction = additionOrSubtraction.startsWith("-");
                if (isSubtraction) {
                    Integer toSubtract = Integer.parseInt(additionOrSubtraction.substring(1));
                    X = X - toSubtract;
                } else {
                    Integer toAdd = Integer.parseInt(additionOrSubtraction);
                    X = X + toAdd;
                }
                cycle += 1;
                assessCycle(cycle, X, isPart2);
            } else {
                cycle += 1;
                assessCycle(cycle, X, isPart2);
            }
        }
    }

    public void assessCycle(Integer cycle, Integer X, Boolean isPartTwo) {
        if (cycle >19 && (cycle - 20) % 40 == 0) {
            xValueAtCycles.put(cycle, X);
        }

        if (isPartTwo) {
            Integer hCycle = 0;
            if (cycle < 41) {
                hCycle = cycle;
            }
            // bruteforce.. too tired to redo..
            if ((cycle > 40) && (cycle < 81)) {
                hCycle = cycle - 40;
            }
            if ((cycle > 80) && (cycle < 121)) {
                hCycle = cycle - 80;
            }
            if ((cycle > 120) && (cycle < 161)) {
                hCycle = cycle - 120;
            }
            if ((cycle > 160) && (cycle < 201)) {
                hCycle = cycle - 160;
            }
            if ((cycle > 200) && (cycle < 241)) {
                hCycle = cycle - 200;
            }


            if ((X - 1 == hCycle-1) || (X == hCycle-1) || (X + 1 == hCycle-1)) {
                    pixels.put(cycle -1, '#');
                } else {
                    pixels.put(cycle -1, '.');
                }
        }
    }

    public void printPixels() {
        for (int x1=0; x1<40; x1++) {
            System.out.print(pixels.get(x1));
        }
        System.out.println();
        for (int x1=40; x1<80; x1++) {
            System.out.print(pixels.get(x1));
        }
        System.out.println();
        for (int x1=80; x1<120; x1++) {
            System.out.print(pixels.get(x1));
        }
        System.out.println();
        for (int x1=120; x1<160; x1++) {
            System.out.print(pixels.get(x1));
        }
        System.out.println();
        for (int x1=160; x1<200; x1++) {
            System.out.print(pixels.get(x1));
        }
        System.out.println();
        for (int x1=200; x1<240; x1++) {
            System.out.print(pixels.get(x1));
        }
    }

}
