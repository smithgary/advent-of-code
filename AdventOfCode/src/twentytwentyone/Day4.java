package twentytwentyone;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 extends DataLoader{
    private List<BingoCard> bingoCards = new ArrayList<>();
    private List<Integer> calledNumbers = new ArrayList<>();

    // Part 2 solution
    public void getLastBingoWinningCard(DataInputSource dataInputSource){
        loadBingoCards(dataInputSource);

        for(int i=0; i<calledNumbers.size(); i++) {
            Integer newNumber = calledNumbers.get(i);
            bingoCards.stream().forEach(card -> card.addCalledNumber(newNumber));
            Boolean anyMatchingColumns = bingoCards.stream().map(card -> card.hasAnyColumnBeenCompleted()).reduce(Boolean.FALSE, Boolean::logicalOr);
            Boolean anyMatchingRows = bingoCards.stream().map(card -> card.hasAnyRowBeenCompleted()).reduce(Boolean.FALSE, Boolean::logicalOr);
            Integer totalUnMarkedOnMatchingColumn = bingoCards.stream().filter(card -> card.hasAnyColumnBeenCompleted()).map(card -> card.getTotalUnmarkedNumbers()).findFirst().orElse(0);
            Integer totalUnMarkedOnMatchingRow = bingoCards.stream().filter(card -> card.hasAnyRowBeenCompleted()).map(card -> card.getTotalUnmarkedNumbers()).findFirst().orElse(0);


            if(anyMatchingColumns || anyMatchingRows) {

                if(bingoCards.stream().count() > 1 ) {
                    List<BingoCard> cardOrCardsToRemove;
                    if (totalUnMarkedOnMatchingColumn > 0) {
                        cardOrCardsToRemove = bingoCards.stream().filter(Predicate.not(card -> card.hasAnyColumnBeenCompleted())).collect(Collectors.toList());
                    } else {
                        cardOrCardsToRemove = bingoCards.stream().filter(Predicate.not(card -> card.hasAnyRowBeenCompleted())).collect(Collectors.toList());
                    }
                    bingoCards = cardOrCardsToRemove;
                } else {
                    System.out.println("Match found at call " + newNumber);
                    if (totalUnMarkedOnMatchingColumn > 0) {
                        System.out.println("Total unmarked on last matching card was:" + totalUnMarkedOnMatchingColumn);
                        System.out.println("Calculation: " + totalUnMarkedOnMatchingColumn * newNumber);
                    } else {
                        System.out.println("Total unmarked on last matching card was:" + totalUnMarkedOnMatchingRow);
                        System.out.println("Calculation: " + totalUnMarkedOnMatchingRow * newNumber);
                    }
                    break;
                }
            }
        }
    }

    // Part 1 solution
    public void getBingo(DataInputSource dataInputSource){
        loadBingoCards(dataInputSource);

        for(int i=0; i<calledNumbers.size(); i++) {
            Integer newNumber = calledNumbers.get(i);
            bingoCards.stream().forEach(card -> card.addCalledNumber(newNumber));
            Boolean anyMatchingColumns = bingoCards.stream().map(card -> card.hasAnyColumnBeenCompleted()).reduce(Boolean.FALSE, Boolean::logicalOr);
            Boolean anyMatchingRows = bingoCards.stream().map(card -> card.hasAnyRowBeenCompleted()).reduce(Boolean.FALSE, Boolean::logicalOr);
            Integer totalUnMarkedOnMatchingColumn = bingoCards.stream().filter(card -> card.hasAnyColumnBeenCompleted()).map(card -> card.getTotalUnmarkedNumbers()).findFirst().orElse(0);
            Integer totalUnMarkedOnMatchingRow = bingoCards.stream().filter(card -> card.hasAnyRowBeenCompleted()).map(card -> card.getTotalUnmarkedNumbers()).findFirst().orElse(0);

            if(anyMatchingColumns || anyMatchingRows) {
                System.out.println("Match found at call " + newNumber);
                if (totalUnMarkedOnMatchingColumn > 0) {
                    System.out.println("Total unmarked on matching card was:" + totalUnMarkedOnMatchingColumn);
                    System.out.println("Calculation: " + totalUnMarkedOnMatchingColumn * newNumber);
                } else {
                    System.out.println("Total unmarked on matching card was:" + totalUnMarkedOnMatchingRow);
                    System.out.println("Calculation: " + totalUnMarkedOnMatchingRow * newNumber);
                }
                break;
            }
        }
    }

    public void loadBingoCards(DataInputSource dataInputSource) {
        loadDataSource(dataInputSource, 1);

        String[] calledNumbersAsStrings = column1.get(0).split(",");
        int[] calledInts = Arrays.stream(calledNumbersAsStrings).mapToInt(Integer::parseInt).toArray();
        calledNumbers = Arrays.stream(calledInts).boxed().collect(Collectors.toList());

        final List<List<Integer>> collect = column1.stream()
                .filter(Predicate.not(row -> row.contains(",")))
                .map(String::trim)
                .map(line -> line.split("\\s+"))
                .filter(a -> a.length > 1)
                .map(line -> Arrays.stream(line).map(Integer::valueOf).toArray(Integer[]::new))
                .map(i -> Arrays.stream(i).collect(Collectors.toList()))
                .collect(Collectors.toList());

        bingoCards.clear();

        IntStream.range(0, collect.size()).filter(i -> i % 5 == 0).forEach(i -> {
            List<List<Integer>> listForCard = new ArrayList<>();
            IntStream.range(i, i+5).forEach(cardRow -> listForCard.add(collect.get(cardRow)));
            bingoCards.add(new BingoCard(listForCard));
        });
    }

    @Override
    public void loadSampleValues() {
        column1.clear();
        column1.add("7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1");
        column1.add("22 13 17 11  0");
        column1.add("8  2 23  4 24");
        column1.add("21  9 14 16  7");
        column1.add("6 10  3 18  5");
        column1.add("1 12 20 15 19");
        column1.add("");
        column1.add("3 15  0  2 22");
        column1.add("9 18 13 17  5");
        column1.add("19  8  7 25 23");
        column1.add("20 11 10 24  4");
        column1.add("14 21 16 12  6");
        column1.add("");
        column1.add("14 21 17 24  4");
        column1.add("10 16 15  9 19");
        column1.add("18  8 23 26 20");
        column1.add("22 11 13  6  5");
        column1.add("2  0 12  3  7");

    }

}

