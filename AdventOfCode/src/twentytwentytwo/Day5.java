package twentytwentytwo;

import twentytwentyone.*;

import java.util.*;
import java.util.stream.*;

public class Day5 extends DataLoader implements AocTest {

    List<Stack<String>> crateStack;
    List<Integer[]> crateMoves;
    Integer lastCrateRow;

    @Override
    public String calculatePartOne() {

        loadQueues();
        loadMoves();
        processMoves(true);

        return printAndPopFinal();
    }

    @Override
    public String calculatePartTwo() {

        loadQueues();
        loadMoves();
        processMoves(false);

        return printAndPopFinal();
    }

    public void loadQueues() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        lastCrateRow = getLastCrateRow(startingList);
        List<String> crateList = startingList.subList(0, lastCrateRow);

        crateStack = new ArrayList<>();

        for (int i = crateList.size() - 1; i > -1; i--) {
            initializeCrates(crateList.get(i), lastCrateRow);
        }
    }

    public void processMoves(Boolean useSingle) {
        for (int i = 0; i < crateMoves.size(); i++) {
            Integer quantity = crateMoves.get(i)[0];
            Integer from = crateMoves.get(i)[1];
            Integer to = crateMoves.get(i)[2];

            Stack<String> tempStack = new Stack();

            for (int j = 0; j < quantity; j++) {
                String s = crateStack.get(from - 1).pop();
                if (useSingle) {
                    crateStack.get(to - 1).push(s);
                } else {
                    tempStack.push(s);
                }
            }

            if (!useSingle) {
                Integer tempStackSize = tempStack.size();
                for (int k = 0; k < tempStackSize; k++) {
                    String s = tempStack.pop();
                    crateStack.get(to - 1).push(s);
                }
            }

        }

    }

    public String printAndPopFinal() {
        StringBuilder output = new StringBuilder();
        for (int j = 0; j < crateStack.size(); j++) {
            String s = crateStack.get(j).pop();
            output.append(s);
        }
        return output.toString();
    }

    public void loadMoves() {
        List<String> startingList = column1.stream().collect(Collectors.toList());
        crateMoves = new ArrayList<>();

        List<String> moveList = startingList.subList(lastCrateRow + 2, startingList.size());
        moveList.stream().forEach(s -> loadMoves(s));
    }


    public Integer initializeCrates(String s, Integer lastCrateRow) {

        ArrayList<String> lineOfCrates = new ArrayList<>();
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            // Letter is every fourth character, starting at 1
            if ((i - 1) % 4 == 0) {
                lineOfCrates.add(String.valueOf(charArray[i]));
            }
        }

        if (crateStack.size() < 1) {
            for (int i = 0; i < lineOfCrates.size(); i++) {
                crateStack.add(new Stack<>());
            }
        }

        //transpose crates from row to stack
        for (int i = 0; i < lineOfCrates.size(); i++) {
            if (!lineOfCrates.get(i).equals(" ")) {
                crateStack.get(i).push(lineOfCrates.get(i));
            }
        }

        return lastCrateRow;
    }

    public void loadMoves(String s) {
        // split on space
        Integer moves[] = new Integer[3];
        String[] moveMessages = s.split(" ");
        moves[0] = Integer.parseInt(moveMessages[1]);
        moves[1] = Integer.parseInt(moveMessages[3]);
        moves[2] = Integer.parseInt(moveMessages[5]);
        crateMoves.add(moves);
    }

    public Integer getLastCrateRow(List<String> sampleData) {

        Integer lastLine = 0;
        for (int i = 0; i < sampleData.size(); i++) {
            char[] charArray = sampleData.get(i).toCharArray();
            if (charArray[1] == 49) { //Number 1 End of crates
                return i;
            }
        }
        return lastLine;
    }
}


