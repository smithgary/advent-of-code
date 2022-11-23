package twentytwentyone;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day8 {
    public static final String ZERO = "abcefg";
    public static final String ONE = "cf";
    public static final String TWO = "acdeg";
    public static final String THREE = "acdfg";
    public static final String FOUR = "bcdf";
    public static final String FIVE = "abdfg";
    public static final String SIX = "abdefg";
    public static final String SEVEN = "acf";
    public static final String EIGHT = "abcdefg";
    public static final String NINE = "abcdfg";
    public static final ArrayList<String> VALID_NUMBERS = new ArrayList<>(Arrays
            .asList(ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE));
    Map<String, String> segmentMap = new HashMap<>();   // Map with certainty
    Map<String, List<String>> possibleMap = new HashMap<>();  // Map with uncertainty
    public static final String TESTLINE = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab";
    public static final String TESTOUT = "cdfeb fcadb cdfeb cdbaf";
    public static final String TESTLINE1 = "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb";
    public static final String TESTOUT1 = "fdgacbe cefdb cefbgd gcbe";

    public static final ArrayList<String> combination1 = new ArrayList<>(Arrays.asList("a", "c", "f", "b", "d", "e", "g"));
    public static final ArrayList<String> combination2 = new ArrayList<>(Arrays.asList("a", "c", "f", "b", "d", "g", "e"));
    public static final ArrayList<String> combination3 = new ArrayList<>(Arrays.asList("a", "c", "f", "d", "b", "e", "g"));
    public static final ArrayList<String> combination4 = new ArrayList<>(Arrays.asList("a", "c", "f", "d", "b", "g", "e"));
    public static final ArrayList<String> combination5 = new ArrayList<>(Arrays.asList("a", "f", "c", "b", "d", "e", "g"));
    public static final ArrayList<String> combination6 = new ArrayList<>(Arrays.asList("a", "f", "c", "b", "d", "g", "e"));
    public static final ArrayList<String> combination7 = new ArrayList<>(Arrays.asList("a", "f", "c", "d", "b", "e", "g"));
    public static final ArrayList<String> combination8 = new ArrayList<>(Arrays.asList("a", "f", "c", "d", "b", "g", "e"));

    public static final List<ArrayList<String>> possibleCombinations =
            new ArrayList<>(Arrays.asList(combination1,combination2, combination3, combination4, combination5, combination6, combination7, combination8));


    //be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb [] fdgacbe cefdb cefbgd gcbe
    //Seven segment displays
    //     1, 2, 3, 4, 5, 6, 7
    // 0 : a, b, c,  , e, f, g : Total 6
    // 1 :  ,  , c,  ,  , f,   : Total 2      ** Unique
    // 2 : a,  , c, d, e,  , g : Total 5
    // 3 : a,  , c, d,  , f, g : Total 5
    // 4 :  , b, c, d,  , f,   : Total 4      ** Unique
    // 5 : a, b,  , d,  , f, g : Total 5
    // 6 : a, b,  , d, e, f, g : Total 6
    // 7 : a,  , c,  ,  , f,   : Total 3      ** Unique
    // 8 : a, b, c, d, e, f, g : Total 7      ** Unique
    // 9 : a, b, c, d,  , f, g : Total 6

    // 2 digits = 1
    // 3 digits = 7
    // 4 digits = 4
    // 5 digits = 2 OR 3 OR 5
    // 6 digits = 0 OR 6 OR 9
    // 7 digits = 8

    // For given pattern then..
    // Segment 1

    // All digits within a display use the same connections
    // Ten signal patterns sent through
    // Try
    // Read in entries.
    ArrayList<String> entries = new ArrayList<>();
    ArrayList<String> rawEntries = new ArrayList<>();
    ArrayList<String> rawOutputs = new ArrayList<>();

    ArrayList<String> firstChar = new ArrayList<>();
    ArrayList<String> secondChar = new ArrayList<>();
    ArrayList<String> thirdChar = new ArrayList<>();
    ArrayList<String> fourthChar = new ArrayList<>();

    Integer characterSequenceCount;
    Integer maxSegmentsIlluminated = 7;
    Map<Integer, ArrayList<String>> characterLists = new HashMap<>();


    public Day8(){
        characterLists.put(1, firstChar);
        characterLists.put(2, secondChar);
        characterLists.put(3, thirdChar);
        characterLists.put(4, fourthChar);
        characterSequenceCount = 4;
    }

    // Use this one
    public void printOutLines() {
        entries.forEach(System.out::println);
    }

    public void getAllEntries() {
        IntStream.range(1, characterSequenceCount).forEach(integer -> getEntriesFor(integer));
    }

    public void getAllMappedLengths() {
        IntStream.range(1, characterSequenceCount).forEach(characterSequence -> characterLists.get(characterSequence).forEach(column -> {
            IntStream.range(1, maxSegmentsIlluminated).forEach(lengthToFind ->
                    System.out.println( "column: " + characterSequence + " charLength: " + lengthToFind + " count: " + getCountOfSequenceLengthForColumn(lengthToFind, characterSequence)));
        }));
    }

    public void getEntriesFor(Integer integer) {
        entries.forEach(line -> characterLists.get(integer).add(line.split(Pattern.quote( " "))[integer]));
        characterLists.get(integer).forEach(System.out::println);
    }

    public Long getCountOfSequenceLengthForColumn(Integer lengthToFind, Integer charSeqToCheck) {
        return characterLists.get(charSeqToCheck).stream().map(charSeq -> charSeq.length()).filter(seqLength -> seqLength.equals(lengthToFind)).count();
    }

    // Use this
    public void mapLinesToInts() {
        entries.stream().forEach(line -> {
            System.out.println();
            IntStream.range(1, characterSequenceCount +1).forEach(columnNumber -> System.out.print((line.split(Pattern.quote(" "))[columnNumber].length())));
        });
    }

    // part 2
    //Seven segment displays
    //     1, 2, 3, 4, 5, 6, 7
    // 0 : a, b, c,  , e, f, g : Total 6                    6
    // 1 :  ,  , c,  ,  , f,   : Total 2      ** Unique 2
    // 2 : a,  , c, d, e,  , g : Total 5                   5
    // 3 : a,  , c, d,  , f, g : Total 5                   5
    // 4 :  , b, c, d,  , f,   : Total 4      ** Unique   4
    // 5 : a, b,  , d,  , f, g : Total 5                   5
    // 6 : a, b,  , d, e, f, g : Total 6                    6
    // 7 : a,  , c,  ,  , f,   : Total 3      ** Unique  3
    // 8 : a, b, c, d, e, f, g : Total 7      ** Unique      7
    // 9 : a, b, c, d,  , f, g : Total 6                    6
    //     x  .  .  .  .  .  .

    // 1 :  ,  , c,  ,  , f,   : Total 2      ** Unique 2
    // 4 :  , b, c, d,  , f,   : Total 4      ** Unique   4
    // 7 : a,  , c,  ,  , f,   : Total 3      ** Unique  3

    // 2 : a,  , c, d, e,  , g : Total 5                   5
    // 3 : a,  , c, d,  , f, g : Total 5                   5
    // 5 : a, b,  , d,  , f, g : Total 5                   5

    // 4 :  , b, c, d,  , f,   : Total 4      ** Unique   4
    // 0 : a, b, c,  , e, f, g : Total 6                    6
    // 6 : a, b,  , d, e, f, g : Total 6                    6
    // 9 : a, b, c, d,  , f, g : Total 6                    6
    //     x  .  .  .     .

    // 2 digits = 1
    // 3 digits = 7
    // 4 digits = 4
    // 5 digits = 2 OR 3 OR 5
    // 6 digits = 0 OR 6 OR 9
    // 7 digits = 8
//   8:
//  aaaa
// b    c
// b    c
//  dddd
// e    f
// e    f
//  gggg

    // Need to take single line.
    // Any with 2 digits (1) , letters map to either c or f
    // Any with 3 digits (7) , letters map to either a, c or f
    // Any with 4 digits (4) , letters map to either b, c, d or f
    // Any with 5 digits (2, 3, 5), letters map to a, c, ..
    // Any with 6 digits

    // Answer to part 2
    public void kickOffPart2() {
        ArrayList<Integer> finalIntegersConverted = new ArrayList<>();
        Integer size = rawEntries.size();
        for(int i=0; i< size; i++) {
            finalIntegersConverted.add(determineMapForLine(rawEntries.get(i), rawOutputs.get(i)));
        }
        System.out.println(finalIntegersConverted.stream().reduce(0, Integer::sum));
    }

    public Integer determineMapForLine(String inputLine, String outputLine) {

        String[] inputs = inputLine.split(Pattern.quote(" "));
        List<String> inputStrings = Arrays.stream(inputs).collect(Collectors.toList());
        List<Integer> inputLengths = Arrays.stream(inputs).map(entry -> entry.length()).collect(Collectors.toList());

        String[] entryStrings = new String[inputs.length];

        // First check if have a 2 and 3, in which case the one letter different maps to a.
        // Also c and f will be the other two - just not sure which way around
        if(inputLengths.contains(2) && inputLengths.contains(3)) {

            String firstChar = inputStrings.get(inputLengths.indexOf(2)).charAt(0) + "";
            String secondChar = inputStrings.get(inputLengths.indexOf(2)).charAt(1) + "";
            String diff = inputStrings.get(inputLengths.indexOf(3)).replace(firstChar, "").replace(secondChar, "");
            entryStrings[0] = diff;
            entryStrings[1] = firstChar;
            entryStrings[2] = secondChar;
            segmentMap.put(diff, "a");
            possibleMap.put(diff, Arrays.asList("a"));
            possibleMap.put(firstChar, Arrays.asList("c", "f"));
            possibleMap.put(secondChar, Arrays.asList("f", "c"));
        }
        // If have 2 and 4, other two must be b and d, but could be either..
        if(inputLengths.contains(2) && inputLengths.contains(4)) {
            String firstChar = inputStrings.get(inputLengths.indexOf(2)).charAt(0) + "";
            String secondChar = inputStrings.get(inputLengths.indexOf(2)).charAt(1) + "";
            //diff here is two chars long
            String diff = inputStrings.get(inputLengths.indexOf(4)).replace(firstChar, "").replace(secondChar, "");
            String firstDiff = diff.charAt(0) + "";
            String secondDiff = diff.charAt(1) + "";
            entryStrings[3] = firstDiff;
            entryStrings[4] = secondDiff;
            possibleMap.put(firstDiff, Arrays.asList("b", "d"));
            possibleMap.put(secondDiff, Arrays.asList("d", "b"));
        }
        // If have 3 and 4, other two must be b and d, but could be either..
        // Also if have 2, 3, 4 the only 2 letters missing map to e and g.
        if(inputLengths.contains(2) && inputLengths.contains(3) && inputLengths.contains(4)) {
            String fullOptions = "abcdefg";
            String firstChar2 = inputStrings.get(inputLengths.indexOf(2)).charAt(0) + "";
            String secondChar2 = inputStrings.get(inputLengths.indexOf(2)).charAt(1) + "";

            String firstChar3 = inputStrings.get(inputLengths.indexOf(3)).charAt(0) + "";
            String secondChar3 = inputStrings.get(inputLengths.indexOf(3)).charAt(1) + "";
            String thirdChar3 = inputStrings.get(inputLengths.indexOf(3)).charAt(2) + "";

            String firstChar4 = inputStrings.get(inputLengths.indexOf(4)).charAt(0) + "";
            String secondChar4 = inputStrings.get(inputLengths.indexOf(4)).charAt(1) + "";
            String thirdChar4 = inputStrings.get(inputLengths.indexOf(4)).charAt(2) + "";
            String fourthChar4 = inputStrings.get(inputLengths.indexOf(4)).charAt(3) + "";

            ArrayList<String> presentChars = new ArrayList<>(Arrays.asList(firstChar2, secondChar2, firstChar3, secondChar3, thirdChar3, firstChar4, secondChar4, thirdChar4, fourthChar4));
            List<String> distinctPresentChars = presentChars.stream().distinct().collect(Collectors.toList());
            String distPresentStrg = arrayListToString(distinctPresentChars.toString());
            String eOrG = arrayListToString(getDiff(fullOptions, distPresentStrg));

            String firstMissing = eOrG.charAt(0) + "";
            String secondMissing = eOrG.charAt(1) + ""; // Error here - picking this out as d, which was already used.
            entryStrings[5] = firstMissing;
            entryStrings[6] = secondMissing;
            possibleMap.put(firstMissing, Arrays.asList("e", "g"));
            possibleMap.put(secondMissing, Arrays.asList("g", "e"));
        }
        // By now, should know what maps to a, b, c, d, e, f, g.
        // Now need to use these as maps to generate all digits 0-9.
        /**
         * 2d array of possible combinations             1st 1 = c f, 1st 2 = fc
         *                      1 2  3 4  5 6  7 8        2nd 1 = b d, 2nd 2 = d b
         *      d -> [a]        a a  a a  a a  a a        3rd 1 = e g, 3rd 2 = g e
         *      a -> [c, f]     c c  c c  f f  f f                         1 1 1
         *      b -> [f, c]     f f  f f  c c  c c                         1 1 2
         *      e -> [b, d]     b b  d d  b b  d d                         1 2 1
         *      f -> [d, b]     d d  b b  d d  b b                         1 2 2
         *      c -> [e, g]     e g  e g  e g  e g                         2 1 1
         *      g -> [g, e]     g e  g e  g e  g e                         2 1 2
         *                                                                2 2 1 ** Missing
         *                                                                2 2 2
         */

        Map<String, String> possibleMap1 = new HashMap<>();
        Map<String, String> possibleMap2 = new HashMap<>();
        Map<String, String> possibleMap3 = new HashMap<>();
        Map<String, String> possibleMap4 = new HashMap<>();
        Map<String, String> possibleMap5 = new HashMap<>();
        Map<String, String> possibleMap6 = new HashMap<>();
        Map<String, String> possibleMap7 = new HashMap<>();
        Map<String, String> possibleMap8 = new HashMap<>();

        for(int input=0; input < 7; input++) {
            possibleMap1.put(entryStrings[input], combination1.get(input));
            possibleMap2.put(entryStrings[input], combination2.get(input));
            possibleMap3.put(entryStrings[input], combination3.get(input));
            possibleMap4.put(entryStrings[input], combination4.get(input));
            possibleMap5.put(entryStrings[input], combination5.get(input));
            possibleMap6.put(entryStrings[input], combination6.get(input));
            possibleMap7.put(entryStrings[input], combination7.get(input));
            possibleMap8.put(entryStrings[input], combination8.get(input));

        }
        List<Map<String, String>> possibleMaps = new ArrayList<>();
        possibleMaps.add(possibleMap1);
        possibleMaps.add(possibleMap2);
        possibleMaps.add(possibleMap3);
        possibleMaps.add(possibleMap4);
        possibleMaps.add(possibleMap5);
        possibleMaps.add(possibleMap6);
        possibleMaps.add(possibleMap7);
        possibleMaps.add(possibleMap8);

        Optional<Map<String, String>> maybeMapToUse = possibleMaps.stream().filter(possibleMap -> isMapCorrectForLine(possibleMap, inputLine)).findFirst();

        if(maybeMapToUse.isPresent()) {
            String[] outputs = outputLine.split(Pattern.quote(" "));
            List<String> outputStrings = Arrays.stream(outputs).collect(Collectors.toList());
            Map<String, String> mapToUse = maybeMapToUse.get();
            List<String> mappedOutputStrings = mapLineOfMessages(outputStrings, mapToUse);
            List<Integer> mappedOutputIntegers = getDigitsFromMessages(mappedOutputStrings);

            System.out.println(getIntegerFromList(mappedOutputIntegers));
            return getIntegerFromList(mappedOutputIntegers);

        } else {
            System.out.println("No map found for " + inputLine);
            return 0;
        }

    }

    public Integer getIntegerFromList(List<Integer> integerList) {
        Integer integerValue = 0;
        integerValue += integerList.get(0) * 1000;
        integerValue += integerList.get(1) * 100;
        integerValue += integerList.get(2) * 10;
        integerValue += integerList.get(3) * 1;
        return integerValue;
    }

    public Boolean isMapCorrectForLine(Map<String, String> mapToAssess, String inputLine) {
        String[] inputs = inputLine.split(Pattern.quote(" "));
        List<String> inputStrings = Arrays.stream(inputs).collect(Collectors.toList());

        List<String> outputStrings = mapLineOfMessages(inputStrings, mapToAssess);
        return isStringsInListMatchANumber(outputStrings);
    }


    private Boolean isStringsInListMatchANumber(List<String> outputStrings){
        Long totalMatches = outputStrings.stream().filter(outputString -> isMatchForInputString(outputString)).count();
        return totalMatches == outputStrings.size() ? true : false;

    }

    private Boolean isMatchForInputString(String inputString){
        return VALID_NUMBERS.stream().filter(validString -> validString.equals(inputString)).findFirst().isPresent();
    }

    private List<String> mapLineOfMessages(List<String> lineOfMessages, Map<String, String> mapBeingTried) {
        return lineOfMessages.stream()
                .map(message -> sortedString(arrayListToString(mapSingleMessage(message, mapBeingTried))))
                .collect(Collectors.toList());
    }

    private List<Integer> getDigitsFromMessages(List<String> lineOfMessages) {
        return lineOfMessages.stream()
                .map(message -> getIntegerMatchingMessage(message)).collect(Collectors.toList());
    }

    private Integer getIntegerMatchingMessage(String message) {
        Integer i = null;
        final Optional<String> maybeStringOfNumber = VALID_NUMBERS.stream().filter(validNo -> validNo.equals(message)).findFirst();
        if(maybeStringOfNumber.isPresent()) {
            i = VALID_NUMBERS.indexOf(maybeStringOfNumber.get());
        }
        return i;
    }

    private String mapSingleMessage(String input, Map<String, String> mapBeingTried) {
        String [] inputCharacters = input.split("");
        List<String> collect = Arrays.stream(inputCharacters).map(character -> mapBeingTried.get(character)).collect(Collectors.toList());

        return collect.toString();
    }

    private String mapMessage(String input, Integer iteration) {
        String[] inputCharacters = new String[input.length()];
        for(int i=0; i < input.length(); i++) {
            inputCharacters[i] = input.charAt(i)+ "";
        }
        List<String> collect = Arrays.stream(inputCharacters).map(character -> possibleMap.get(character).get(iteration)).collect(Collectors.toList()); //.collect(Collectors.toList());
        return collect.toString();
    }

    private String arrayListToString(String arrayListString) {
        return arrayListString.replace("[", "").replace(", ","").replace("]", "");
    }
    private String sortedString(String unsortedString) {
        char charArray[] = unsortedString.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }


    // Answer to part 1
    public void countEntriesWithLengths() {
        IntStream requiredNumbers = IntStream.of(2,3,4,7);
        ArrayList<Integer> reqNos = new ArrayList<>();
        requiredNumbers.forEach(i -> reqNos.add(i));

     Long matchingCount = entries.stream()
                .map(line -> line.split(Pattern.quote(" ")))
                .flatMap(Stream::of)
                .map(string -> string.length())
                .filter(len -> reqNos.contains(len))
                .count();
        System.out.println(matchingCount);
    }

    //Use this
    public void readLines(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> {
                entries.add(line.split(Pattern.quote("|"))[1]);
                rawEntries.add(line.split(Pattern.quote("|"))[0].trim());
                rawOutputs.add(line.split(Pattern.quote("|"))[1].trim());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getDiff(String completeList, String b) {
        //Check all a in b, then b in a
        ArrayList<String> missingFromOthers = new ArrayList<>();
        final char[] Achars = completeList.toCharArray();
        for (int i = 0; i < Achars.length; i++) {
            int index = b.lastIndexOf(Achars[i]);
            if (index == -1) {
                missingFromOthers.add(String.valueOf(Achars[i]));
            }
        }
        return missingFromOthers.toString();
    }
}
