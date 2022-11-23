package twentytwentyone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day1 {
    ArrayList<Integer> entries = new ArrayList<>();
    String fileName = "";

    public void countDecreasing(Integer option){
        final List<Integer> finalIntegerList = configureEntries(option);

        System.out.println("Total Single: "+  IntStream.range(0, finalIntegerList.size() -1)
                .filter(i -> finalIntegerList.get(i) < finalIntegerList.get(i+1))
                .count());
    }

    public void countThree(Integer option) {
        final List<Integer> finalIntegerList = configureEntries(option);

        System.out.println("Total 3: "+ IntStream.range(0, finalIntegerList.size() -3)
                .filter(i -> isThisThreeLessThanNextThree(i, finalIntegerList))
                //.filter(i -> isThisThreeLessThanTheNextThree(i, finalIntegerList))
                .count());
    }

    private List<Integer> configureEntries(Integer option){
        List<Integer> integerList = new ArrayList<>();
        if(option == DataInputSource.SAMPLE.getValue()) {
            integerList = getSampleIntegers();
        }
        if(option == DataInputSource.TEST.getValue()) {
            readLines(fileName);
            integerList = entries;
        }
        return integerList;
    }

    private Boolean isThisThreeLessThanNextThree(Integer integer, List<Integer> finalIntegerList) {
        final Integer sumThisThree = finalIntegerList.get(integer) + finalIntegerList.get(integer + 1) + finalIntegerList.get(integer + 2);
        final Integer sumNextThree = finalIntegerList.get(integer + 1) + finalIntegerList.get(integer + 2) + finalIntegerList.get(integer + 3);
        return (sumThisThree < sumNextThree);
    }

    private Boolean isThisThreeLessThanTheNextThree(Integer thisItem, List<Integer> finalIntegerList) {
        long sumThisThree = IntStream.range(thisItem, thisItem + 2).map(i -> finalIntegerList.get(i)).sum();
        long sumNextThree = IntStream.range(thisItem + 1, thisItem + 3).map(i -> finalIntegerList.get(i)).sum();
        System.out.println(sumThisThree + " " + sumNextThree);
        return (sumThisThree < sumNextThree);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void readLines(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            entries.clear();
            stream.forEach(line -> {
                entries.add(Integer.parseInt(line));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getSampleIntegers(){
        return Arrays.asList(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);
    }
}
