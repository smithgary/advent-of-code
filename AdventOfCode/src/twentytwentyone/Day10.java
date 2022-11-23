package twentytwentyone;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10 extends DataLoader {

    public Day10() {
        loadClosingMatch();
        loadClosingValue();
        loadAdditionalValue();
    }
    private static Map<String, String> closingMatch;
    private static Map<String, Integer> closingMatchValue;
    private static HashMap<Character, Integer> closingAdditionalValue;

    public Integer removeCorrupted() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        Integer corruptedResult = 0;

        Optional<Integer> i = startingList.stream().map(s -> getMismatchedClosingSymbolValue(s)).reduce(Integer::sum);
        if (i.isPresent()) {
            System.out.println(i.get());
            corruptedResult = i.get();
        }
        return corruptedResult;
    }

    public BigInteger completeMissing() {
        List<String> startingList = column1.stream().collect(Collectors.toList());

        List<BigInteger> l = startingList.stream().map(s -> getMissingSymbols(s)).filter(i -> i.longValue() > 0).sorted().toList();
        Integer countSize = l.size();

        BigInteger answer = l.get(countSize/2);

        System.out.println(answer);
        return answer;
    }

    public Integer getMismatchedClosingSymbolValue(String openingSymbol) {
        Integer score = 0;
        Stack<String> s = new Stack();
        for (int i = 0; i < openingSymbol.length() - 1; i++) {
            String thisChar = openingSymbol.substring(i, i + 1);
            // Is it an opener?
            if (closingMatch.containsKey(thisChar)) {
                // Yes => add to stack
                s.push(thisChar);
            } else {
                // No => compare with top of stack
                String topOfStack = s.pop();
                if (!closingMatch.get(topOfStack).equals(thisChar)) {
                    score = closingMatchValue.get(thisChar);
                    System.out.println("Mismatch: " + i + " Expected " + closingMatch.get(topOfStack) + ", but found " + thisChar + " instead. SCORE: " + score);

                    break;
                }
            }
        }
        return score;
    }

    public BigInteger getMissingSymbols(String openingSymbol) {
        BigInteger score = BigInteger.ZERO;
        Boolean isCorrupted = false;
        Stack<String> s = new Stack();
        for (int i = 0; i < openingSymbol.length(); i++) {
            String thisChar = openingSymbol.substring(i, i + 1);
            // Is it an opener?
            if (closingMatch.containsKey(thisChar)) {
                // Yes => add to stack
                s.push(thisChar);
            } else {
                // No => compare with top of stack
                String topOfStack = s.pop();
                if (!closingMatch.get(topOfStack).equals(thisChar)) {
                    isCorrupted = true;
                    break;
                }
            }
        }

        ArrayList<String> completionString = new ArrayList<>();
        if (!isCorrupted && s.size() > 0 ) {
            int stackSize = s.size();
            for (int i=0; i< stackSize; i++) {
                String topOfStack = s.pop();
                String additionalSymbol = closingMatch.get(topOfStack);
                completionString.add(additionalSymbol);
            }
        }
        String str = String.join("",completionString);
        score = calculateCompletionScoreForLine(str);

        return score;
    }

    private BigInteger calculateCompletionScoreForLine(String s) {
        BigInteger multiplier = BigInteger.valueOf(5);
        BigInteger result = BigInteger.ZERO;

        for (char c : s.toCharArray()) {
            result = result.multiply(multiplier).add(BigInteger.valueOf(closingAdditionalValue.get(c)));
        }

        return result;
    }

    private void loadClosingMatch() {
        closingMatch = new HashMap<>();
        closingMatch.put("[", "]");
        closingMatch.put("(", ")");
        closingMatch.put("{", "}");
        closingMatch.put("<", ">");
    }

    private void loadClosingValue() {
        closingMatchValue = new HashMap<>();
        closingMatchValue.put(")", 3);
        closingMatchValue.put("]", 57);
        closingMatchValue.put("}", 1197);
        closingMatchValue.put(">", 25137);
    }

    private void loadAdditionalValue() {
        closingAdditionalValue = new HashMap<>();
        closingAdditionalValue.put(')', 1);
        closingAdditionalValue.put(']', 2);
        closingAdditionalValue.put('}', 3);
        closingAdditionalValue.put('>', 4);
    }

}