class BingoCard{
    List<Integer> calledNumbers = new ArrayList<>();
    List<List<Integer>> cardValues;
    Integer[][] cardDigits = new Integer[5][5];
    public BingoCard(List<List<Integer>> cardValues) {
        this.cardValues = cardValues;
        convertListToArray();
    }
    public void addCalledNumber(Integer number) {
        calledNumbers.add(number);
    }

    public Boolean hasAnyRowBeenCompleted(){
        if (cardValues == null || calledNumbers == null) {
            return false;
        }
        return cardValues.stream().map(list -> list.stream().allMatch(calledNumbers::contains)).reduce(Boolean.FALSE, Boolean::logicalOr);
    }

    public void printOutCardNumbers(){
        cardValues.stream().forEach(list -> list.stream().forEach(System.out::println));
    }

    public Boolean hasAnyColumnBeenCompleted(){
        if (cardValues == null || calledNumbers == null) {
            return false;
        }
        //Can't do this with streams .. ?
        List<List<Integer>> bingoVerticals = new ArrayList<>();
        for(int colNum = 0; colNum < 5; colNum ++) {
            List<Integer> columnList = new ArrayList<>();
            for (int rowNum = 0; rowNum < 5; rowNum++) {
                columnList.add(cardDigits[rowNum][colNum]);
            }
            bingoVerticals.add(columnList);
        }

        return bingoVerticals.stream().map(list -> list.stream().allMatch(calledNumbers::contains)).reduce(Boolean.FALSE, Boolean::logicalOr);
    }
    public Integer getTotalUnmarkedNumbers(){
        return cardValues.stream().flatMap(list -> list.stream().filter(Predicate.not(calledNumbers::contains))).reduce(0, Integer::sum);
    }

    private void convertListToArray() {
        Integer totalRows = cardValues.size();
        for (int i=0; i< totalRows; i++) {
            for (int j = 0; j < totalRows; j++) {
                cardDigits[i][j] = cardValues.get(i).get(j);
            }
        }
    }
}
